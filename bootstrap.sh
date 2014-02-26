#!/usr/bin/env bash

# install java and utils
apt-get update
apt-get install openjdk-7-jdk curl git tmux vim htop -y

# install leiningen
[[ ! -e lein ]] && wget https://raw.github.com/technomancy/leiningen/stable/bin/lein -O /usr/bin/lein
chmod +x /usr/bin/lein
su vagrant -c 'lein self-install'

# prepare startup
echo 'echo $(date) > /var/log/rc.log' > /etc/rc.local
#echo 'su vagrant -c "cd /vagrant && nohup lein ring server-headless &" >> /var/log/rc.log 2>&1' >> /etc/rc.local

# start the service and register it
#su vagrant -c 'cd /vagrant && nohup lein ring server-headless &'

