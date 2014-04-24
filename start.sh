#!/bin/bash

[[ ! $CONTEXT ]] && CONTEXT="/"
su $APP_USER -c "cd ~/ && nohup java -jar jetty.jar --path $CONTEXT dwc-services.war > dwc-services.log 2>&1 &" &

/usr/sbin/sshd -D

