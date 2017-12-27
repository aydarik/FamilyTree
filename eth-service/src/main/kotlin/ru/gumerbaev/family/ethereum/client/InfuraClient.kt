package ru.gumerbaev.family.ethereum.client

import org.codehaus.jettison.json.JSONObject
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(url = "\${infura.url}", name = "infura-client")
@RequestMapping(params = ["token=\${infura.key}"])
interface InfuraClient {

    @RequestMapping(method = [RequestMethod.GET], value = ["/\${infura.network}/methods"]/*, consumes = [MediaType.TEXT_PLAIN_VALUE]*/)
    fun getMethods(): JSONObject

    @RequestMapping(method = [RequestMethod.GET], value = ["/ticker/symbols"])
    fun getSymbols(): JSONObject
}
