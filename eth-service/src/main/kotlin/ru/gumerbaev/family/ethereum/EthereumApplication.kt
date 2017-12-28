package ru.gumerbaev.family.ethereum

import feign.RequestInterceptor
import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.OkHttpClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import java.io.IOException
import java.lang.String.format
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit


@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
@EnableOAuth2Client
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties
class EthereumApplication : ResourceServerConfigurerAdapter() {

    private val log = LoggerFactory.getLogger(EthereumApplication::class.java)

    @Value("\${infura.network}")
    private lateinit var infuraNetwork: String

    @Value("\${infura.key}")
    private lateinit var infuraKey: String

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(EthereumApplication::class.java, *args)
        }

        @JvmStatic
        fun getProxyClient(): OkHttpClient {
            var value = System.getenv("http_proxy")
            if (value.isEmpty()) return OkHttpClient()
            System.out.println(value)

            val prefix = value.indexOf("//", 0)
            if (prefix >= 0) {
                value = value.substring(prefix + 2)
            }
            value = value.replace("/", "")

            val split = value.split("@")
            val hasCredentials = split.size > 1

            var proxyAuthenticator: Authenticator? = null
            if (hasCredentials) {
                val credentialsSplit = split[0].split(":")
                val authUser = credentialsSplit[0]
                val authPassword = credentialsSplit[1]

                proxyAuthenticator = object : Authenticator {
                    @Throws(IOException::class)
                    override fun authenticate(route: okhttp3.Route, response: okhttp3.Response): okhttp3.Request {
                        val credential = Credentials.basic(authUser, authPassword)
                        return response.request().newBuilder()
                                .header("Proxy-Authorization", credential)
                                .build()
                    }
                }

                System.out.println("proxyUser: " + authUser)
                System.out.println("proxyPassword: proxyPassword: *****")
            }

            val server = split[if (hasCredentials) 1 else 0]
            val serverSplit = server.split(":")
            val serverHost = serverSplit[0]
            val serverPort = serverSplit[1]

            val builder = okhttp3.OkHttpClient.Builder()
            val client = builder
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress(serverHost, serverPort.toInt())))
                    .proxyAuthenticator(proxyAuthenticator ?: Authenticator.NONE)
                    .build()

            System.out.println("proxyHost: " + serverHost)
            System.out.println("proxyPort: " + serverPort)

            return client
        }
    }

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    fun clientCredentialsResourceDetails(): ClientCredentialsResourceDetails {
        return ClientCredentialsResourceDetails()
    }

    @Bean
    fun oauth2FeignRequestInterceptor(): RequestInterceptor {
        return OAuth2FeignRequestInterceptor(DefaultOAuth2ClientContext(), clientCredentialsResourceDetails())
    }

    @Bean
    fun clientCredentialsRestTemplate(): OAuth2RestTemplate {
        return OAuth2RestTemplate(clientCredentialsResourceDetails())
    }

    @Bean
    fun web3(): Web3j {
        // We start by creating a new web3j instance to connect to remote nodes on the network.
        val web3 = Web3j.build(HttpService(format("https://%s.infura.io/%s", infuraNetwork, infuraKey),
                getProxyClient(), false))
        log.info("Connected to Ethereum client version: {}", web3.web3ClientVersion().send().web3ClientVersion)

        return web3
    }
}
