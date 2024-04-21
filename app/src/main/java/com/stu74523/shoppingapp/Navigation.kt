package com.stu74523.shoppingapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.firestore.FirebaseFirestore
import com.stu74523.shoppingapp.routes.*
import com.stu74523.shoppingapp.screens.*


@Composable
fun AppNavigation(
    modifier: Modifier,
    navController: NavHostController,
    dataBase: FirebaseFirestore,
    isLoggedin: MutableState<Boolean>,
    currentRoute: MutableState<String>
) {


    NavHost(navController = navController, startDestination = SPLASH)
    {
        composable(route = SIGN_UP)
        {
            currentRoute.value = SIGN_UP
            SignUpScreen(modifier = modifier, navController = navController, dataBase)
        }

        composable(route = LOGIN)
        {
            currentRoute.value = LOGIN
            LoginScreen(modifier = modifier, navController = navController)
        }

        composable(route = CREATE_USER)
        {
            currentRoute.value = CREATE_USER
            CreateUserScreen(
                modifier = modifier,
                navController = navController,
                dataBase = dataBase
            )
        }

        composable(route = PRODUCT_LIST)
        {
            currentRoute.value = PRODUCT_LIST
            ProductListScreen(
                modifier = modifier,
                navController = navController,
                dataBase = dataBase
            )
        }

        composable(route = "${PRODUCT_DETAILS}/{productId}")
        {
            currentRoute.value = PRODUCT_DETAILS

            val productId = it.arguments?.getString("productId")

            ProductDetails(
                modifier = modifier,
                navController = navController,
                dataBase = dataBase,
                productId = productId
            )

        }

        composable(route = CART)
        {
            currentRoute.value = CART
            CartScreen(
                modifier = modifier,
                navController = navController,
                dataBase = dataBase
            )
        }

        composable(route = ORDER_HISTORY)
        {
            currentRoute.value = ORDER_HISTORY
            OrderHistoryScreen(
                modifier = modifier,
                navController = navController,
                dataBase = dataBase,
            )
        }

        composable(route = SPLASH)
        {
            currentRoute.value = SPLASH
            SplashScreen(
                modifier = modifier,
                navController = navController,
                isLoggedin = isLoggedin
            )
        }

        composable(route = USER_DETAILS)
        {
            currentRoute.value = USER_DETAILS
            UserDetailsScreen(
                modifier = modifier,
                navController = navController,
                dataBase = dataBase
            )
        }

        composable(route = ABOUT_THIS_APP)
        {
            currentRoute.value = ABOUT_THIS_APP
            AboutThisAppScreen(modifier = modifier, navController = navController)
        }

        composable(route = WORK_IN_PROGRESS)
        {
            currentRoute.value = WORK_IN_PROGRESS
            WorkInProgressScreen(modifier = modifier, navController = navController)
        }
    }
}