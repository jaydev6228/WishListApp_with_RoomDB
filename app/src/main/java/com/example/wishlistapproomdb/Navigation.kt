package com.example.wishlistapproomdb

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(viewModel: WishListViewModel = viewModel(),
               navigationController: NavHostController = rememberNavController()) {

    NavHost(navController = navigationController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeView(navController = navigationController, viewModel = viewModel)
        }

        composable(
            route = Screen.AddScreen.route + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
                defaultValue = 0L
                nullable = false
            })
        ) { entry ->
            val id = if (entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            AddEditDetailView(id = id, viewModel = viewModel, navController = navigationController)
        }
    }
}