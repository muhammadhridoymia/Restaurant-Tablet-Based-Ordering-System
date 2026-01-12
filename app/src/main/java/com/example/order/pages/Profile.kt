package com.example.order.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.order.R

import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    navController: NavController
) {

    // Demo cart data
    val cartItems = listOf(
        Pair("Chicken", 299),
        Pair("Fish", 499),
        Pair("Burger", 252),
        Pair("Chicken", 299),
        Pair("Fish", 499),
        Pair("Burger", 252),
        Pair("Chicken", 299),
        Pair("Fish", 499),
        Pair("Burger", 252),
        Pair("Chicken", 299),
        Pair("Fish", 499),
        Pair("Burger", 252)
    )

    val itemCount = cartItems.size
    val totalPrice = cartItems.sumOf { it.second }

    Scaffold(
        // 🔹 TOP BAR
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Cart",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFF6F00))
            )
        },

    ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFFFF8E1)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Spacer(modifier = Modifier.height(16.dp)) // top spacing

                // 🔹 Profile Section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo), // demo profile photo
                        contentDescription = "Profile Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Hridoy Ahmed", // demo name
                        fontSize = 22.sp,
                        color = Color.Black
                    )
                }

                // 🔹 Logout Button at Bottom
                Button(
                    onClick = {
                        // TODO: handle logout
                        // navController.navigate("login")
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(70.dp)
                        .padding(bottom = 24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F00)),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Logout",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }
        }


