package ru.gumerbaev.family.ethereum.service

import org.codehaus.jettison.json.JSONObject

interface EthereumService {

    fun getMethods(): JSONObject?

    fun getSymbols(): JSONObject?
}
