package ru.gumerbaev.family.ethereum.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import ru.gumerbaev.family.ethereum.client.InfuraResponse
import ru.gumerbaev.family.ethereum.service.EthereumService
import java.security.Principal

@RestController
@RequestMapping(produces = [MediaType.TEXT_PLAIN_VALUE])
class EthereumController {

    @Autowired
    private lateinit var ethService: EthereumService

    @RequestMapping(path = ["/network/methods"], method = [RequestMethod.GET])
    fun getMethods(principal: Principal): InfuraResponse {
        return ethService.getMethods()
    }

    @RequestMapping(path = ["ticker/symbols"], method = [RequestMethod.GET])
    fun getSymbols(principal: Principal): InfuraResponse {
        return ethService.getSymbols()
    }
}
