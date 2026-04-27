package com.example.wishlistapproomdb

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(viewModel: WishListViewModel = viewModel(),
               navigationController: NavHostController = rememberNavController()) {

    NavHost(navController = navigationController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeView(navController = navigationController, viewModel = viewModel)
        }

        composable(route = Screen.AddScreen.route) {
            AddEditDetailView(id = 0L, viewModel = viewModel, navController = navigationController)
        }
    }
}