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

    @RequestMapping(path = ["/methods"], method = [RequestMethod.GET])
    fun getMethods(principal: Principal): String {
        return ethService.getMethods()
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

    @RequestMapping(path = ["/generate"], method = [RequestMethod.GET])
    fun generate(): String {
        return ethService.generate()
    }
}
