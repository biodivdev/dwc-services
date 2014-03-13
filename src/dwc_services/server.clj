(ns dwc-services.server
  (:use ring.adapter.jetty
        ring.util.response)
  (:use compojure.core)
  (:use dwc-services.api)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]))

(defn jsonp
  "JSONP Wrapper"
  [handler] 
   (fn [req] 
     (let [q (:query-string req)
           response (handler req)]
       (if (nil? q) response
         (if-not (.contains q "callback=") response
           (let [callback (second
                           (re-find #"callback=([a-zA-Z0-9-_]+)" 
                            (:query-string req)))]
               (assoc response :body (str callback "(" (:body response) ");")))
           )))))

(defn options
  "CORS Options Wrapper"
  [handler]
   (fn [req]
     (if (= "OPTIONS" (:method req))
       {:headers {"Allow" "GET,POST,PUT,OPTIONS" 
                  "Access-Control-Allow-Origin"  "*"
                  "Access-Control-Allow-Methods" "GET,POST,OPTIONS" 
                  "Access-Control-Allow-Headers" "x-requested-with"}
        :status 200}
       (handler req))))

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
      (jsonp)
      (options)))

(defn -main
  ""
  [& args]
  (run-jetty app {:port 3030 :join? true}))

