package com.stu74523.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stu74523.shoppingapp.navigation.AppNavigation
import com.stu74523.shoppingapp.routes.CART
import com.stu74523.shoppingapp.routes.PRODUCT_LIST
import com.stu74523.shoppingapp.routes.USER_DETAILS
import com.stu74523.shoppingapp.ui.theme.ShoppingAppTheme


class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingAppTheme {
                val navController = rememberNavController()
                val db = Firebase.firestore
                val currentRoute = remember{ mutableStateOf(PRODUCT_LIST)}


                //SeedData(db)
                //Firebase.auth.signOut()
                val isLoggedIn = remember { mutableStateOf(true) }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomAppBar(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                actions = {
                                    if(isLoggedIn.value) {

                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth(0.33f),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                        )
                                        {
                                            IconButton(
                                                onClick = { navController.navigate(PRODUCT_LIST) },
                                                colors = IconButtonDefaults.iconButtonColors(
                                                    containerColor = Color.Transparent,
                                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                                    disabledContentColor = MaterialTheme.colorScheme.tertiaryContainer,
                                                    disabledContainerColor = Color.Transparent
                                                ),
                                                enabled = currentRoute.value != PRODUCT_LIST
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Rounded.Home,
                                                    contentDescription = "Home"
                                                )
                                            }
                                            Text(
                                                text = "Home",
                                                style = MaterialTheme.typography.labelLarge,
                                                color = if (currentRoute.value == PRODUCT_LIST) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                        }

                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth(0.5f),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                        )
                                        {
                                            IconButton(
                                                onClick = { navController.navigate(CART) },
                                                colors = IconButtonDefaults.iconButtonColors(
                                                    containerColor = Color.Transparent,
                                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                                    disabledContentColor = MaterialTheme.colorScheme.tertiaryContainer,
                                                    disabledContainerColor = Color.Transparent
                                                ),
                                                enabled = currentRoute.value != CART
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Rounded.ShoppingCart,
                                                    contentDescription = "My Cart"

                                                )
                                            }
                                            Text(
                                                text = "My Cart",
                                                style = MaterialTheme.typography.labelLarge,
                                                color = if (currentRoute.value == CART) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.onPrimaryContainer

                                            )
                                        }

                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                        )
                                        {
                                            IconButton(
                                                onClick = { navController.navigate(USER_DETAILS) },
                                                colors = IconButtonDefaults.iconButtonColors(
                                                    containerColor = Color.Transparent,
                                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                                    disabledContentColor = MaterialTheme.colorScheme.tertiaryContainer,
                                                    disabledContainerColor = Color.Transparent

                                                ),
                                                enabled = currentRoute.value != USER_DETAILS
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Rounded.Person,
                                                    contentDescription = "Profile Page"

                                                )
                                            }
                                            Text(
                                                text = "Profile Page",
                                                style = MaterialTheme.typography.labelLarge,
                                                color = if (currentRoute.value == USER_DETAILS) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                        }
                                    }
                                }
                            )
                        },
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        containerColor = MaterialTheme.colorScheme.background,
                    ) {
                        AppNavigation(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize(),
                            navController = navController,
                            dataBase = db,
                            isLoggedin = isLoggedIn,
                            currentRoute = currentRoute,
                        )

                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingAppTheme {
        Greeting("Android")
    }
}