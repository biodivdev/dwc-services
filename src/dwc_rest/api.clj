(ns dwc-rest.api
  (:use dwc-rest.core)
  (:use compojure.core))

(def formats ["dwc-a" "csv" "tsv" "ods" "xlsx" "json" "geojson"])

(def params ["from" "to" "url" "hook" "stream"])

(defroutes  api-v1-routes

  (GET "/convert" {params :params}
    nil)

  (POST "/convert" data
    nil)
  
  )

