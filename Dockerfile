FROM diogok/java8

RUN curl https://repo1.maven.org/maven2/org/eclipse/jetty/jetty-runner/9.3.3.v20150827/jetty-runner-9.3.3.v20150827.jar -o /root/jetty.jar

EXPOSE 80

ENV CONTEXT /
ENV JAVA_OPTS -server -XX:+UseConcMarkSweepGC -XX:+UseCompressedOops -XX:+DoEscapeAnalysis
ENV PORT 80

ADD start.sh /root/start.sh
RUN chmod +x /root/start.sh
CMD ["/root/start.sh"]

ADD target/dwc-services-0.0.27-standalone.war /root/dwc-services.war

