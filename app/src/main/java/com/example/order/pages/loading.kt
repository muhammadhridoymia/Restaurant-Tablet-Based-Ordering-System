package com.example.order.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatapp.LoginDataStore

@Composable
fun LoadingPage(navController: NavHostController) {

    val context = LocalContext.current
    val name by LoginDataStore.getName(context).collectAsState(initial = null)

    LaunchedEffect(name) {
        if (name == null) return@LaunchedEffect  // still loading

        if (name!!.isNotEmpty()) {
            navController.navigate("home") {
                popUpTo("loading") { inclusive = true }
            }
        } else {
            navController.navigate("landing") {
                popUpTo("loading") { inclusive = true }
            }
        }
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(color = 0xFFFF6F00))
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Loading...")
        }
    }
}
