#!/bin/bash

java $JAVA_OPTS -jar /root/jetty.jar --port $PORT --path $CONTEXT /root/dwc-services.war

