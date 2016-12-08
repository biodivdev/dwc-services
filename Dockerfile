FROM diogok/java8:zulu

EXPOSE 80

WORKDIR /opt
CMD ["java","-server","-XX:+UseConcMarkSweepGC","-XX:+UseCompressedOops","-XX:+DoEscapeAnalysis","-DPORT=80","-jar","dwc-services.jar"]

ADD target/dwc-services-0.0.34-standalone.jar /opt/dwc-services.jar

