FROM alpine:3.5

ENV http_proxy $HTTP_PROXY
ENV https_proxy $HTTPS_PROXY

RUN apk --update add python py-pip && \
    pip install elasticsearch-curator==5.4.1

# add tasks scripts
ADD ./tasks /etc/periodic/daily
RUN chmod a+x /etc/periodic/daily/*

CMD ["crond", "-f", "-l", "8"]
