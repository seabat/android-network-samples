package dev.seabat.socket

class SocketClient(val listener: SocketClientListener) {
    private var transportType: String = "TCP"

    fun setTransportType(transportType: String) {
        this.transportType = transportType;
    }

    fun sendAsync(msg: String) {
        sendJni(this.transportType,this, msg)
    }

    fun callback(msg: String): Unit {
        this.listener.onReceive(msg)
    }

    private external fun sendJni(transportType: String, client: SocketClient, msg: String): Int
}

interface SocketClientListener {
    fun onReceive(msg: String)
}