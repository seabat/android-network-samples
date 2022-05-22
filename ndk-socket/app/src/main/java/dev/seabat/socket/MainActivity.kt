package dev.seabat.socket

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat
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
                if (Thread.currentThread() != this@MainActivity.application.mainLooper.thread) {
                    val mainThreadHandler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())
                    mainThreadHandler.post(Runnable {
                        binding.sampleText.text = "From Server: $msg"
                    })
                } else {
                    binding.sampleText.text = "From Server: $msg"
                }
                Log.v("SocketServer", "receive:$msg")
            }
        })
        server.run()

        client = SocketClient(object : SocketClientListener {
            override fun onReceive(msg: String) {
                if (Thread.currentThread() != this@MainActivity.application.mainLooper.thread) {
                    val mainThreadHandler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())
                    mainThreadHandler.post(Runnable {
                        binding.sampleText.text = "From Client: $msg"
                    })
                } else {
                    binding.sampleText.text = "From Client: $msg"
                }
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