#!/bin/bash

cd /root && nohup lein ring server-headless > lein.log &"

/usr/sbin/sshd -D


