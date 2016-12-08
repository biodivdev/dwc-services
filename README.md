# dwc-services

This implements a complete API for working with DwarinCore records, from  [dwc-io](https://github.com/biodivdev/dwc-io) and [dwc-analysis](https://github.com/biodivdev/dwc-analsys) libs.

### Features

- Convertion from/to: json, geojson, csv, xlsx and dwca.
- Validation of occurrences
- Search on Tapir, Digir and GBIF
- Common fixes on records
-- verbatimCoordinates vs decimalLatitude/decimalLongitude
-- fields and keys case (DecimalLatitude vs decimalLatitude)
-- empty and null values
-- occurrenceID generation, if not exists, as one of:
--- id field
--- globalUniqueIdentifier field
--- institutionCode:collectionCode:catalogNumber
--- randomUUID
- Analysis
-- AOO
-- EOO
-- Clusters
-- Quality

## Usage

Checkout the [Services API](http://cncflora.jbrj.gov.br/dwc_services/index.html).

## Deploy

### WAR 

Download the latest war from the [ realases page ](https://github.com/biodivdev/dwc-services/releases) and deploy it to your favorite container.

### JAR

Download the latest jar from the [ realases page ](https://github.com/biodivdev/dwc-services/releases) and run it:

  $ java -jar dwc-services.jar

### Docker

Using docker:

    $ docker run -p 8080:80 diogok/dwc-services

## Dev

Clone the repo and enter the project:

    git clone git@github.com:cncflora/dwc-services.git
    cd dwc-services

Lein tasks:

    lein midje # run tests
    lein midje :autotest # run tests on file changes
    lein ring server-headless # run server on 192.168.50.30:3000
    lein ring uberwar # generate war
    lein ring uberjar # generate jar

Docker:

    docker build -t diogok/dwc-services . # build the container
    docker run -p 8181:80 -t diogok/dwc-servces # run the container

## License

MIT

