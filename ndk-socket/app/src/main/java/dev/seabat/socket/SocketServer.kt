package dev.seabat.socket

class SocketServer(val listener: SocketServerListener) {

    private var transportType: String = "TCP"

    fun setTransportType(transportType: String) {
        this.transportType = transportType;
    }

    fun run() {
        runJni(this.transportType,this)
    }

    fun callback(msg: String): Unit {
        this.listener.onReceive(msg)
    }

    private external fun runJni(transportType: String, server: SocketServer): Int
    private external fun closeJni()
}

interface SocketServerListener {
    fun onReceive(msg: String)
}