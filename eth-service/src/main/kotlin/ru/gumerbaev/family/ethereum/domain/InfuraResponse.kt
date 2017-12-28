package ru.gumerbaev.family.ethereum.domain

class InfuraResponse {

    class InfuraError {

        val code: Int? = null

        val message: String? = null
    }

    var jsonrpc: String? = null

    var id: Int? = null

    var result: String? = null

    var error: InfuraError? = null
}