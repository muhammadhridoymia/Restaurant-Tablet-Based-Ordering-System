package com.example.order

import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter

object SocketManager {

    private const val SOCKET_URL = "http://172.172.10.240:5000/"

    private lateinit var socket: Socket

    fun initSocket() {
        try {
            socket = IO.socket(SOCKET_URL)
            socket.connect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        if (::socket.isInitialized && socket.connected()) {
            socket.disconnect()
        }
    }

    fun joinRoom(userId: String) {
        if (::socket.isInitialized) {
            socket.emit("joinRoom", userId)
        }
    }

    fun OrderSupmit() {
        if (::socket.isInitialized) {
            socket.emit("orderSubmit")
        }
    }

    // Emit an update without data
    fun listenOrderUpdates(onUpdate: () -> Unit) {
        if (::socket.isInitialized) {
            socket.on("orderUpdate", Emitter.Listener {
                onUpdate()
            })
        }
    }



}
