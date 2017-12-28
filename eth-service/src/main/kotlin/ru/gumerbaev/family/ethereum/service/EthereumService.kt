package ru.gumerbaev.family.ethereum.service

interface EthereumService {

    fun getNetwork(): String

    fun getGethClient(): String

    fun getBalanceOfUser(username: String): Double
}
