package ru.gumerbaev.family.ethereum.client

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import ru.gumerbaev.family.ethereum.OutNetworkConfiguration
import ru.gumerbaev.family.ethereum.domain.InfuraResponse

@FeignClient(url = "\${infura.url}/\${infura.network}", name = "infura-client",
        configuration = [OutNetworkConfiguration::class])
@RequestMapping(params = ["token=\${infura.key}"])
interface InfuraClient {

    @RequestMapping(method = [RequestMethod.GET], path = ["/methods"], produces = [MediaType.TEXT_PLAIN_VALUE])
    fun getMethods(): String

    @RequestMapping(method = [RequestMethod.GET], path = ["/{method}"], produces = [MediaType.TEXT_PLAIN_VALUE])
    fun method(@PathVariable("method") method: String, @RequestParam("params") params: String): InfuraResponse
}
