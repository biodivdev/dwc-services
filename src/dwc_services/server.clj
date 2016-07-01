(ns dwc-services.server
  (:use ring.adapter.jetty
        ring.util.response)
  (:use compojure.core)
  (:use dwc-services.api)
  (:use dwc-services.web-wrap)
  (:use ring.middleware.gzip)
  (:require [compojure.route :as route]
            [compojure.handler :as handler])
  (:gen-class))

(defroutes main

  (GET "/" [] 
    (slurp (clojure.java.io/resource "public/index.html")))

  (POST "/echo" req
    (let [data (slurp (:body req))]
      (println "/echo" data)
      {:status 200 :body data}))

  (context "/api" []
    (context "/v1" [] api-v1-routes))

  (route/resources "/"))

(def app
  (-> (handler/site main)
      (wrap-context)
      (wrap-context-redir)
      (wrap-proxy-redir)
      (wrap-jsonp)
      (wrap-options)
      (wrap-gzip)
      ))


(defn -main
  ""
  ([& args]
    (let [port (System/getProperty "PORT" "3000")]
     (run-jetty #'app {:port (Integer/valueOf port) :join? true}))
    (shutdown-agents)))

