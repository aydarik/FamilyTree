package ru.gumerbaev.family.auth.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import ru.gumerbaev.family.auth.domain.User
import ru.gumerbaev.family.auth.repository.UserRepository

@Service
class UserServiceImpl : UserService {

    private val log = LoggerFactory.getLogger(javaClass)

    private val encoder = BCryptPasswordEncoder()

    @Autowired
    private lateinit var repository: UserRepository

    override fun create(user: User) {
        val existing = repository.findOne(user.username)

//        Assert.isNull(existing, "user already exists: " + user.username)
        if (existing == null) {
            val hash = encoder.encode(user.password)
            user.setPassword(hash)

            repository.save(user)
            log.info("New user has been created: {}", user.username)
        } else {
            log.info("User already exists: {}", user.username)
        }
    }

    override fun delete(username: String) {
        repository.delete(username)
        log.info("User has been deleted: {}", username)
    }
}
