package dev.seabat.socket

class SocketClient(val listener: SocketClientListener) {
    fun sendAsync(msg: String) {
        sendJni(this, msg)
    }

    fun callback(msg: String): Unit {
        this.listener.onReceive(msg)
    }

    private external fun sendJni(client: SocketClient, msg: String): Int
}

interface SocketClientListener {
    fun onReceive(msg: String)
}