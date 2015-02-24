FROM ubuntu:14.04

RUN apt-get install wget -y

RUN echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee /etc/apt/sources.list.d/webupd8team-java.list && \
    echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list && \
    apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886 && \
    apt-get update && \
    echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
    echo debconf shared/accepted-oracle-license-v1-1 seen true | debconf-set-selections && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y --force-yes oracle-java8-installer oracle-java8-set-default 

RUN wget http://repo1.maven.org/maven2/org/eclipse/jetty/jetty-runner/9.2.7.v20150116/jetty-runner-9.2.7.v20150116.jar -O /root/jetty.jar

EXPOSE 8080

ENV CONTEXT /
ENV JAVA_OPTS -server -XX:+UseConcMarkSweepGC -XX:+UseCompressedOops -XX:+DoEscapeAnalysis 

ADD start.sh /root/start.sh
RUN chmod +x /root/start.sh
CMD ["/root/start.sh"]

ADD target/dwc-services-0.0.21-standalone.war /root/dwc-services.war

