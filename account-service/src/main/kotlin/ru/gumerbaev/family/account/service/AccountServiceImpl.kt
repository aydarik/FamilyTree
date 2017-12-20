package ru.gumerbaev.family.account.service

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

    override fun findByName(accountName: String): Account? {
        Assert.hasLength(accountName, "Argument must have length; it must not be null or empty")
        return repository.findByName(accountName)
    }

    override fun create(user: User): Account {
        authClient.createUser(user)

        val existing = repository.findByName(user.username!!)

//        Assert.isNull(existing, "Account already exists: " + user.username)
        if (existing == null) {
            val account = Account()
            account.name = user.username
            account.lastSeen = Date()
            repository.save(account)

            log.info("New account has been created: {}", account.name)
            return account
        } else {
            log.info("Account already exists: {}", existing.name)
            return existing
        }
    }

    override fun saveChanges(name: String, update: Account) {
        val account = repository.findByName(name)!!
        Assert.notNull(account, "Can't find account with name " + name)

        account.note = update.note
        account.lastSeen = Date()
        repository.save(account)

        log.info("Account {} changes has been saved", name)
    }

    override fun delete(name: String) {
        repository.delete(name)
        authClient.deleteUser(name)

        log.info("Account {} has been deleted", name)
    }
}
