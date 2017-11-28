package ru.gumerbaev.family.auth

import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import org.springframework.beans.factory.annotation.Value

class SSHConnection {
    @Value("\${family.ssh.knownHostsFile}")
    private val S_PATH_FILE_KNOWN_HOSTS = ""

    private val LOCAl_PORT = 3307

    @Value("\${family.ssh.remotePort}")
    private val REMOTE_PORT = 3306

    private val SSH_REMOTE_PORT = 22

    @Value("\${family.ssh.user}")
    private val SSH_USER = ""

    @Value("\${family.ssh.password}")
    private val SSH_PASS = ""

    @Value("\${family.ssh.remoteServer}")
    private val SSH_REMOTE_SERVER = ""

    private val MYSQL_REMOTE_SERVER = "localhost"

    private var sesion: Session // Represents each ssh session

    fun closeSSH() {
        sesion.disconnect()
    }

    init {
        val jSch = JSch()
        jSch.setKnownHosts(S_PATH_FILE_KNOWN_HOSTS)

        sesion = jSch.getSession(SSH_USER, SSH_REMOTE_SERVER, SSH_REMOTE_PORT)
        sesion.setPassword(SSH_PASS)
        sesion.connect() // SSH connection established!

        // By security policy, you must connect through a fowarded port
        sesion.setPortForwardingL(LOCAl_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT)
    }
}