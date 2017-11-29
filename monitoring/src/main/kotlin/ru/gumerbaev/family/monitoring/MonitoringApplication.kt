package ru.gumerbaev.family.monitoring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream

@SpringBootApplication
@EnableTurbineStream
@EnableHystrixDashboard
open class MonitoringApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(MonitoringApplication::class.java, *args)
        }
    }
}
