# dwc-services

Set of common web services around [DarwinCore library](http://github.com/diogok/dwc).

### Features

- Convertion from/to: json, geojson, csv, xlsxa and dwca.
- Validation the occurrences
- AOO/EOO calculation
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

Checkout the [Services API](http://dwc-01-1.diogok.cont.tutum.io:8080/api/v1).

## Dev

Clone the repo and enter the project:

    git clone git@github.com:cncflora/dwc-services.git
    cd dwc-services

Use [vagrant](http://vagrantup.com) have a dev machine:

    vagrant up
    vagrant ssh
    cd /vagrant # inside vagrant ssh

Tasks:

    lein midje # run tests
    lein midje :autotest # run tests on file changes
    lein ring server-headless # run server on 192.168.50.30:3000
    lein ring uberwar # generate war
    lein ring uberjar # generate jar

Docker:

    docker build -t cncflora/dwc-services . # build the container
    docker run -p 8181:80 -p 9001:9001 -t cncflora/dwc-servces # run the container
    docker run -p 8181:80 -p 9001:9001 -e "CONTEXT=/dwc" -t cncflora/dwc-servces # run the container with app on context /dwc

## License

MIT

