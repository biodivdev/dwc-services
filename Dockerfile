FROM dockerfile/java:oracle-java8

RUN apt-get update && apt-get install supervisor ruby -y

RUN mkdir /var/log/supervisord 
RUN mkdir /var/lib/floraconnect

RUN wget http://repo1.maven.org/maven2/org/eclipse/jetty/jetty-runner/9.2.2.v20140723/jetty-runner-9.2.2.v20140723.jar -O /root/jetty.jar

RUN gem sources -r http://rubygems.org/ && gem sources -a https://rubygems.org/ && gem install small-ops -v 0.0.30

ADD start.sh /root/start.sh
RUN chmod +x /root/start.sh

ADD supervisord.conf /etc/supervisor/conf.d/dwc.conf

EXPOSE 8080
EXPOSE 9001

CMD ["supervisord"]

ADD target/dwc-services-0.0.12-standalone.war /root/dwc-services.war

