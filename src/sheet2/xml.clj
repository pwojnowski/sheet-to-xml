(ns sheet2.xml
  (:require [dk.ative.docjure.spreadsheet :as d])
  (:import [java.text SimpleDateFormat]
           [org.apache.poi.ss.usermodel Cell DateUtil]))

(defn- get-column-names
  "Returns column names vector from given sheet."
  [sheet]
  (mapv #(.toString %) (d/cell-seq (first (d/row-seq sheet)))))

(defn- format-date-cell [cell]
  (-> (SimpleDateFormat. "yyyy-MM-dd")
      (.format (.getDateCellValue cell))))

(defn- date-cell-p [cell]
  (and (= Cell/CELL_TYPE_NUMERIC (.getCellType cell))
       (DateUtil/isCellDateFormatted cell)))

(defn- format-cell [cell]
  (if (date-cell-p cell)
    (format-date-cell cell)
    (let [as-string (.toString cell)]
      (if (.isEmpty (.trim as-string))
        nil
        as-string))))

(defn- get-row-values
  "Return vectors with rows' values."
  [sheet]
  (filter #(not-every? nil? %)
          (for [row (rest (d/row-seq sheet))]
            (mapv format-cell (d/cell-seq row)))))

(defn to-xml-attr [k v]
  (if (.equalsIgnoreCase "null" v)
    nil
    (format "%s=\"%s\"" k v)))

(defn to-xml-element [name attrs]
  (format "    <%s %s/>" name (clojure.string/join " " (remove nil? attrs))))

(defn- seq-to-lines [xml-lines]
  (clojure.string/join "\n" xml-lines))

(defn- load-sheets
  "Returns seq of sheets from specified file."
  [file excluded-sheets]
  (let [exclusion (into #{} (clojure.string/split excluded-sheets #","))]
    (println "Excluding sheets: " exclusion)
    (filter #(not (exclusion (.toLowerCase (d/sheet-name %))))
            (d/sheet-seq (d/load-workbook file)))))

(defn- sheet-to-xml-seq
  "Returns sheet content as XML, where sheet name is element name and column
names are its attributes."
  [element-prefix sheet]
  (let [name (str element-prefix (.toUpperCase (d/sheet-name sheet)))
        header (get-column-names sheet)
        rows (get-row-values sheet)]
    (seq-to-lines
     (map #(to-xml-element name (mapv to-xml-attr header %)) rows))))

(defn- sheets-to-xml
  [file options]
  (let [root-tag (:root-tag options)
        to-xml-seq (partial sheet-to-xml-seq (:element-prefix options))]
    (str "<" root-tag  ">\n"
         (seq-to-lines
          (map to-xml-seq (load-sheets file (:exclude-sheets options))))
         "\n</" root-tag ">")))

(defn convert-to-xml
  [xls-file options]
  (let [xml-file (clojure.string/replace xls-file #".xls.?$" ".xml")
        xml-data (sheets-to-xml xls-file options)]
    (spit xml-file xml-data)))
