(ns sheet2.core
  (:require [sheet2.xml :refer [convert-to-xml]])
  (:require [clojure.tools.cli :as cli])
  (:gen-class))

(def args-spec
  "Available command line arguments."
  [["-p" "--element-prefix PREFIX" "Prefix added to all XML elements but root"
    :default ""]
   ["-r" "--root-tag ROOT-TAG" "XML root tag to use"
    :default "dataset"]
   ["-x" "--exclude-sheets SHEETS" "Comma separated list of sheets to exclude"
    :default ""]])

(defn do-main
  [args]
  (let [{:keys [options arguments errors summary]} (cli/parse-opts args args-spec)]
    (if (and (empty? errors) (seq args))
      (doseq [xls-file arguments]
        (println "Converting:" xls-file)
        (convert-to-xml xls-file options))
      (println "Usage: program-name [options] files\n" summary))))

(defn -main
  "Convert Excel sheet into DbUnit Flat XML format."
  [& args]
  (do-main args))



















