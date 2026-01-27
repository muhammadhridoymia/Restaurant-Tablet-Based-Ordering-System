package com.example.order.pages

import GetOrdersViewModel
import Order
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.chatapp.LoginDataStore

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import com.example.order.SocketManager


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersPage(navController: NavHostController) {

    val context = LocalContext.current
    val userId = LoginDataStore.getId(context).collectAsState(initial = "").value

    val viewModel: GetOrdersViewModel = viewModel()
    val order by viewModel.order
    val loading by viewModel.loading

    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            viewModel.fetchOrder(userId)
        }
        SocketManager.listenOrderUpdates {
            viewModel.fetchOrder(userId)
        }
    }

    // 🔒 Navigation lock (THE REAL FIX)
    val isNavigating = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Orders",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(
                        enabled = !isNavigating.value,
                        onClick = {
                            isNavigating.value = true
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White.copy(if (isNavigating.value) 0.4f else 1f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFF6F00)
                )
            )
        }
    ) { padding ->

        Column (
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                //notice color
                .background(Color(0xFFFF6F00))
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                when {
                    loading -> {
                        CircularProgressIndicator()
                    }

                    order == null -> {
                        Text(
                            text = "No active order",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }

                    else -> {
                        OrderCard(order!!)
                    }
                }
            }
        }
    }
}


@Composable
fun OrderCard(order: Order) {
    val statusColor = when (order.status) {
        "ACCEPTED", "COMPLETED" -> Color(0xFF4CAF50)
        "CANCELLED" -> Color.Red
        else -> Color(0xFFFF9800)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp))
        {
            Text(
                text = "Keep this page open to track your order",
                fontSize = 12.sp,
                color = Color.Gray
            )

            // Order Header: Status + Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Order Status",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Box(
                    modifier = Modifier
                        .background(statusColor, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = order.status,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Items List
            Text(
                text = "Order Foods:",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                order.items.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${index + 1}. ${item.name} × ${item.quantity}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (!item.received) Color(0xFFFF5722) else Color.DarkGray
                        )

                        if (!item.received) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "(new)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF5722)
                            )
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(12.dp))

            // Message Section
            if (order.message.isNotEmpty()) {

                if (order.status=="ACCEPTED") {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFFF1F8E9),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Restaurant Message:",
                            fontSize = 13.sp,
                            color = Color(0xFF33691E),
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "Your order is being prepared. Please wait.",
                            fontSize = 12.sp,
                            color = Color(0xFF33691E)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            // Ordered Time
            Text(
                text = "Ordered at: ${order.orderedAt}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
