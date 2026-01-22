package com.example.order

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.LoginDataStore

//Pages
import com.example.order.pages.HomePage
import com.example.order.pages.Cart
import com.example.order.pages.Profile
import com.example.order.pages.OrdersPage
import com.example.order.pages.LandingPage
import com.example.order.Restration.LoginScreen
import com.example.order.pages.LoadingPage
import com.example.order.pages.FoodCategoryListPage





@Composable
fun Navigation() {

    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "loading"
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
        composable("landing") {
            LandingPage( navController=navController)
        }
        composable("loading") {
            LoadingPage(navController=navController)
        }
        composable("categorylist") {
            FoodCategoryListPage(navController=navController)
        }
        composable("login") {
            LoginScreen(navController=navController,
                onLoginSuccess = {
                    navController.navigate("home"){
                        popUpTo("login") { inclusive = true }
                    }
                }
                )
        }
    }
}
