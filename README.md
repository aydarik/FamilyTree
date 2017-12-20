# FamilyTree
Docker / Microservices test project.

*Based on [PiggyMetrics](https://github.com/sqshq/PiggyMetrics).*

### Built on
- Kotlin
- Gradle
- Spring Cloud
- MongoDB

### How to build
Execute `clean build` gradle tasks on *family-tree* project.

### How to start
Run `docker-compose -f docker-compose.yml -f docker-compose.dev.yml up -d --build`.

### How to use
Open http://localhost:80 in any browser.

### Other endpoints
- http://localhost:8761 - Eureka Dashboard
- http://localhost:9000/hystrix - Hystrix Dashboard (paste Turbine stream link on the form)
- http://localhost:8989 - Turbine stream (source for the Hystrix Dashboard)
- http://localhost:15672 - RabbitMQ management (default login/password: guest/guest)
