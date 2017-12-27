package ru.gumerbaev.family.ethereum.client

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(url = "\${infura.url}", name = "infura-client")
@RequestMapping(params = ["token=\${infura.key}"], produces = [MediaType.TEXT_PLAIN_VALUE])
interface InfuraClient {

    @RequestMapping(method = [RequestMethod.GET], value = ["/\${infura.network}/methods"])
    fun getMethods(): String

    @RequestMapping(method = [RequestMethod.GET], value = ["/ticker/symbols"])
    fun getSymbols(): String
}
