package dev.seabat.socket

class SocketServer(val listener: SocketServerListener) {

    private var transportType: String = "TCP"

    fun changeTransportType(transportType: String) {
        this.transportType = transportType;
//        this.closeJni(); //TODO: 待ち状態の socket を閉じる実装にまだなっていない
        this.runJni(this.transportType,this)
    }

    fun run() {
        this.runJni(this.transportType,this)
    }

    fun callback(msg: String): Unit {
        this.listener.onReceive(msg)
    }

    private external fun runJni(transportType: String, server: SocketServer): Int
    private external fun closeJni(): Unit
}

interface SocketServerListener {
    fun onReceive(msg: String)
}