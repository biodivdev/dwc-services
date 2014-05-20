FROM nickstenning/java7 

RUN apt-get update -y && apt-get upgrade -y

RUN wget http://repo1.maven.org/maven2/org/eclipse/jetty/jetty-runner/9.2.0.M0/jetty-runner-9.2.0.M0.jar -O /root/jetty.jar

ADD target/dwc-services-0.0.2-standalone.war /root/dwc-services.war
ADD start.sh /root/start.sh
RUN chmod +x /root/start.sh

EXPOSE 8080

CMD ["/root/start.sh"]

