package ru.gumerbaev.family.auth.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.gumerbaev.family.auth.domain.User

@Repository
interface UserRepository : CrudRepository<User, String> {
    fun findByUsername(): User
}