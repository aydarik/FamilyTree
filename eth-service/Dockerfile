FROM java:8-jre
MAINTAINER Aydar Gumerbaev <aydar@gumerbaev.ru>

ENV http_proxy $HTTP_PROXY

ADD ./build/libs/eth-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/eth-service.jar"]

EXPOSE 7000