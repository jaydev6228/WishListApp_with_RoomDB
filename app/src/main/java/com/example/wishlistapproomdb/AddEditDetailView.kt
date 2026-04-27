package com.example.wishlistapproomdb

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wishlistapproomdb.data.Wish
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishListViewModel,
    navController: NavController
) {

    val snackMessage = remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    if (id != 0L) {
        viewModel.getWishById(id).collectAsState(initial = Wish(title = "", description = "")).value.let {
            viewModel.wishTitleState = it.title
            viewModel.wishDescriptionState = it.description
        }
    } else {
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AppBarView(
                title =
                    if (id == 0L) stringResource(id = R.string.add_wish)
                    else stringResource(id = R.string.update_wish),
                onBackNavigation = {
                    navController.navigateUp()
                })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(label = "Title",
                value = viewModel.wishTitleState,
                onValueChange = {
                    viewModel.onWishTitleChanged(it)
                } )

            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(label = "Description",
                value = viewModel.wishDescriptionState,
                onValueChange = {
                    viewModel.onWishDescriptionChanged(it)
                } )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    if (viewModel.wishTitleState.isNotEmpty() &&
                        viewModel.wishDescriptionState.isNotEmpty()
                    ) {
                        if (id != 0L) {
                            viewModel.updateWish(
                                Wish(
                                    id = id,
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )
                            snackMessage.value = "Wish Updated"
                        } else {
                            viewModel.addWish(
                                Wish(
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )
                            snackMessage.value = "Wish Added"
                        }
                        
                        scope.launch {
                            snackbarHostState.showSnackbar(snackMessage.value)
                            navController.navigateUp()
                        }
                    } else {
                        snackMessage.value = "Enter fields to create a wish"
                        scope.launch {
                            snackbarHostState.showSnackbar(snackMessage.value)
                        }
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.teal_700)
                )
            ) {
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update_wish)
                    else stringResource(id = R.string.add_wish),
                    style = TextStyle(
                        fontSize = 18.sp
                    ),
                    color = colorResource(id = R.color.white),
                )
            }
        }
    }
}


@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.teal_700),
            unfocusedBorderColor = colorResource(id = R.color.teal_700),
            focusedLabelColor = colorResource(id = R.color.teal_700),
            unfocusedLabelColor = colorResource(id = R.color.teal_700)
        )
    )
}