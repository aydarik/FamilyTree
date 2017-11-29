package ru.gumerbaev.family.account.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.gumerbaev.family.account.domain.Account

@Repository
interface AccountRepository : CrudRepository<Account, String> {

    fun findByName(name: String): Account

}
