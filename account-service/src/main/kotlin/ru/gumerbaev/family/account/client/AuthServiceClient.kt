package ru.gumerbaev.family.account.client

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import ru.gumerbaev.family.account.domain.User

@FeignClient(name = "auth-service")
interface AuthServiceClient {

    @RequestMapping(method = arrayOf(RequestMethod.POST), value = "/uaa/users", consumes = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun createUser(user: User)
}
