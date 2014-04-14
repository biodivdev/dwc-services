# dwc-services

Set of common web services around [DarwinCore library](http://github.com/CNCFlora/dwc).

## Usage

Checkout [Services API](http://cncflora.jbrj.gov.br/dwc-services).

## Dev

Use [vagrant](http://vagrantup.com):

    vagrant up
    vagrant ssh
    cd /vagrant

Tasks:

    lein midje # run tests
    lein midje :autotest # run tests on file changes
    lein ring server-headless # run server on 192.168.50.30:3000

## License

Apache License 2.0

