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
        socketIOServer.addEventListener(SocketEvents.MessageEvent.eventName, Message::class.java, onMessageReceived())
        socketIOServer.addEventListener(
            SocketEvents.AllConnectedUsersEvent.eventName,
            String::class.java,
            onAllConnectedUsersRequest()
        )
    }

    private fun onAllConnectedUsersRequest(): DataListener<String> {
        return DataListener { socketIOClient, t, ackRequest ->
            socketIOClient.sendEvent(SocketEvents.AllConnectedUsersEvent.eventName, connectedClients.keys)
        }
    }

    val connectedClients = hashMapOf<String, String>()

    final override fun onConnected(): ConnectListener {
        return ConnectListener { client ->
            val email = client.handshakeData.httpHeaders["email"]
            val id = client.sessionId


            if (email == null) {
                println("No Email Found on Headers")
                client.disconnect()
                return@ConnectListener
            }
            println("Client Connected : $email")
            connectedClients[email] = id.toString()

            //existing users to new client
            client.sendEvent(SocketEvents.AllConnectedUsersEvent.eventName, "from server")

//            new client to existing users
//            socketIOServer.broadcastOperations.sendEvent(SocketEvents.NewUserConnectedEvent.eventName, email)
        }
    }

    final override fun onMessageReceived(): DataListener<Message> {
        return DataListener { socketIOClient, t, ackRequest ->
            println(t.toString())
//            socketIOClient.sendEvent(SocketEvents.MessageEvent.eventName,"Return Message")
//            val receiverSocketId =
//            ackRequest.sendAckData()

        }
    }

//    override fun getConnectedClients(): List<String> {
//        return connectedClients.values.toList()
//    }

    final override fun onDisconnected(): DisconnectListener {
        return DisconnectListener { client ->
            val email = client.handshakeData.httpHeaders["email"]
            connectedClients.remove(email)
            socketIOServer.broadcastOperations.sendEvent(SocketEvents.CurrUserDisconnectedEvent.eventName, email)
            println("Client Disconnected : $email")
        }
    }
}