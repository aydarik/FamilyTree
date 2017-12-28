package ru.gumerbaev.family.ethereum.domain

import java.util.*

class Account {

    var name: String? = null

    var ethAddress: String? = null

    var lastSeen: Date? = null

    var note: String? = null

    override fun toString(): String {
        return "Account {" +
                "name='" + name + "', " +
                "ethAddress='" + ethAddress + "', " +
                "lastSeen='" + lastSeen + "', " +
                "note='" + note + "'}"
    }
}
