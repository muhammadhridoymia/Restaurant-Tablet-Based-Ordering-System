package com.example.order.pages

import Food
import FoodViewModel
import OrderViewModel
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
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.chatapp.LoginDataStore
import com.example.order.components.Popup




@Composable
fun FoodItemPage() {

    val viewModel: FoodViewModel = viewModel()
    val foods = viewModel.foods
    val loading = viewModel.loading

    // Fetch data ONCE
    LaunchedEffect(Unit) {
        viewModel.fetchFoods()
    }

    if (loading) {
        CircularProgressIndicator()
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            foods.forEach { food ->
                FoodItemCard(food)
            }
        }
    }
}


@Composable
fun FoodItemCard(food: Food) {


    val showpopup= remember { mutableStateOf(false) }
    var quantity by remember { mutableStateOf(1) }
    val isAvailable = food.display

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp),
        onClick = {  },
        colors = if (isAvailable) CardDefaults.cardColors(containerColor = Color.DarkGray) else CardDefaults.cardColors(containerColor = Color.LightGray),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        if (showpopup.value){
            Popup(showpopup,food.id,quantity)
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = food.img,
                contentDescription = food.name,
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 🔹 Right: Details + Buttons
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = food.name,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )

                Row (
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(
                        text = "৳${food.price}",
                        fontSize = 16.sp,
                        color = Color(0xFFF5F5F5),
                        fontWeight = FontWeight.SemiBold
                    )
                    Button(
                        onClick = { /* Add to Cart */ },
                        enabled = isAvailable,
                        modifier = Modifier
                            .width(60.dp)
                            .height(20.dp),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (isAvailable) Color(0xFFFF8F00) else Color.LightGray, disabledContainerColor = Color.LightGray)
                    ) {
                        Text(
                            text = "Add To Cart",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Button(
                        onClick = { if (quantity > 1) quantity-- },
                        enabled = isAvailable,
                        modifier = Modifier.size(20.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("-", fontSize = 14.sp)
                    }

                    Text(
                        text = quantity.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = { quantity++ },
                        enabled = isAvailable,
                        modifier = Modifier.size(20.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text("+", fontSize = 14.sp, color = Color.Black)
                    }
                }

                // Order Now button (primary)
                Button(
                    onClick = { showpopup.value=true },
                    enabled = isAvailable,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8F00))
                ) {
                    Text(if (isAvailable) "Order Now" else "Out of Stock", color = Color.White)
                }
            }
        }
    }
}