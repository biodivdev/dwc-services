(defproject dwc-services "0.0.15"
  :description "Simple web api to convert darwincore formats."
  :url "http://github.com/CNCFlora/dwc-services"
  :license {:name "MIT" }
  :main dwc-services.server
  :ring {:handler dwc-services.server/app :reload-paths ["src"]}
  :resources-path "resources"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [dwc "0.0.35"]
                 [clj-time "0.8.0"]
                 [compojure "1.1.8"]
                 [ring/ring-core "1.3.1"]
                 [ring/ring-jetty-adapter "1.3.1"]
                 [org.clojure/data.json "0.2.5"]
                 [clj-http "0.9.0"]]
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[midje "1.6.3"]
                                  [javax.servlet/servlet-api "2.5"]]
                   :plugins [[lein-ring "0.8.6"]
                             [lein-midje "3.1.3"]]}})
