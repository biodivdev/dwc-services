FROM nickstenning/java7 

RUN apt-get install supervisor ruby -y
RUN gem sources -r http://rubygems.org/ && gem sources -a https://rubygems.org/ && gem install small-ops
RUN mkdir /var/log/supervisord 

RUN wget http://repo1.maven.org/maven2/org/eclipse/jetty/jetty-runner/9.2.0.M0/jetty-runner-9.2.0.M0.jar -O /root/jetty.jar

ADD target/dwc-services-0.0.4-standalone.war /root/dwc-services.war
ADD start.sh /root/start.sh
RUN chmod +x /root/start.sh

ADD supervisord.conf /etc/supervisor/conf.d/proxy.conf

EXPOSE 8080

CMD ["supervisord"]

