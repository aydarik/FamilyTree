package ru.gumerbaev.family.ethereum.blockchain

import org.ethereum.core.Block
import org.ethereum.core.TransactionReceipt
import org.ethereum.facade.Ethereum
import org.ethereum.listener.EthereumListener.SyncState
import org.ethereum.listener.EthereumListenerAdapter
import org.ethereum.util.BIUtil
import org.slf4j.LoggerFactory
import java.math.BigInteger

class EthereumListener(private val ethereum: Ethereum) : EthereumListenerAdapter() {

    private val log = LoggerFactory.getLogger(javaClass)

    private var syncDone = false
    private val thou = 1000

    private fun out(s: String) {
        log.info(s)
    }

    private fun calcNetHashRate(block: Block): String {
        if (block.getNumber() > thou) {
            var timeDelta: Long = 0
            for (i in 0 until thou) {
                val parent = ethereum
                        .blockchain
                        .getBlockByHash(block.getParentHash())
                timeDelta += Math.abs(block.getTimestamp() - parent.timestamp)
            }
            return block.difficultyBI
                    .divide(BIUtil.toBI(timeDelta / thou))
                    .divide(BigInteger.valueOf(1000000000))
                    .toDouble().toString() + " GH/s"
        }
        return "Net hash rate not available"
    }

    override fun onBlock(block: Block, receipts: List<TransactionReceipt>) {
        if (syncDone) {
            out("Net hash rate: " + calcNetHashRate(block))
            out("Block difficulty: " + block.getDifficultyBI().toString())
            out("Block transactions: " + block.getTransactionsList().toString())
            out("Best block (last block): " + ethereum
                    .blockchain
                    .bestBlock.toString())
            out("Total difficulty: " + ethereum
                    .blockchain
                    .totalDifficulty.toString())
        }
    }

    override fun onSyncDone(state: SyncState?) {
        out("Sync state: " + state!!)
        if (!syncDone) {
            out(" ** SYNC DONE ** ")
            syncDone = true
        }
    }
}