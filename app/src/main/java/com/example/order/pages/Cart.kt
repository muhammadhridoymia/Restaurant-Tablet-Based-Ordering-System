package com.example.order.pages

import CartViewModel
import Food
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cart(navController: NavController,cartViewModel: CartViewModel) {

    val cartItems = cartViewModel.cartItems

    val itemCount = cartItems.size
    val totalPrice = cartItems.sumOf { it.food.price * it.quantity }

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
                onOrderClick = { println(cartItems) }
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
                Items( food = cartItems[it].food, quantity = cartItems[it].quantity)
            }
        }
    }
}



@Composable
fun Items( food: Food, quantity: Int) {
    val quantity = remember { mutableStateOf(quantity) }
    val isAvailable = food.display

    fun increaseQuantity() {
        quantity.value++
    }

    fun decreaseQuantity() {
        if (quantity.value > 1) {
            quantity.value--
        }
    }



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
            AsyncImage(
                model = food.img,
                contentDescription = food.name,
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = food.name,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "৳${food.price}",
                    fontSize = 16.sp,
                    color = Color(0xFFF5F5F5),
                    fontWeight = FontWeight.SemiBold
                )
                // 🔹 Right: Quantity Buttons
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Button(
                        onClick = { decreaseQuantity()},
                        enabled = isAvailable,
                        modifier = Modifier.size(20.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("-", fontSize = 14.sp)
                    }

                    Text(
                        text = quantity.value.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = { increaseQuantity()},
                        enabled = isAvailable,
                        modifier = Modifier.size(20.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text("+", fontSize = 14.sp, color = Color.Black)
                    }
                }

                Button(
                    onClick = { /* remove from cart  */ },
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