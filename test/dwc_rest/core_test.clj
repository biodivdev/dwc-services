(ns dwc-rest.core-test
  (:use dwc-rest.core
        midje.sweet))

(fact "It converts"
  (let [occs [{:scientificName "Vicia faba"}]
        csv  "\"scientificName\"\n\"Vicia faba\""
        json "[{\"scientificName\":\"Vicia faba\"}]"]
    (convert {:from :csv :to :json :source (str-to-file csv)}) => json
    ))

