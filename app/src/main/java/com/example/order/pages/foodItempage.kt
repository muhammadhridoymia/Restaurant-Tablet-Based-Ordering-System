package com.example.order.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.order.R

@Composable
fun FoodItemPage() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8E1)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(10) {
            FoodItemCard()
        }
    }
}

@Composable
fun FoodItemCard() {

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
                    text = "Chicken Items",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Row (
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(
                        text = "৳ 8,350",
                        fontSize = 16.sp,
                        color = Color(0xFFF5F5F5),
                        fontWeight = FontWeight.SemiBold
                    )
                    Button(
                        onClick = { /* Add to Cart */ },
                        modifier = Modifier
                            .width(60.dp)
                            .height(20.dp),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp) // important
                    ) {
                        Text(
                            text = "Add To Cart",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    // Order Now button (primary)
                    Button(
                        onClick = { /* Order */ },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF8F00)
                        )
                    ) {
                        Text("Order Now")
                    }
                }
            }
        }
    }
}
