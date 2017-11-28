package ru.gumerbaev.family.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
import ru.gumerbaev.family.auth.service.security.FamilyUserDetailsService

@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
open class AuthApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(AuthApplication::class.java, *args)
        }
    }

    @Configuration
    @EnableWebSecurity
    protected open class WebSecurityConfig : WebSecurityConfigurerAdapter() {

        @Autowired
        private val userDetailsService: FamilyUserDetailsService? = null

        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            http.authorizeRequests().anyRequest().authenticated()
                    .and().csrf().disable()
        }

        @Throws(Exception::class)
        override fun configure(auth: AuthenticationManagerBuilder) {
            auth.userDetailsService(userDetailsService)
                    .passwordEncoder(BCryptPasswordEncoder())
        }

        @Bean
        @Throws(Exception::class)
        override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()
    }

    @Configuration
    @EnableAuthorizationServer
    protected open class OAuth2AuthorizationConfig : AuthorizationServerConfigurerAdapter() {

        private val tokenStore = InMemoryTokenStore()

        @Autowired
        @Qualifier("authenticationManagerBean")
        private val authenticationManager: AuthenticationManager? = null

        @Autowired
        private val userDetailsService: FamilyUserDetailsService? = null

//        @Autowired
//        private val env: Environment? = null

        @Throws(Exception::class)
        override fun configure(clients: ClientDetailsServiceConfigurer) {

            // TODO persist clients details

            clients.inMemory()
                    .withClient("browser")
                    .authorizedGrantTypes("refresh_token", "password")
                    .scopes("ui")
//                    .and()
//                    .withClient("account-service")
//                    .secret(env!!.getProperty("ACCOUNT_SERVICE_PASSWORD"))
//                    .authorizedGrantTypes("client_credentials", "refresh_token")
//                    .scopes("server")
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
