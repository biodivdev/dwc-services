(ns dwc-rest.api
  (:require [clj-http.client :as http])
  (:use dwc-rest.core)
  (:use compojure.core)
  (:use ring.util.response))

(def ctypes 
  {:json "application/json"
   :geojson "application/json"
   :csv "text/csv"
   :tsv "text/tsv"
   :dwca "application/zip"
   :archive "application/zip"
   :xlsx "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"})

(defn res
  ""
  [params]
  (try
    (if-let [url (:url params)]
      (if-let [from (:from params)]
        (if-let [to (:to params)]
          (if-let [hook (:hook params)]
            (do 
              (future
                (http/post hook {:body (convert {:from from :to to :source url})
                                  :headers {"Content-Type" (get ctypes to)}}))
              {:status 200 :body "ok"})
            {:status 200
             :headers {"Content-Disposition" (str "attachment; filename=\"dwc." to "\"")
                       "Content-Type" (get ctypes to)}
             :body (convert {:from from :to to :source url})})
          {:status 400 :body "Must provide 'to' parameter of the output format."})
        {:status 400 :body "Must provide 'from' parameter of the input format."})
     {:status 400 :body "Must provide 'url' parameter of data source."})
    (catch Exception e 
      (do (.printStackTrace e)
        {:status 500 :body (str "Server error: " (.getMessage e))}))
    )
  )

(defroutes  api-v1-routes

  (GET "/convert" req
   (res (:params req)))

  (POST "/convert" req
    (res (assoc (:params req) :url (:body req))))

)

