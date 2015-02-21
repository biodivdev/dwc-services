#!/bin/bash

java $JAVA_OPTS -jar /root/jetty.jar --path $CONTEXT /root/dwc-services.war

