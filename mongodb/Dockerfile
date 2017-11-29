FROM mongo:3
MAINTAINER Aydar Gumerbaev <aydar@gumerbaev.ru>

ADD init.sh /init.sh
ADD ./dump /

ENV http_proxy $HTTP_PROXY
ENV https_proxy $HTTPS_PROXY

RUN \
	chmod +x /init.sh && \
	apt-get update && apt-get dist-upgrade -y && \
	apt-get install psmisc -y -q && \
	apt-get autoremove -y && apt-get clean && \
	rm -rf /var/cache/* && rm -rf /var/lib/apt/lists/*

ENTRYPOINT ["/init.sh"]