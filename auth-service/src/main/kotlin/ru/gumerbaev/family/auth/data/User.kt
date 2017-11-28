package ru.gumerbaev.family.auth.data

import kotlinx.nosql.Id
import kotlinx.nosql.mongodb.DocumentSchema
import kotlinx.nosql.string
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

object Users : DocumentSchema<User>("users", User::class) {
    val username = string("username")
    val password = string("password")

    init {
        ensureIndex(text = arrayOf(username, password))
    }
}

data class User(private val username: String, private val password: String) : UserDetails {
    val id: Id<String, Users>? = null

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }

    override fun getAuthorities(): List<GrantedAuthority>? {
        return null
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
