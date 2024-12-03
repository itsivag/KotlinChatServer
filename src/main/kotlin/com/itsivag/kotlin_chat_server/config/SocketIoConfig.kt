package com.itsivag.kotlin_chat_server.config

import com.corundumstudio.socketio.SocketIOServer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SocketIoConfig {
    @Value("\${socket-server.host}")
    lateinit var host: String

    @Value("\${socket-server.port}")
    lateinit var port: String

    @Bean
    fun getSocketIoServer(): SocketIOServer {
        val config = com.corundumstudio.socketio.Configuration()
        config.hostname = host
        config.port = port.toInt()

        return SocketIOServer(config)
    }
}