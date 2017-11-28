FROM java:8-jre
MAINTAINER Aydar Gumerbaev <aydar@gumerbaev.ru>

ADD ./build/libs/auth-service.jar /app/
ADD ${MYSQL_SSH_KNOWN_HOSTS_FILE} /.ssh/known_hosts
CMD ["java", "-Xmx200m", "-jar", "/app/auth-service.jar"]

EXPOSE 5000