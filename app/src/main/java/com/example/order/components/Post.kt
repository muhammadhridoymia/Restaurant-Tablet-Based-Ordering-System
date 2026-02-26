package com.example.order.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage



// Data Model
data class Post(
    val id: Int,
    val imageUrl: String,
    val message: String,
    val likes: Int,
    val comments: Int
)

@Composable
fun PostScreen() {

    val demoPosts = listOf(
        Post(
            id = 1,
            imageUrl = "https://images.unsplash.com/photo-1504674900247-0877df9cc836",
            message = "Enjoying delicious food 🍕",
            likes = 12,
            comments = 4
        ),
        Post(
            id = 2,
            imageUrl = "https://images.unsplash.com/photo-1492724441997-5dc865305da7",
            message = "Coffee time ☕",
            likes = 8,
            comments = 2
        )
    )

    Column {
        demoPosts.forEach { post ->
            PostCard(post)
        }
    }
}

@Composable
fun PostCard(post: Post) {

    var likeCount by remember { mutableStateOf(post.likes) }

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {

        Column {

            AsyncImage(
                model = post.imageUrl,
                contentDescription = "Post Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            )

            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = post.message,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { likeCount++ }) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Like",
                                tint = Color.Red
                            )
                        }
                        Text(text = likeCount.toString())
                    }

                    IconButton(onClick = { /* Share */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
                        )
                    }
                }
            }
        }
    }
}