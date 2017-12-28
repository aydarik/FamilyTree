package ru.gumerbaev.family.ethereum.service

import org.ethereum.crypto.ECKey
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import ru.gumerbaev.family.ethereum.client.AccountClient
import ru.gumerbaev.family.ethereum.client.InfuraClient

@Service
class EthereumServiceImpl : EthereumService {

    private val log = LoggerFactory.getLogger(javaClass)
    private val eth = 1000000000000000000

    @Autowired
    private lateinit var accountClient: AccountClient

    @Autowired
    private lateinit var infuraClient: InfuraClient

    override fun getMethods(): String {
        log.info("Requesting methods")
        return infuraClient.getMethods()
    }

    override fun getBalanceOfUser(username: String): Double {
        log.info("Requesting balance: {}", username)

        val account = accountClient.getAccountByName(username)
        Assert.notNull(account, "Account not found: " + username)

        val ethAddress = account!!.ethAddress
        Assert.hasLength(ethAddress, "Account Ethereum address is empty: " + account.name)
        log.info("Ethereum address: {}", ethAddress)

        val params = String.format("[\"%s\", \"latest\"]", ethAddress)
        val response = infuraClient.method("eth_getBalance", params)
        Assert.notNull(response.result, if (response.error != null) {
            response.error!!.message
        } else {
            "Unknown error"
        })

        val result = response.result!!.replaceFirst("0x", "")
        return java.lang.Long.parseLong(result, 16).toDouble() / eth
    }

    override fun generate(): String {
        val key = ECKey()
        return key.toStringWithPrivate()
    }
}
