package ru.gumerbaev.family.ethereum.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import ru.gumerbaev.family.ethereum.domain.Account

@FeignClient("account-service")
interface AccountClient {

    @RequestMapping(method = [RequestMethod.GET], path = ["/accounts/{name}"])
    fun getAccountByName(@PathVariable("name") username: String): Account?
}
