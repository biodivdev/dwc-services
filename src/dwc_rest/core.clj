(ns dwc-rest.core
  (:use dwc.csv)
  (:use dwc.xlsx)
  (:use dwc.json)
  (:use dwc.geojson)
  (:use dwc.archive))

(def reader
  {:csv read-csv
   :xlsx read-xlsx
   :json read-json
   :geojson read-geojson
   :dwca read-archive})

(def writer
  {:json write-json
   :geojson write-geojson})

(defn str-to-file
  [data] 
   (let [tmp (java.io.File/createTempFile "dwc-" ".tmp")]
     (spit tmp data)
     (.getAbsolutePath tmp)))

(defn convert
  [{from :from to :to source :source}]
   ((get writer (keyword to ))
     ((get reader (keyword from )) source)))

