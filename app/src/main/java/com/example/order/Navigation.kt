package com.example.order

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

//Pages
import com.example.order.pages.HomePage
import com.example.order.pages.Cart
import com.example.order.pages.Profile
import com.example.order.pages.OrdersPage




@Composable
fun Navigation() {

    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomePage(navController=navController)
        }
        composable("cart") {
            Cart(navController=navController)
        }
        composable("profile") {
            Profile(navController=navController)
        }
        composable("orders") {
            OrdersPage(navController=navController)
        }
    }
}
