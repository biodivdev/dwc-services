#!/bin/bash

cd /root/dwc-services && nohup lein ring server-headless > lein.log 2>&1 &

/usr/sbin/sshd -D


