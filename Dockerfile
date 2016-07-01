FROM diogok/java8:zulu

RUN curl https://repo1.maven.org/maven2/org/eclipse/jetty/jetty-runner/9.3.9.v20160517/jetty-runner-9.3.9.v20160517.jar -o /root/jetty.jar

EXPOSE 80

ENV CONTEXT /
ENV JAVA_OPTS -server -XX:+UseConcMarkSweepGC -XX:+UseCompressedOops -XX:+DoEscapeAnalysis
ENV PORT 80

ADD start.sh /root/start.sh
RUN chmod +x /root/start.sh
CMD ["/root/start.sh"]

ADD target/dwc-services-0.0.33-standalone.war /root/dwc-services.war

