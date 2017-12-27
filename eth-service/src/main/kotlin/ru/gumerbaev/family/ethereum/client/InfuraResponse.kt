package ru.gumerbaev.family.ethereum.client

class InfuraResponse(val jsonrpc: String, val id: Int, val result: String?, val error: InfuraError?) {

    class InfuraError(val code: Int, val message: String)
}