(defproject dwc-rest "0.0.1"
  :description "Simple web api to convert darwincore formats."
  :url "http://github.com/CNCFlora/dwc-rest"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main dwc-rest.server
  :ring {:handler dwc-rest.server :reload-paths ["src"]}
  :resources-path "resources"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [dwc "0.0.1"]
                 [compojure "1.1.6"]
                 [ring/ring-core "1.2.1"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [clj-http "0.9.0"]]
  :profiles {:dev {:dependencies [[midje "1.5.1"]
                                  [javax.servlet/servlet-api "2.5"]]
                   :plugins [[lein-ring "0.8.6"]
                             [lein-midje "3.0.0"]]}})


