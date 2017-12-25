package ru.gumerbaev.family.auth.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import ru.gumerbaev.family.auth.domain.User
import ru.gumerbaev.family.auth.repository.UserRepository

@Service
class UserServiceImpl : UserService {

    private val log = LoggerFactory.getLogger(javaClass)

    private val encoder = BCryptPasswordEncoder()

    @Autowired
    private lateinit var repository: UserRepository

    override fun create(user: User) {
        val existing = repository.exists(user.username)
        Assert.isTrue(!existing, "User already exists: " + user.username)

        val hash = encoder.encode(user.password)
        user.setPassword(hash)
        repository.save(user)

        log.info("New user has been created: {}", user.username)
    }

    override fun delete(username: String) {
        repository.delete(username)
        log.info("User has been deleted: {}", username)
    }
}
