package ru.gumerbaev.family.ethereum.service

interface EthereumService {

    fun getMethods(): String

    fun getBalanceOfUser(username: String): Double

    fun generate(): String
}
