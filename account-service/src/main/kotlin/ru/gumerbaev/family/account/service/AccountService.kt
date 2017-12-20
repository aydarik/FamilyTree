package ru.gumerbaev.family.account.service

import ru.gumerbaev.family.account.domain.Account
import ru.gumerbaev.family.account.domain.User

interface AccountService {

    /**
     * Finds account by given name
     *
     * @param accountName
     * @return found account
     */
    fun findByName(accountName: String): Account?

    /**
     * Checks if account with the same name already exists
     * Creates new account with default parameters
     *
     * @param user
     * @return created account
     */
    fun create(user: User): Account

    /**
     * Validates and applies incoming account updates
     *
     * @param name
     * @param update
     */
    fun saveChanges(name: String, update: Account)

    /**
     * Deletes account
     *
     * @param name
     */
    fun delete(name: String)
}
