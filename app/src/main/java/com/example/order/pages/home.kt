package com.example.order.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.order.R
import com.example.order.components.CategoryChip
import com.example.order.components.FoodBannerItem
import com.example.order.components.ReviewsShowPage
import androidx.navigation.NavHostController
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.TopAppBarDefaults


@Composable
fun HomePage(navController: NavHostController) {

    val categories = listOf("All", "Category", "Popular", "Restaurant Review")
    var selectedCategory by remember { mutableStateOf("All") }



    Scaffold(
        modifier = Modifier
            //Safe Area Color
            .background(Color(0xFFFF6F00))
            .windowInsetsPadding(WindowInsets.statusBars.union(WindowInsets.navigationBars)),

                bottomBar = {
            NavigationBar(modifier = Modifier.
            height(60.dp)
                .windowInsetsPadding(WindowInsets.navigationBars),
                containerColor = Color.White
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { navController.navigate("profile") },
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
                    label = { Text("Profile",color=Color.Black) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { navController.navigate("cart") },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "MY Cart") },
                    label = { Text("MY Cart",color=Color.Black) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { navController.navigate("orders") },
                    icon = { Icon(Icons.Default.List, contentDescription = "Orders") },
                    label = { Text("Orders",color=Color.Black) }
                )
            }
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
            // Banner
            item {
                Image(
                    painter = painterResource(id = R.drawable.banner),
                    contentDescription = "Restaurant Banner",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(150.dp, 190.dp)
                        .clip(RoundedCornerShape(18.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // Categories
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(categories) { category ->
                        CategoryChip(
                            text = category,
                            isSelected = selectedCategory == category
                        ) {
                            selectedCategory = category
                        }
                    }
                }
            }

            // Dynamic Content
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    when (selectedCategory) {
                        "All" -> repeat(10) { FoodItemCard() }
                        "Category" -> repeat(10) { index -> FoodBannerItem(title = "Category Banner $index") }
                        "Popular" -> repeat(10) { FoodItemCard() }
                        "Restaurant Review" -> ReviewsShowPage()
                    }
                }
            }
        }

    }
}

