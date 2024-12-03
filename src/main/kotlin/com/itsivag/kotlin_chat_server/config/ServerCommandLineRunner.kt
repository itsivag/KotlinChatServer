package com.itsivag.kotlin_chat_server.config

import com.corundumstudio.socketio.SocketIOServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class ServerCommandLineRunner(@Autowired private val socketIoServer: SocketIOServer) : CommandLineRunner {
    //    val socketIoServer = socketIoConfig.getSocketIoServer()
    override fun run(vararg args: String?) {
        socketIoServer.start()
    }
}