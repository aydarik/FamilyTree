package ru.gumerbaev.family.account.domain

import org.hibernate.validator.constraints.Length

import javax.validation.constraints.NotNull

class User {

    @NotNull
    @Length(min = 3, max = 20)
    var username: String? = null

    @NotNull
    @Length(min = 6, max = 40)
    var password: String? = null
}
