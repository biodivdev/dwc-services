(defproject dwc-services "0.0.28"
  :description "Simple web api to convert darwincore formats and perform analysis."
  :url "http://github.com/diogok/dwc-services"
  :license {:name "MIT" }
  :ring {:handler dwc-services.server/app :reload-paths ["src"]}
  :resources-path "resources"
  :main dwc-services.server
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [dwc-io "0.0.52"]
                 [dwc-analysis "0.0.27"]
                 [compojure "1.4.0"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [org.clojure/data.json "0.2.5"]
                 [org.clojars.diogok/ring-gzip-middleware "0.0.2"]
                 [clj-http "2.0.0"]]
  :profiles {:dev {:dependencies [[midje "1.8.2"]
                                  [javax.servlet/servlet-api "2.5"]]
                   :plugins [[lein-ring "0.9.7"]
                             [lein-midje "3.1.3"]]}})
