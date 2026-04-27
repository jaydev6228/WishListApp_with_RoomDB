package com.example.wishlistapproomdb

import android.service.autofill.OnClickAction
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wishlistapproomdb.data.DummyWish
import com.example.wishlistapproomdb.data.Wish

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavHostController = rememberNavController(),
             viewModel: WishListViewModel = viewModel()) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBarView(title = "Wish List", onBackNavigation = {
                Toast.makeText(context,
                    "Back Navigation Clicked",
                    Toast.LENGTH_SHORT).show()
            })
        },

        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                containerColor = colorResource(id = R.color.teal_700),
                onClick = {
                    navController.navigate(Screen.AddScreen.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = colorResource(id = R.color.white)
                )
            }
        }

    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .padding(it)) {
            items(DummyWish.wishList) { wish ->
                WishItem(wish = wish) {
                    Toast.makeText(context,
                        "Item Clicked: ${wish.title}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun WishItem(wish: Wish, onClickAction: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable {
                onClickAction()
            }, 
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = wish.title, style = MaterialTheme.typography.titleLarge)
            Text(text = wish.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}