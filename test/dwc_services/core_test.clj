(ns dwc-services.core-test
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
