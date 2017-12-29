package ru.gumerbaev.family.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore
import ru.gumerbaev.family.auth.service.security.MongoUserDetailsService

@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableCircuitBreaker
class AuthApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(AuthApplication::class.java, *args)
        }
    }

    @Configuration
    @EnableWebSecurity
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected class WebSecurityConfig : WebSecurityConfigurerAdapter() {

        @Autowired
        private lateinit var userDetailsService: MongoUserDetailsService

        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            http
                    .authorizeRequests().anyRequest().authenticated()
                    .and()
                    .csrf().disable()
        }

        @Throws(Exception::class)
        override fun configure(auth: AuthenticationManagerBuilder) {
            auth.userDetailsService(userDetailsService).passwordEncoder(BCryptPasswordEncoder())
        }

        @Bean
        @Throws(Exception::class)
        override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()
    }

    @Configuration
    @EnableAuthorizationServer
    protected class OAuth2AuthorizationConfig : AuthorizationServerConfigurerAdapter() {

        private val tokenStore = InMemoryTokenStore()

        @Autowired
        @Qualifier("authenticationManagerBean")
        private lateinit var authenticationManager: AuthenticationManager

        @Autowired
        private lateinit var userDetailsService: MongoUserDetailsService

        @Autowired
        private lateinit var env: Environment

        @Throws(Exception::class)
        override fun configure(clients: ClientDetailsServiceConfigurer) {

            // TODO persist clients details

            clients.inMemory()
                    .withClient("browser")
                    .authorizedGrantTypes("password", "refresh_token")
                    .scopes("ui")
                    .accessTokenValiditySeconds(3600)
                    .and()
                    .withClient("account-service")
                    .secret(env.getProperty("ACCOUNT_SERVICE_PASSWORD"))
                    .authorizedGrantTypes("client_credentials", "refresh_token")
                    .scopes("server")
                    .accessTokenValiditySeconds(3600)
                    .and()
                    .withClient("eth-service")
                    .secret(env.getProperty("ETHEREUM_SERVICE_PASSWORD"))
                    .authorizedGrantTypes("client_credentials", "refresh_token")
                    .scopes("server")
                    .accessTokenValiditySeconds(3600)
        }

        @Throws(Exception::class)
        override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
            endpoints
                    .tokenStore(tokenStore)
                    .authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService)
        }

        @Throws(Exception::class)
        override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
            oauthServer
                    .tokenKeyAccess("permitAll()")
                    .checkTokenAccess("isAuthenticated()")
        }
    }
}
