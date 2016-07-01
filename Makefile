all: build

build:
	lein ring uberwar
	docker build -t diogok/dwc-services .

push:
	docker push diogok/dwc-services

