package ru.gumerbaev.family.ethereum

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices
import ru.gumerbaev.family.ethereum.service.security.CustomUserInfoTokenServices

@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
@EnableOAuth2Client
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
class EthereumApplication : ResourceServerConfigurerAdapter() {

    @Autowired
    private lateinit var sso: ResourceServerProperties

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(EthereumApplication::class.java, *args)
        }
    }

    @Bean
    fun tokenServices(): ResourceServerTokenServices {
        return CustomUserInfoTokenServices(sso.userInfoUri, sso.clientId)
    }
}
