package ru.gumerbaev.family.ethereum.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import ru.gumerbaev.family.ethereum.service.EthereumService
import java.security.Principal

@RestController
class EthereumController {

    @Autowired
    private lateinit var ethService: EthereumService

    @RequestMapping(path = ["/network"], method = [RequestMethod.GET])
    fun getNetwork(): String {
        return ethService.getNetwork()
    }

    @RequestMapping(path = ["/geth"], method = [RequestMethod.GET])
    fun getGethClient(): String {
        return ethService.getGethClient()
    }

    @PreAuthorize("#name.equals('admin')")
    @RequestMapping(path = ["/balance/{username}"], method = [RequestMethod.GET])
    fun getBalanceOfUser(@PathVariable username: String): Double {
        return ethService.getBalanceOfUser(username)
    }

    @RequestMapping(path = ["/balance"], method = [RequestMethod.GET])
    fun getBalance(principal: Principal): Double {
        return ethService.getBalanceOfUser(principal.name)
    }
}
