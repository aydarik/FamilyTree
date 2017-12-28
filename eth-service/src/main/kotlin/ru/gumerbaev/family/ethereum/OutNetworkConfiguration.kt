package ru.gumerbaev.family.ethereum

import feign.Client
import feign.okhttp.OkHttpClient
import okhttp3.Authenticator
import okhttp3.Credentials
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit

class OutNetworkConfiguration {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun feignClient(): Client {
        return getProxyClient()
    }

    private fun getProxyClient(): OkHttpClient {
        var value = System.getenv("http_proxy") ?: return OkHttpClient()
        log.debug(value)

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

            log.debug("proxyUser: {}", authUser)
            log.debug("proxyPassword: *****")
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

        log.debug("proxyHost: {}", serverHost)
        log.debug("proxyPort: {}", serverPort)

        return OkHttpClient(client)
    }
}