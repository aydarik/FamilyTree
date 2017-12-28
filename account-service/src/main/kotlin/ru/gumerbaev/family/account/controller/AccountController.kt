package ru.gumerbaev.family.account.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.oauth2.common.exceptions.InvalidClientException
import org.springframework.web.bind.annotation.*
import ru.gumerbaev.family.account.domain.Account
import ru.gumerbaev.family.account.domain.User
import ru.gumerbaev.family.account.service.AccountService
import java.security.Principal
import javax.validation.Valid

@RestController
class AccountController {

    @Autowired
    private lateinit var accountService: AccountService

    @RequestMapping(path = ["/"], method = [RequestMethod.POST])
    fun createNewAccount(@Valid @RequestBody user: User): Account {
        return accountService.create(user)
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(path = ["/{name}"], method = [RequestMethod.GET])
    fun getAccountByName(@PathVariable name: String): Account? {
        return accountService.findByName(name)
    }

    @RequestMapping(path = ["/current"], method = [RequestMethod.GET])
    fun getCurrentAccount(principal: Principal): Account {
        try {
            return accountService.findByName(principal.name)!!
        } catch (npe: KotlinNullPointerException) {
            throw InvalidClientException("Current account not found (unexpected deletion?)")
        }
    }

    @RequestMapping(path = ["/current"], method = [RequestMethod.PUT])
    fun saveCurrentAccount(principal: Principal, @Valid @RequestBody account: Account) {
        accountService.saveChanges(principal.name, account)
    }

    @RequestMapping(path = ["/current"], method = [RequestMethod.DELETE])
    fun deleteAccount(principal: Principal) {
        accountService.delete(principal.name)
    }
}
