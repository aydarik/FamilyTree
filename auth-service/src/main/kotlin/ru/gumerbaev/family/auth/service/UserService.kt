package ru.gumerbaev.family.auth.service

import ru.gumerbaev.family.auth.data.User

interface UserService {

    fun create(user: User)
}
