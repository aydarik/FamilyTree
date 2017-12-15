package ru.gumerbaev.family.auth.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import ru.gumerbaev.family.auth.domain.User
import ru.gumerbaev.family.auth.service.UserService
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @RequestMapping(value = "/current", method = arrayOf(RequestMethod.GET))
    fun getUser(principal: Principal): Principal {
        return principal
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun createUser(@Valid @RequestBody user: User) {
        userService.create(user)
    }
}
