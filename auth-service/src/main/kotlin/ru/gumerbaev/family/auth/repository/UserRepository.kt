package ru.gumerbaev.family.auth.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.gumerbaev.family.auth.domain.User

@Repository
interface UserRepository : JpaRepository<User, String>
