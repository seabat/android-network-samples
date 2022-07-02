package dev.seabat.socket

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.RadioButton
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
        binding.serverText.text = stringFromJNI()
        binding.sendButton.setOnClickListener {
            val text: String = binding.editText.text.toString()
            client.sendAsync(text)
        }


        server = SocketServer(object : SocketServerListener {
            override fun onReceive(msg: String) {
                if (Thread.currentThread() != this@MainActivity.application.mainLooper.thread) {
                    val mainThreadHandler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())
                    mainThreadHandler.post(Runnable {
                        binding.serverText.text = "Server: $msg"
                    })
                } else {
                    binding.serverText.text = "Server: $msg"
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
                        binding.clientText.text = "Client: $msg"
                    })
                } else {
                    binding.clientText.text = "Client: $msg"
                }
                Log.v("SocketClient", "receive:$msg")
            }
        })

        // ラジオボタングループのデフォルトを TCP にする
        val radioGroup = binding.transportGrp
        radioGroup.check(R.id.radio_tcp)
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_tcp ->
                    if (checked) {
                        this.server.changeTransportType("TCP")
                        this.client.setTransportType("TCP")
                    }
                R.id.radio_udp ->
                    if (checked) {
                        this.server.changeTransportType("UDP")
                        this.client.setTransportType("UDP")
                    }
            }
        }
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