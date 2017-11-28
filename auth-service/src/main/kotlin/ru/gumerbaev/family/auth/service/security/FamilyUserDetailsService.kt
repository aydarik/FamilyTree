package ru.gumerbaev.family.auth.service.security

import kotlinx.nosql.mongodb.MongoDB
import kotlinx.nosql.text
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.gumerbaev.family.auth.data.User
import ru.gumerbaev.family.auth.data.Users

@Service
class FamilyUserDetailsService @Autowired constructor(val db: MongoDB) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        var user: User? = null
        db.withSession {
            user = Users.find { text(username) }.single()
        }

        return user!!
    }
}
