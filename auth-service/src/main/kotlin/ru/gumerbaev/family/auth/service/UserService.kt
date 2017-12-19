package ru.gumerbaev.family.auth.service

import ru.gumerbaev.family.auth.domain.User

interface UserService {

    fun create(user: User)

    fun delete(username: String)
}
