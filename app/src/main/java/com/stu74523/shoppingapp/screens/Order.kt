package com.stu74523.shoppingapp.screens

import android.text.format.DateFormat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.stu74523.shoppingapp.R
import com.stu74523.shoppingapp.models.Cart
import com.stu74523.shoppingapp.models.Order
import com.stu74523.shoppingapp.models.Product
import com.stu74523.shoppingapp.models.User
import com.stu74523.shoppingapp.routes.CART
import com.stu74523.shoppingapp.routes.PRODUCT_DETAILS
import com.stu74523.shoppingapp.routes.PRODUCT_LIST
import com.stu74523.shoppingapp.routes.SPLASH
import com.stu74523.shoppingapp.ui.theme.ShoppingAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavController = rememberNavController(),
    dataBase: FirebaseFirestore?,
    preview: Boolean = false
) {
    val user = remember { mutableStateOf(User()) }

    if (!preview && dataBase != null) {
        if (Firebase.auth.getCurrentUser() == null) {
            navController.navigate(SPLASH)
        }

        getUser(dataBase = dataBase, navController = navController, user = user)

    }
    else {
        user.value.cart.items.add(
            element = Order(
                Product(
                    title = "product n°1",
                    category = "a",
                    price = 100.78f,
                    image = "https://picsum.photos/200",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
                ), 5
            )
        )
        user.value.cart.items.add(
            Order(
                Product(
                    title = "product n°2",
                    category = "a",
                    price = 100.78f,
                    image = "https://picsum.photos/200",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
                ), 5
            )
        )
    }


    Column(
        modifier = modifier,
    ) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text(text = "Buy") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Shopping cart icon"
                        )
                    },
                    shape = FloatingActionButtonDefaults.largeShape,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    onClick = {
                        if (user.value.cart.items.isNotEmpty()) {
                            user.value.cart.date = Timestamp.now()
                            user.value.history.add(user.value.cart.copy())
                            user.value.cart.empty()
                            if (dataBase != null) {
                                updateUser(dataBase = dataBase, user = user)
                            }
                            navController.navigate(PRODUCT_LIST)
                        }
                    },
                )
            }
        ) {
            OrderListComposable(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                navController = navController,
                cart = user.value.cart,
                updatable = dataBase != null,
                update = {
                    if (dataBase != null) {
                        updateUser(dataBase = dataBase, user = user)
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavController = rememberNavController(),
    dataBase: FirebaseFirestore? = null,
    preview: Boolean = false
) {
    val user = remember { mutableStateOf(User()) }
    val cart = remember { mutableStateOf(Cart()) }

    if (!preview && dataBase != null) {
        if (Firebase.auth.getCurrentUser() == null) {
            navController.navigate(SPLASH)
        }

        getUser(dataBase = dataBase, navController = navController, user = user)

    } else {
        cart.value.items.add(
            Order(
                Product(
                    title = "product n°1",
                    category = "a",
                    price = 100.78f,
                    image = "https://picsum.photos/200",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
                ), 2
            )
        )
        cart.value.items.add(
            Order(
                Product(
                    title = "product n°2",
                    category = "b",
                    price = 50.95f,
                    image = "https://picsum.photos/200",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
                ), 5
            )
        )
    }

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    Column(modifier = modifier) {
        ModalNavigationDrawer(
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(state = rememberScrollState())
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(10.dp),
                        )
                ) {
                    user.value.history.forEachIndexed { index, _cart ->
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 5.dp, vertical = 30.dp)
                                .clickable { cart.value = _cart }
                                .width(100.dp)
                                .height(50.dp),
                            shape = MaterialTheme.shapes.extraLarge,
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 50.dp,
                                pressedElevation = 10.dp
                            ),
                            border = BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.onTertiaryContainer
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        ) {
                            val date = _cart.date.toDate()
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            )
                            {
                                Text(
                                    text = "${DateFormat.format("dd/mm/yyyy", date)}",
                                    color = MaterialTheme.colorScheme.onTertiaryContainer)
                            }
                        }
                    }
                }
            },
            drawerState = drawerState
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .fillMaxHeight(0.05f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { coroutineScope.launch { drawerState.open() } })
                    {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu Icon",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,)
                    }
                }

                OrderListComposable(
                    navController = navController,
                    updatable = false,
                    update = { /*Nothing*/ },
                    cart = cart.value
                )

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderListComposable(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavController,
    updatable: Boolean,
    update: () -> Unit,
    cart: Cart,
) {

    val updateSwitch = remember{ mutableStateOf(true) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(rememberNestedScrollInteropConnection())
    ) {
        stickyHeader {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxHeight(0.1f),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .height(70.dp)
                        .width(70.dp),
                    painter = painterResource(R.drawable.shopping_cart),
                    contentDescription = null
                )

                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.8f),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Total:",
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        key(updateSwitch.value)
                        {
                            Text(
                                text = "€${cart.total()}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 30.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }
                    }
                }
            }
        }

        for (order in cart.items) {
            item {
                val amount = remember {
                    mutableStateOf(order.amount)
                }
                Button(modifier = Modifier
                    .fillParentMaxWidth()
                    .fillParentMaxHeight(0.3f)
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    onClick = { if (order.product.id != "") navController.navigate("$PRODUCT_DETAILS/${order.product.id}") })
                {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.7f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        )
                        {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.3f)
                                    .padding(5.dp),
                                model = order.product.image,
                                contentScale = ContentScale.Crop,
                                contentDescription = null
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.55f)
                                    .padding(5.dp),
                            )
                            {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.5f)
                                )
                                {
                                    Text(
                                        text = order.product.title,
                                        fontStyle = FontStyle.Italic,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                                {
                                    Text(
                                        text = "${order.product.price * order.amount}€",
                                        fontStyle = FontStyle.Normal,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.ExtraLight,
                                        fontFamily = FontFamily.Monospace,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }

                        }
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.5f)
                                .background(
                                    color = MaterialTheme.colorScheme.tertiaryContainer,
                                    shape = RoundedCornerShape(
                                        topEnd = 0.dp,
                                        topStart = 0.dp,
                                        bottomEnd = 20.dp,
                                        bottomStart = 20.dp
                                    )
                                ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.25f),
                                onClick = {
                                    amount.value = amount.value - 1

                                    if (amount.value == 0) {
                                        cart.items.remove(order)
                                        navController.navigate(CART)
                                    } else {
                                        order.amount = amount.value
                                    }
                                    update()
                                    updateSwitch.value = !updateSwitch.value

                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                                ),
                                enabled = updatable && order.amount > 0,
                            ) {
                                if (updatable) {
                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowLeft,
                                        contentDescription = "More",
                                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.66f)
                                    .clip(RoundedCornerShape(1.dp))
                                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                                    .padding(1.dp),
                                contentAlignment = Alignment.Center
                            )
                            {
                                Text(
                                    text = "${amount.value}",
                                    fontStyle = FontStyle.Normal,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraLight,
                                    fontFamily = FontFamily.Monospace,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }

                            IconButton(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                                onClick = {
                                    if (amount.value < order.product.stock) {
                                        amount.value = amount.value + 1

                                        order.amount = amount.value

                                        update()
                                        updateSwitch.value = !updateSwitch.value

                                    }
                                },
                                enabled = updatable && amount.value <= order.product.stock,
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                                )

                                ) {
                                if (updatable) {
                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowRight,
                                        contentDescription = "Less",
                                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderHistoryPreview() {
    ShoppingAppTheme {
        OrderHistoryScreen(preview = true)
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    ShoppingAppTheme {
        CartScreen(dataBase = null, preview = true)
    }
}