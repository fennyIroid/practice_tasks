package com.example.practice_tasks.task28Socket

import io.socket.client.IO
import io.socket.client.Socket

object SocketManager {
    private const val SOCKET_URL = "http://10.0.2.2:3001"
    lateinit var socket: Socket

    fun connect() {
        try {
            val options = IO.Options()
            options.forceNew = true
            socket = IO.socket(SOCKET_URL, options)
            socket.connect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        socket.disconnect()
    }
}