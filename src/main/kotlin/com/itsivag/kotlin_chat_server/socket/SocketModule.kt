package com.itsivag.kotlin_chat_server.socket

import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.listener.DataListener
import com.corundumstudio.socketio.listener.DisconnectListener
import com.itsivag.kotlin_chat_server.model.Message
import org.springframework.stereotype.Component

@Component
class SocketModule(private val socketIOServer: SocketIOServer) {
    init {
        socketIOServer.addConnectListener(onConnected())
        socketIOServer.addDisconnectListener(onDisconnected())
        socketIOServer.addEventListener("message", Message::class.java, onMessageReceived())
    }


    private fun onConnected(): ConnectListener {
        return ConnectListener { client ->
            println("Client Connected : $client")
        }
    }

    private fun onMessageReceived(): DataListener<Message> {
        return DataListener { socketIOClient, t, ackRequest ->
            println(t.toString())
        }
    }

    private fun onDisconnected(): DisconnectListener {
        return DisconnectListener { client ->
            println("Client Disconnected : $client")
        }
    }
}