(ns dwc-services.core-test
  (:use [clojure.data.json :only [read-str write-str]])
  (:use dwc-services.core
        midje.sweet))

(fact "It converts formats"
  (let [occs [{:scientificName "Vicia faba"}]
        csv  "\"scientificName\"\n\"Vicia faba\""
        json "[{\"scientificName\":\"Vicia faba\"}]"]
    (convert {:from :csv :to :json :source (str-to-file csv)}) => json
    (convert {:from :json :to :csv :source (str-to-file json)}) => csv
    ))

(fact "It fixes"
  (let [json "[{\"GlobalUniqueIdentifier\":\"123\"}]"]
    (fix (str-to-file json )) => (list {:occurrenceID "123" :globalUniqueIdentifier "123"} ) ))

(fact "It converts formats with fixes"
  (let [occs [{:scientificName "Vicia faba"}]
        csv  "\"scientificName\";\"id\"\n\"Vicia faba\";\"123\""
        json "[{\"occurrenceID\":\"123\",\"id\":\"123\",\"scientificName\":\"Vicia faba\"}]"]
    (convert {:fixes true :from :csv :to :json :source (str-to-file csv)}) => json
    ))

(fact "It performs all analysis"
  (let [data (str-to-file (write-str [{:decimalLatitude 10.10 :decimalLongitude 20.20 }] ) )]
    (int (:area (eoo data))) => 257
    (:area (aoo data)) => 4000
    (get-in (all-analysis data) [:aoo :area]) => 4000
    (int (get-in (all-analysis data) [:eoo :area])) => 257
    (map int (map :area (map val (all-analysis data) )) ) => (list 257 4000)
    )
  )
