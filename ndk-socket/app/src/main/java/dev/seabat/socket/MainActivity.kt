package dev.seabat.socket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import dev.seabat.socket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var server: SocketServer
    private lateinit var client: SocketClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()
        binding.sendButton.setOnClickListener {
            client.sendAsync("Hello")
        }


        server = SocketServer(object : SocketServerListener {
            override fun onReceive(msg: String) {
                binding.sampleText.text = "From Server: $msg"
                Log.v("SocketServer", "receive:$msg")
            }
        })
        server.run()

        client = SocketClient(object : SocketClientListener {
            override fun onReceive(msg: String) {
                binding.sampleText.text = "From Client: $msg"
                Log.v("SocketClient", "receive:$msg")
            }
        })
    }

    /**
     * A native method that is implemented by the 'socket' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'socket' library on application startup.
        init {
            System.loadLibrary("socket")
        }
    }
}