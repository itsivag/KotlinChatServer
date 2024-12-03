package com.itsivag.kotlin_chat_server.socket

import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.ConnectListener
import org.springframework.stereotype.Component

@Component
class SocketModule(private val socketIOServer: SocketIOServer) {
    init {
        socketIOServer.addConnectListener(onConnected())
    }

    private fun onConnected(): ConnectListener {
        return ConnectListener { client ->
            println("Client Connected : $client")
        }
    }
}