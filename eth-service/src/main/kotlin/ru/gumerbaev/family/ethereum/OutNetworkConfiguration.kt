package ru.gumerbaev.family.ethereum

import feign.Client
import org.springframework.context.annotation.Bean

@Deprecated("Use in configuration of FeignClient.", level = DeprecationLevel.WARNING)
class OutNetworkConfiguration {

    @Bean
    fun feignClient(): Client {
        return feign.okhttp.OkHttpClient(EthereumApplication.getProxyClient())
    }
}