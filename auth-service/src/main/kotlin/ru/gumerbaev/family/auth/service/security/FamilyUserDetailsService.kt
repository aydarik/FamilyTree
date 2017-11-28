package ru.gumerbaev.family.auth.service.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.gumerbaev.family.auth.repository.UserRepository

@Service
class FamilyUserDetailsService : UserDetailsService {

    @Autowired
    private val repository: UserRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        return repository!!.findOne(username) ?: throw UsernameNotFoundException(username)
    }
}
