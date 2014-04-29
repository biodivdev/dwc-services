FROM nickstenning/java7 

ENV APP_USER cncflora 
ENV APP_PASS cncflora

RUN cp /etc/apt/sources.list /etc/apt/sources.list.bkp && sed -e 's/http/ftp/g' /etc/apt/sources.list.bkp > /etc/apt/sources.list
RUN apt-get update -y && apt-get upgrade -y
RUN apt-get install curl git vim openssh-server tmux sudo aptitude screen wget -y

RUN useradd -g users -G www-data,sudo -s /bin/bash -m $APP_USER && \
    echo $APP_USER:$APP_PASS | chpasswd && \
    mkdir /var/run/sshd && \
    chmod 755 /var/run/sshd 

RUN wget http://repo1.maven.org/maven2/org/eclipse/jetty/jetty-runner/9.2.0.M0/jetty-runner-9.2.0.M0.jar -O /home/$APP_USER/jetty.jar

ADD target/dwc-services-0.0.1-standalone.war /home/$APP_USER/dwc-services.war
RUN chown $APP_USER /home/$APP_USER/* && chmod +x /home/$APP_USER/*

ADD start.sh /root/start.sh
RUN chmod +x /root/start.sh

EXPOSE 22
EXPOSE 8080

ADD start.sh /root/start.sh
RUN chmod +x /root/start.sh

CMD ["/root/start.sh"]

