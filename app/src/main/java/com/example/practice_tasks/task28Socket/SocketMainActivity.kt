package com.example.practice_tasks.task28Socket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practice_tasks.task28Socket.ui.theme.Practice_tasksTheme
import io.socket.client.Socket

class SocketMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practice_tasksTheme {
                SocketDemoScreen()
            }
        }
    }
}

@Composable
fun SocketDemoScreen() {

    var connectionStatus by remember { mutableStateOf("Connecting...") }
    var receivedMessage by remember { mutableStateOf("No message") }

    LaunchedEffect(Unit) {
        SocketManager.connect()

        SocketManager.socket.on(Socket.EVENT_CONNECT) {
            android.os.Handler(android.os.Looper.getMainLooper()).post {
                connectionStatus = "Connected"

                // ✅ Emit ONLY after connected
                SocketManager.socket.emit(
                    "send_message",
                    "Hello from compose!"
                )
            }
        }

        SocketManager.socket.on("receive_message") { args ->
            android.os.Handler(android.os.Looper.getMainLooper()).post {
                receivedMessage = args[0].toString()
            }
        }

        SocketManager.socket.on(Socket.EVENT_CONNECT_ERROR) { args ->
            val error = if (args.isNotEmpty()) args[0].toString() else "Unknown Error"
            android.util.Log.e("SOCKET", "ERROR: $error")
            android.os.Handler(android.os.Looper.getMainLooper()).post {
                connectionStatus = "Error: $error"
            }
        }

        SocketManager.socket.on(Socket.EVENT_DISCONNECT) {
            android.os.Handler(android.os.Looper.getMainLooper()).post {
                connectionStatus = "Disconnected"
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            SocketManager.disconnect()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Status: $connectionStatus")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Received Message: $receivedMessage")
    }
}
