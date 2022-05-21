package dev.seabat.socket

class SocketServer(val listener: SocketServerListener) {

    fun run() {
        runJni(this)
    }

    fun callback(msg: String): Unit {
        this.listener.onReceive(msg)
    }

    private external fun runJni(server: SocketServer): Int
    private external fun closeJni()
}

interface SocketServerListener {
    fun onReceive(msg: String)
}