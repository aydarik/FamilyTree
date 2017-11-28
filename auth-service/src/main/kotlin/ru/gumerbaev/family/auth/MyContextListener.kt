package ru.gumerbaev.family.auth

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener

@Configuration
@WebListener
//@ConfigurationProperties(prefix = "family")
open class MyContextListener : ServletContextListener {
    @Value("\${family.ssh.enabled}")
    var tunnel: Boolean = false
    var ssh: SSHConnection? = null

    override fun contextInitialized(sce: ServletContextEvent) {
        println("Context initialized")
        if (tunnel) {
            try {
                ssh = SSHConnection()
            } catch (e: Throwable) {
                e.printStackTrace() // Error connecting SSH server
            }
        }
    }

    override fun contextDestroyed(sce: ServletContextEvent) {
        println("Context destroyed")
        ssh?.closeSSH() // Disconnect
    }
}