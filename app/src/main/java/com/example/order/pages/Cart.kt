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
fun Cart(
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

        // 🔹 SUMMARY BAR
        bottomBar = {
             CartBottomBar (
                itemCount = itemCount,
                totalPrice = totalPrice,
                onOrderClick = { /* Place order */ }
            )
        }

    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFFF6F00)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cartItems.size) {
                Items()
            }
        }
    }
}



@Composable
fun Items() {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // 🔹 Left: Food Image
                    Image(
                        painter = painterResource(id = R.drawable.banner),
                        contentDescription = "Food Item",
                        modifier = Modifier
                            .size(240.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Chicken Items",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                            Text(
                                text = "৳ 350",
                                fontSize = 16.sp,
                                color = Color(0xFFF5F5F5),
                                fontWeight = FontWeight.SemiBold
                            )
                        Button(
                            onClick = { /* Add to Cart */ },
                            modifier = Modifier
                                .width(60.dp)
                                .height(20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8F00)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(0.dp) // important
                        ) {
                            Text(
                                text = "Remove",
                                fontSize = 8.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }



@Composable
fun CartBottomBar(
    itemCount: Int,
    totalPrice: Int,
    onOrderClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // 🔹 Left: Price info
            Column {
                Text(
                    text = "$itemCount items",
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Text(
                    text = "৳ $totalPrice",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            // 🔹 Right: Order button
            Button(
                onClick = onOrderClick,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.height(45.dp)
            ) {
                Text(
                    text = "Order Now",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
