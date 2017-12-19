FROM java:8-jre
MAINTAINER Aydar Gumerbaev <aydar@gumerbaev.ru>

ADD ./build/libs/account-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/account-service.jar"]

EXPOSE 6000