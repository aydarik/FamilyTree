FROM java:8-jre
MAINTAINER Aydar Gumerbaev <aydar@gumerbaev.ru>

ENV http_proxy $HTTP_PROXY

ADD ./build/libs/eth-service.jar /app/
CMD ["java", "-Xmx200m", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=7005", "-Dsync.fast.enabled=true", "-jar", "/app/eth-service.jar"]

EXPOSE 7000