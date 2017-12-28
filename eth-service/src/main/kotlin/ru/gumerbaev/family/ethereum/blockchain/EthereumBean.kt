package ru.gumerbaev.family.ethereum.blockchain

import org.ethereum.core.Block
import org.ethereum.facade.Ethereum
import org.ethereum.facade.EthereumFactory
import java.math.BigInteger

class EthereumBean {
    private var ethereum: Ethereum? = null

    fun start() {
        ethereum = EthereumFactory.createEthereum()
        ethereum!!.addListener(EthereumListener(ethereum!!))
    }

    fun getBestBlock(): Block {
        return ethereum!!.blockchain.bestBlock
    }

    fun getTotalDifficulty(): BigInteger {
        return ethereum!!.blockchain.totalDifficulty
    }
}