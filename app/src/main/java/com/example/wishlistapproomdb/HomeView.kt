package com.example.wishlistapproomdb

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wishlistapproomdb.data.Wish

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavHostController = rememberNavController(),
             viewModel: WishListViewModel = viewModel()) {
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }
    val wishToDelete = remember { mutableStateOf<Wish?>(null) }

    if (openDialog.value && wishToDelete.value != null) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Delete Wish") },
            text = { Text(text = "Are you sure you want to delete this wish?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        wishToDelete.value?.let {
                            viewModel.deleteWish(it)
                        }
                        openDialog.value = false
                        wishToDelete.value = null
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        wishToDelete.value = null
                    }
                ) {
                    Text("No")
                }
            }
        )
    }

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
                    navController.navigate(Screen.AddScreen.route + "/${0L}")
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
        val wishList = viewModel.getAllWishes.collectAsState(initial = listOf())

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .padding(it)) {
            items(wishList.value, key = {wish -> wish.id}) { wish ->

                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { value ->
                        if (value == SwipeToDismissBoxValue.EndToStart) {
                            wishToDelete.value = wish
                            openDialog.value = true
                            false
                        } else if (value == SwipeToDismissBoxValue.StartToEnd) {
                            navController.navigate(Screen.AddScreen.route + "/${wish.id}")
                            false
                        } else {
                            false
                        }
                    }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {
                        val color = when (dismissState.dismissDirection) {
                            SwipeToDismissBoxValue.EndToStart -> Color.White.copy(alpha = 0.5f)
                            SwipeToDismissBoxValue.StartToEnd -> Color.White.copy(alpha = 0.5f)
                            else -> Color.Transparent
                        }
                        val alignment = if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd)
                            Alignment.CenterStart else Alignment.CenterEnd
                        val icon = if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd)
                            Icons.Default.Edit else Icons.Default.Delete
                        val iconColor = if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd)
                            Color.Green else Color.Red

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(all = 8.dp)
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ) {
                           Icon(icon, contentDescription = null, tint = iconColor)
                        }
                    }
                ) {
                    WishItem(wish = wish) {
                        navController.navigate(Screen.AddScreen.route + "/${wish.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun WishItem(wish: Wish, onClickAction: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
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