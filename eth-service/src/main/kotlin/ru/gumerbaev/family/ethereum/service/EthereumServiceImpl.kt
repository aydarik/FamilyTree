package ru.gumerbaev.family.ethereum.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.gumerbaev.family.ethereum.client.InfuraClient

@Service
class EthereumServiceImpl : EthereumService {

    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var infuraClient: InfuraClient

    override fun getMethods(): String? {
        log.info("Requesting methods")
        return infuraClient.getMethods()
    }

    override fun getSymbols(): String? {
        log.info("Requesting symbols")
        return infuraClient.getSymbols()
    }
}
