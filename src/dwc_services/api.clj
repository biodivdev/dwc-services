(ns dwc-services.api
  (:require [clj-http.client :as http])
  (:use dwc-services.core)
  (:use compojure.core)
  (:use [clojure.data.json :only [read-str write-str]])
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
   (try 
     (let [data (fun)]
       (if (string? data)
         {:headers {"Content-Type" "application/json"} :body data}
         data))
    (catch Exception e 
      (do 
        (.printStackTrace e)
        {:status 500
         :headers {"Content-Type" "application/json"}
         :body (write-str (str "Server error: " (.getMessage e)))
        }))))

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
                       "Content-Type" (get ctypes (keyword to ))}
             :body (convert {:from from :to to :source url :fixes (:fixes params)})})
          {:status 400 :body "Must provide 'to' parameter of the output format."})
        {:status 400 :body "Must provide 'from' parameter of the input format."})
     {:status 400 :body "Must provide 'url' parameter of data source or data as input."}))

(defroutes  api-v1-routes

  (GET "/convert" req
   (safe #(convert-api (:params req))))

  (POST "/convert" req
    (safe #(convert-api (assoc (:params req) :url (stream-to-file (:body req))))))

  (GET "/validate" {params :params}
    (if-let [url (:url params)]
      (safe #(write-str (validation (:url params))))
      {:status 400 :body "Must provide 'url' parameter of data as input."}))

  (POST "/validate" req
    (if-let [data (:body req)]
      (safe #(write-str (validation data)))
      {:status 400 :body "Must provide occurrence json of data as input."}))

  (GET "/fix" {params :params}
    (if-let [data (:url params)]
      (safe #(write-str (fix data)))
      {:status 400 :body "Must provide occurrence json of data as input."}))

  (POST "/fix" req
    (if-let [data (:body req)]
      (safe #(write-str (fix data)))
      {:status 400 :body "Must provide occurrence json of data as input."}))

  (GET "/search/gbif" {params :params}
    (if-let [field (:field params)]
      (if-let [value (:value params)]
        (safe #(write-str 
                 (gbif {field value})))
        {:status 400 :body "Must provide 'value' parameter to search in field."})
      {:status 400 :body "Must provide 'field' parameter of field to search in."}))

  (GET "/search/:type" {params :params}
    (if-let [url (:url params)]
      (if-let [field (:field params)]
        (if-let [value (:value params)]
            (safe #(write-str 
                    (search (:type params) url
                      {:filters {field value} 
                       :fields (vec (.split (or (:fields params) "") ","))
                       :start (Integer/valueOf (or (:start params) 0))
                       :limit (Integer/valueOf (or (:limit params) 9999))})))
          {:status 400 :body "Must provide 'value' parameter to search in field."})
        {:status 400 :body "Must provide 'field' parameter of field to search in."})
      {:status 400 :body "Must provide 'url' parameter of data as input."}))

  (context "/analysis" []

    (GET "/all" {params :params}
      (if-let [data (:url params)]
        (safe #(write-str (all-analysis data)))
        {:status 400 :body "Must provide occurrence json of data as input."}))

    (POST "/all" req
      (if-let [data (:body req)]
        (safe #(write-str (all-analysis data)))
        {:status 400 :body "Must provide occurrence json of data as input."}))

    (GET "/eoo" {params :params}
      (if-let [data (:url params)]
        (safe #(write-str (eoo data)))
        {:status 400 :body "Must provide occurrence json of data as input."}))

    (POST "/eoo" req
      (if-let [data (:body req)]
        (safe #(write-str (eoo data)))
        {:status 400 :body "Must provide occurrence json of data as input."}))

    (GET "/aoo" {params :params}
      (if-let [data (:url params)]
        (safe #(write-str (aoo data)))
        {:status 400 :body "Must provide occurrence json of data as input."}))

    (POST "/aoo" req
      (if-let [data (:body req)]
        (safe #(write-str (aoo data)))
        {:status 400 :body "Must provide occurrence json of data as input."}))

    (GET "/populations" {params :params}
      (if-let [data (:url params)]
        (safe #(write-str (populations data)))
        {:status 400 :body "Must provide occurrence json of data as input."}))

    (POST "/populations" req
      (if-let [data (:body req)]
        (safe #(write-str (populations data)))
        {:status 400 :body "Must provide occurrence json of data as input."}))
  )
)

