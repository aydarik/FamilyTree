[![Build Status](https://travis-ci.org/aydarik/family-tree.svg?branch=master)](https://travis-ci.org/aydarik/family-tree)

# FamilyTree

Docker / Microservices test project.

*Based on [PiggyMetrics](https://github.com/sqshq/PiggyMetrics).*

### Built on

- Kotlin
- Gradle
- Spring Cloud
- MongoDB

### CI/CD workflow

- [Travis  CI](https://travis-ci.org/aydarik/family-tree)
- Docker Hub


### How to build

Execute `clean build` gradle tasks on *family-tree* project.

### How to start

Be sure, that you have exported environment variables:

```
export CONFIG_SERVICE_PASSWORD="1"
export ACCOUNT_SERVICE_PASSWORD="1"
export MONGODB_PASSWORD="1"
```

> Instead of "**1**" it can be any password you want for each service.

##### Production

`docker-compose up -d`

##### Development

`docker-compose -f docker-compose.yml -f docker-compose.dev.yml up -d`

### How to use

Open http://localhost:80 in any browser.

### Important endpoints

- http://localhost:80 - Gateway
- http://localhost:8761 - Eureka Dashboard
- http://localhost:9000/hystrix - Hystrix Dashboard (paste Turbine stream link on the form)
- http://localhost:8989 - Turbine stream (source for the Hystrix Dashboard)
- http://localhost:15672 - RabbitMQ management (default login/password: guest/guest)

# ELK Stack

Elasticsearch, Logstash, Kibana with Filebeat.

### Endpoints

- http://localhost:5601 - Kibana