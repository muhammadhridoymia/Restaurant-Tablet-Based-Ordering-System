package com.example.order.components

import FoodCategory
import FoodCategoryViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.order.R
import coil.compose.AsyncImage



@Composable
fun CategoryPage(navController: NavHostController) {
    val viewModel: FoodCategoryViewModel = viewModel()
    val categories = viewModel.categories
    val loading = viewModel.loading
    LaunchedEffect(Unit) {
        viewModel.fetchfoodcategory()
    }
    if (loading) {
        CircularProgressIndicator()
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            categories.forEach { category ->
                FoodCategoryCard(category,navController)
            }
        }
    }
}
@Composable
fun FoodCategoryCard(category: FoodCategory,navController: NavHostController) {
        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            onClick = {
                navController.navigate("categorylist")
            },
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Box {
                AsyncImage(
                    model = category.img,
                    contentDescription = category.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = category.name,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                )
            }
        }
    }

