package ru.gumerbaev.family.account.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import ru.gumerbaev.family.account.client.AuthServiceClient
import ru.gumerbaev.family.account.domain.Account
import ru.gumerbaev.family.account.domain.User
import ru.gumerbaev.family.account.repository.AccountRepository
import java.util.*

@Service
class AccountServiceImpl : AccountService {

    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var authClient: AuthServiceClient

    @Autowired
    private lateinit var repository: AccountRepository

    @HystrixCommand
    override fun findByName(accountName: String): Account? {
        Assert.hasLength(accountName, "Argument must have length; it must not be null or empty")
        return repository.findByName(accountName)
    }

    @HystrixCommand
    override fun create(user: User): Account {
        val existing = repository.findByName(user.username!!)
        Assert.isNull(existing, "Account already exists: " + user.username)

        authClient.createUser(user)

        val account = Account()
        account.name = user.username
        account.lastSeen = Date()
        repository.save(account)

        log.info("New account has been created: {}", account.name)
        return account
    }

    @HystrixCommand
    override fun saveChanges(name: String, update: Account) {
        log.info("Account update: {}", update)

        val account = repository.findByName(name)!!
        Assert.notNull(account, "Can't find account with name " + name)

        if (update.ethAddress != null) {
            account.ethAddress = update.ethAddress
            log.info("ethAddress: {}", account.ethAddress)
        }
        if (update.note != null) {
            account.note = update.note
            log.info("note: {}", account.note)
        }
        if (update.lastSeen != null) {
            account.lastSeen = update.lastSeen
        } else {
            account.lastSeen = Date()
        }
        log.info("lastSeen: {}", account.lastSeen)

        repository.save(account)

        log.info("Account {} changes has been saved", name)
    }

    @HystrixCommand
    override fun delete(name: String) {
        val account = repository.findByName(name)
        account?.let { repository.delete(it) }
        authClient.deleteUser(name)

        log.info("Account {} has been deleted", name)
    }
}
