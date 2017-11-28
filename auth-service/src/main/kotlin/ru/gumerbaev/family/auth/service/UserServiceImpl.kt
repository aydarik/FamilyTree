package ru.gumerbaev.family.auth.service

import kotlinx.nosql.mongodb.MongoDB
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import ru.gumerbaev.family.auth.data.User

@Service
class UserServiceImpl @Autowired constructor(val db: MongoDB) : UserService {

    private val log = LoggerFactory.getLogger(javaClass)
    private val encoder = BCryptPasswordEncoder()

    @Autowired
    private val repository: UserRepository? = null

    override fun create(user: User) {

        val existing = repository!!.findOne(user.username)
        Assert.isNull(existing, "user already exists: " + user.username)

        val hash = encoder.encode(user.password)
        user.setPassword(hash)

        repository.save(user)

        log.info("new user has been created: {}", user.username)
    }
}
