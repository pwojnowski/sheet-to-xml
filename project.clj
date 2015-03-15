(defproject excel2dbunit "0.1.0-SNAPSHOT"
  :description "Converts Excel xls(x) files to flat XML used by DbUnit."
  :url "http://github.com/pwojnowski/excel2dbunit"
  :license {:name "MIT License" }
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [dk.ative/docjure "1.8.0"]
                 [org.clojure/tools.cli "0.3.1"]]
  :main ^:skip-aot excel2dbunit.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
