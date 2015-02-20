(ns dwc-services.core
  (:use dwc-io.fixes)
  (:use dwc-io.validation)
  (:use dwc-io.csv)
  (:use dwc-io.xlsx)
  (:use dwc-io.json)
  (:use dwc-io.geojson)
  (:use dwc-io.archive)
  (:use dwc-io.gbif)
  (:use clojure.java.io)
  (:require [dwc-analysis.all :as all])
  (:require [dwc-analysis.aoo :as aoo])
  (:require [dwc-analysis.eoo :as eoo])
  (:require [dwc-analysis.risk :as risk])
  (:require [dwc-analysis.clusters :as clusters])
  (:require [dwc-analysis.quality :as quality])
  (:require [dwc-io.tapir :as tapir]
            [dwc-io.digir :as digir]))

(def readers
  {:csv read-csv
   :xlsx read-xlsx
   :json read-json
   :geojson read-geojson
   :dwca read-archive
   :archive read-archive})

(def gbif read-gbif)

(def writers
  {:json write-json
   :geojson write-geojson
   :csv write-csv
   :xlsx #(java.io.File. (write-xlsx %) )})

(defn str-to-file
  [data] 
   (let [tmp (java.io.File/createTempFile "dwc-" ".tmp")]
     (spit tmp data)
     (.getAbsolutePath tmp)))

(defn stream-to-bytes
    [in]
    (with-open [bout (java.io.ByteArrayOutputStream.)]
      (copy in bout)
      (.toByteArray bout)))

(defn stream-to-file
  [input] 
   (let [tmp (as-file (str "/tmp/dwc-" (java.util.UUID/randomUUID) ".tmp" ))]
     (.createNewFile tmp)
     (with-open [output (output-stream tmp)]
       (.write output (stream-to-bytes (input-stream input))))
     (.getAbsolutePath tmp )))

(defn convert
  [{from :from to :to source :source fixes :fixes}]
  (let [writer (get writers (keyword to))
        reader (get readers (keyword from))
        data   (reader source)]
    (writer
      (if fixes 
        (-fix-> data)
        data)
    )
  ))

(defn fix
  [data]
   (-fix-> (read-json data)))

(defn validation
  [data] 
   (map validate (read-json data)))

(defn search
  [stype url opts]
  (let [opts (if (empty? (:fields opts)) opts (dissoc opts :fields) )]
    (if (= "tapir" stype)
      (tapir/read-tapir url opts)
      (if (= "digir" stype)
        (digir/read-digir url opts)
        nil))))

(defn eoo
  [data] 
   (eoo/eoo (-fix-> (read-json data))))

(defn aoo
  [data] 
   (aoo/aoo (-fix-> (read-json data))))

(defn clusters
  [data] 
   (clusters/clusters (-fix-> (read-json data))))

(defn quality
  [data] 
   (quality/analyse (-fix-> (read-json data))))

(defn all-analysis
  [data]
  (all/all-analysis (-fix-> (read-json data))))

