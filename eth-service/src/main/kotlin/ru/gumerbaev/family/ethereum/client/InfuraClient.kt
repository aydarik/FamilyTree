package ru.gumerbaev.family.ethereum.client

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(url = "\${infura.url}/\${infura.network}", name = "infura-client")
@RequestMapping(params = ["token=\${infura.key}"],
        produces = [MediaType.APPLICATION_JSON_UTF8_VALUE],
        consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
interface InfuraClient {

    @RequestMapping(method = [RequestMethod.GET], value = ["/methods"])
    fun getMethods(): InfuraResponse

    @RequestMapping(method = [RequestMethod.GET], value = ["/ticker/symbols"])
    fun getSymbols(): InfuraResponse
}
