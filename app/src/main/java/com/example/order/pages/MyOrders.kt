package com.example.order.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.order.R

// Demo order data
data class Order(
    val orderNumber: Int,
    val items: List<OrderItem>,
    val totalPrice: Int,
    val status: String,
    val time: String
)

data class OrderItem(
    val name: String,
    val image: Int
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersPage(navController: NavHostController) {

    // 🔹 Demo orders
    val orders = listOf(
        Order(
            orderNumber = 101,
            items = listOf(
                OrderItem("Chicken", R.drawable.banner),
                OrderItem("Fish", R.drawable.banner),
                OrderItem("Burger", R.drawable.banner)
            ),
            totalPrice = 850,
            status = "Cooking",
            time = "15 minutes"
        ),
        Order(
            orderNumber = 102,
            items = listOf(
                OrderItem("Pizza", R.drawable.banner),
                OrderItem("Fries", R.drawable.banner)
            ),
            totalPrice = 550,
            status = "Cooking",
            time = "10 minutes"
        )
    )

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
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color(0xFFFFF8E1)),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(orders) { order ->
                    OrderCard(order)
                }
            }
        }
    }

@Composable
fun OrderCard(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Order number and total items
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Order #${order.orderNumber}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "${order.items.size} items | ৳${order.totalPrice}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Status and time
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Status: ${order.status}",
                    fontSize = 14.sp,
                    color = Color(0xFFFF6F00),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "ETA: ${order.time}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Items list (horizontal scroll)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(order.items) { item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(80.dp)
                    ) {
                        Image(
                            painter = painterResource(id = item.image),
                            contentDescription = item.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.name,
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // View Details button
            Button(
                onClick = { /* TODO: navigate to order details */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text(text = "View Details", color = Color.White, fontSize = 14.sp)
            }
        }
    }
}
