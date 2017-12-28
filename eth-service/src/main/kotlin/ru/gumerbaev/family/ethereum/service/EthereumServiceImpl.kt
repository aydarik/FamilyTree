package ru.gumerbaev.family.ethereum.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.utils.Convert
import ru.gumerbaev.family.ethereum.client.AccountClient

@Service
class EthereumServiceImpl : EthereumService {

    private val log = LoggerFactory.getLogger(javaClass)

    @Value("\${infura.network}")
    private lateinit var infuraNetwork: String

    @Autowired
    private lateinit var web3: Web3j

    @Autowired
    private lateinit var accountClient: AccountClient

    override fun getNetwork(): String {
        return infuraNetwork
    }

    override fun getGethClient(): String {
        return web3.web3ClientVersion().send().web3ClientVersion
    }

    override fun getBalanceOfUser(username: String): Double {
        log.info("Requesting balance: {}", username)

        val account = accountClient.getAccountByName(username)
        Assert.notNull(account, "Account not found: " + username)

        val ethAddress = account!!.ethAddress
        Assert.hasLength(ethAddress, "Account Ethereum address is empty: " + account.name)
        log.info("Ethereum address: {}", ethAddress)

        // send asynchronous requests to get balance
        val ethGetBalance = web3
                .ethGetBalance(ethAddress, DefaultBlockParameterName.LATEST)
                .sendAsync()
                .get()

        val wei = ethGetBalance.balance
        return Convert.fromWei(wei.toBigDecimal(), Convert.Unit.ETHER).toDouble()
    }
}
