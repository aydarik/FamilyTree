FROM java:8-jre
MAINTAINER Aydar Gumerbaev <aydar@gumerbaev.ru>

ADD ./build/libs/account-service.jar /app/
CMD ["java", "-Xmx200m", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/app/account-service.jar"]

EXPOSE 6000