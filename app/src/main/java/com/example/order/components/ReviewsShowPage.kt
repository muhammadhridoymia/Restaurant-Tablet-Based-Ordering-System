package com.example.order.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp



@Composable
fun ReviewsShowPage() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(10) {
            ReviewCard(
                userName = "User ${it + 1}",
                rating = (3..5).random(),
                review = "Very tasty food, fresh and well cooked. Recommended!"
            )
        }
    }
}

@Composable
fun ReviewCard(
    userName: String,
    rating: Int,
    review: String
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(
                text = userName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            RatingStars(rating)

            Text(
                text = review,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}



@Composable
fun RatingStars(rating: Int) {
    Row {
        repeat(5) { index ->
            Text(
                text = if (index < rating) "⭐" else "☆",
                fontSize = 16.sp
            )
        }
    }
}
