package com.example.order

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.chatapp.LoginDataStore
import com.example.order.ui.theme.OrderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OrderTheme {
                val coroutineScope = rememberCoroutineScope()
                val context = LocalContext.current
                val id = LoginDataStore.getId(context).collectAsState(initial = "").value

                SocketManager.initSocket()
                SocketManager.joinRoom(id)
                Navigation()
                }
            }
        }
    }

