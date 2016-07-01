(defproject dwc-services "0.0.33"
  :description "Simple web api to convert darwincore formats and perform analysis."
  :url "http://github.com/diogok/dwc-services"
  :license {:name "MIT" }
  :ring {:handler dwc-services.server/app :reload-paths ["src"]}
  :resources-path "resources"
  :main dwc-services.server
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [dwc-io "0.0.56"]
                 [dwc-analysis "0.0.31"]
                 [compojure "1.5.1"]
                 [ring/ring-core "1.5.0"]
                 [ring/ring-jetty-adapter "1.5.0"]
                 [org.clojure/data.json "0.2.6"]
                 [amalloy/ring-gzip-middleware "0.1.3"]
                 [clj-http "2.2.0"]]
  :repositories [["osgeo" "http://download.osgeo.org/webdav/geotools/"]]
  :profiles {:dev {:dependencies [[midje "1.8.2"]
                                  [javax.servlet/servlet-api "2.5"]]
                   :plugins [[lein-ring "0.9.7"]
                             [lein-midje "3.2"]]}})
