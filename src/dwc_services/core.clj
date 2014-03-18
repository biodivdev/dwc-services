(ns dwc-services.core
  (:use dwc.fixes)
  (:use dwc.validation)
  (:use dwc.csv)
  (:use dwc.xlsx)
  (:use dwc.json)
  (:use dwc.geojson)
  (:use dwc.archive)
  (:use clojure.java.io))

(def readers
  {:csv read-csv
   :xlsx read-xlsx
   :json read-json
   :geojson read-geojson
   :dwca read-archive
   :archive read-archive})

(def writers
  {:json write-json
   :geojson write-geojson})

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
     (println (.getAbsolutePath tmp))
     (with-open [output (output-stream tmp)]
       (.write output (stream-to-bytes (input-stream input))))
     (.getAbsolutePath tmp )))

(defn convert
  [{from :from to :to source :source fixes :fixes}]
   ((get writers (keyword to ))
    (let [data ((get readers (keyword from)) source)]
      (if-not fixes
       data
       (-fix-> data)))))

(defn fix
  [data]
   (-fix-> (read-json data)))

(defn validation
  [data] 
   (map validate (read-json data)))

