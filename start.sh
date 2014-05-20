#!/bin/bash

[[ ! $CONTEXT ]] && CONTEXT="/"
cd /root
java -server -jar jetty.jar --path $CONTEXT dwc-services.war

