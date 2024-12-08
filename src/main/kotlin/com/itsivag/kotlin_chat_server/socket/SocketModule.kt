package com.itsivag.kotlin_chat_server.socket

import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.listener.DataListener
import com.corundumstudio.socketio.listener.DisconnectListener
import com.itsivag.kotlin_chat_server.model.Message

interface SocketModule {
    fun onConnected(): ConnectListener
    fun onDisconnected(): DisconnectListener
    fun onMessageReceived(): DataListener<Message>

//    fun getConnectedClients(): List<String>
}


