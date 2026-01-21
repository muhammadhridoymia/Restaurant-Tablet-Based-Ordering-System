package com.example.order.Restration

import LoginRequest
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatapp.LoginDataStore
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavController, onLoginSuccess: () -> Unit) {
    // State variables to store input
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }


    var context = LocalContext.current
    var scope = rememberCoroutineScope()



    // Screen layout
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFFFFA726)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "Login",
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Name Input
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Your Phone Number",color=Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Password Input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password",color=Color.Black) },
//                visualTransformation = PasswordVisualTransformation(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )

            // Login Button
            Button(
                onClick = {
                    // Handle login click here
                    if (phone.isEmpty() && password.isEmpty()) {
                        Toast.makeText(context, "Enter name and password", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        scope.launch {
                            isLoading = true
                            try {
                                val res = RetrofitInstance.api.login(LoginRequest(phone, password))
                                if (res.success) {
                                    LoginDataStore.userSave(context, res.user.name, res.user.img, res.user.id)
                                    navController.navigate("home")
                                    onLoginSuccess() // <-- fix: add parentheses
                                    isLoading = false
                                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                                    println("Login response: $res")
                                } else {
                                    isLoading = false
                                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                                }

                            } catch (e: Exception) {
                                isLoading = false
                                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                                e.printStackTrace()
                            }

                        }
                    }

                        // Save name and password to DataStore)
                        println("Phone: $phone, Password: $password")
                    },
                    modifier = Modifier.fillMaxWidth()
                    ) {
                    if (isLoading){
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(22.dp)
                        )
                    }else{
                        Text("Login")
                }
                }

        }
}
}
