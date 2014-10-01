(ns dwc-services.server
  (:use ring.adapter.jetty
        ring.util.response)
  (:use compojure.core)
  (:use dwc-services.api)
  (:use dwc-services.web-wrap)
  (:require [compojure.route :as route]
            [compojure.handler :as handler])
  (:gen-class))

(defroutes main

  (GET "/" [] 
    (redirect "/index.html"))

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
      (wrap-options)))

(defn -main
  ""
  [& args]
  (run-jetty app {:port 3030 :join? true}))

