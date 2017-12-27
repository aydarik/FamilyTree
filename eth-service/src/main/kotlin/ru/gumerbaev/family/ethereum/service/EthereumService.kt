package ru.gumerbaev.family.ethereum.service

import ru.gumerbaev.family.ethereum.client.InfuraResponse

interface EthereumService {

    fun getMethods(): InfuraResponse

    fun getSymbols(): InfuraResponse
}
