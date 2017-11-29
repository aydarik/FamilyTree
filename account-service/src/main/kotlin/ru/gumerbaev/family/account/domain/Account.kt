package ru.gumerbaev.family.account.domain

import org.codehaus.jackson.annotate.JsonIgnoreProperties
import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "accounts")
@JsonIgnoreProperties(ignoreUnknown = true)
class Account {

    @Id
    var name: String? = null

    var lastSeen: Date? = null

    @Length(min = 0, max = 20000)
    var note: String? = null
}
