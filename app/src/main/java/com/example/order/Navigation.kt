package com.example.order

import CartViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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



import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {

    val cartViewModel: CartViewModel = viewModel()


    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "loading"
    ) {
        composable("home") {
            HomePage(navController=navController,cartViewModel)
        }
        composable("cart") {
            Cart(navController=navController,cartViewModel)
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
        composable(
            "categorylist/{name}/{id}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("id") { type = NavType.StringType }
            )
        ) {  backStackEntry ->
            val name =backStackEntry.arguments?.getString("name")
            val id =backStackEntry.arguments?.getString("id")

            FoodCategoryListPage(navController=navController,name!!,id!!)
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
