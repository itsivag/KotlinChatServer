package com.itsivag.kotlin_chat_server.socket

import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.listener.DataListener
import com.corundumstudio.socketio.listener.DisconnectListener
import com.itsivag.kotlin_chat_server.model.Message
import org.springframework.stereotype.Component

@Component
class SocketModuleImpl(private val socketIOServer: SocketIOServer) : SocketModule {
    init {
        socketIOServer.addConnectListener(onConnected())
        socketIOServer.addDisconnectListener(onDisconnected())
        socketIOServer.addEventListener("message", Message::class.java, onMessageReceived())
    }

    val connectedClients = mutableListOf<String>()

    final override fun onConnected(): ConnectListener {
        return ConnectListener { client ->
            val email = client.handshakeData.httpHeaders["email"]
            val id = client.sessionId
            println("Client Connected : $email")
            connectedClients.add(email)
        }
    }

    final override fun onMessageReceived(): DataListener<Message> {
        return DataListener { socketIOClient, t, ackRequest ->
            println(t.toString())
            ackRequest.sendAckData()

        }
    }

    override fun getConnectedClients(): List<String> {
        return connectedClients
    }

    final override fun onDisconnected(): DisconnectListener {
        return DisconnectListener { client ->
            println("Client Disconnected : $client")
            connectedClients.remove(client.handshakeData.httpHeaders["email"])
        }
    }
}