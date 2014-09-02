(defproject dwc-services "0.0.7"
  :description "Simple web api to convert darwincore formats."
  :url "http://github.com/CNCFlora/dwc-rest"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main dwc-services.server
  :ring {:handler dwc-services.server/app :reload-paths ["src"]}
  :resources-path "resources"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [dwc "0.0.18"]
                 [compojure "1.1.6"]
                 [ring/ring-core "1.3.0"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [org.clojure/data.json "0.2.5"]
                 [clj-http "0.9.0"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]
                                  [javax.servlet/servlet-api "2.5"]]
                   :plugins [[lein-ring "0.8.6"]
                             [lein-midje "3.1.3"]]}})
