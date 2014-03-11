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

(defn safe
  ""
  [fun] 
   (try (fun)
    (catch Exception e 
      (do (.printStackTrace e)
        {:status 500 :body (str "Server error: " (.getMessage e))}))))

(defn convert-api
  ""
  [params]
    (if-let [url (:url params)]
      (if-let [from (:from params)]
        (if-let [to (:to params)]
          (if-let [hook (:hook params)]
            (do 
              (future
                (http/post hook {:body (convert {:from from :to to :source url :fixes (:fixes params)})
                                  :headers {"Content-Type" (get ctypes to)}}))
              {:status 200 :body "ok"})
            {:status 200
             :headers {"Content-Disposition" (str "attachment; filename=\"dwc." to "\"")
                       "Content-Type" (get ctypes to)}
             :body (convert {:from from :to to :source url :fixes (:fixes params)})})
          {:status 400 :body "Must provide 'to' parameter of the output format."})
        {:status 400 :body "Must provide 'from' parameter of the input format."})
     {:status 400 :body "Must provide 'url' parameter of data source or data as input."}))

(defroutes  api-v1-routes

  (GET "/convert" req
   (safe #(convert-api (:params req))))

  (POST "/convert" req
    (safe #(convert-api (assoc (:params req) :url (:body req)))))

)

