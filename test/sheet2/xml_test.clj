(ns sheet2.xml-test
  (:require [clojure.test :refer :all]
            [sheet2.xml :refer :all]))

(deftest to-xml-attr-test
  (is (= nil (to-xml-attr "col" "null")))
  (is (= nil (to-xml-attr "col" "NULL")))
  (is (= "col=\"42\"" (to-xml-attr "col" 42))))

(deftest to-xml-element-test
  (is (= "    <table />" (to-xml-element "table" [])))
  (is (= "    <table attr1 attr2/>"
         (to-xml-element "table" [nil "attr1" nil "attr2"]))))
