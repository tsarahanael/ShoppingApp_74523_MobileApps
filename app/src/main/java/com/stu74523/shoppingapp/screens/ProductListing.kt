package com.stu74523.shoppingapp.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.stu74523.shoppingapp.models.Order
import com.stu74523.shoppingapp.models.Product
import com.stu74523.shoppingapp.models.User
import com.stu74523.shoppingapp.routes.CART
import com.stu74523.shoppingapp.routes.PRODUCT_DETAILS
import com.stu74523.shoppingapp.routes.SPLASH
import com.stu74523.shoppingapp.ui.theme.ShoppingAppTheme
import kotlinx.coroutines.tasks.await
import kotlin.random.Random


fun getCategories(dataBase: FirebaseFirestore, categories: MutableList<String>) {
    val docRef = dataBase.collection("products")
    docRef.get()
        .addOnSuccessListener {
            categories.removeAll(categories)
            categories.add("All")

            for (doc in it.documents) {
                doc.toObject<Product>()?.let { product ->
                    if (!categories.contains(product.category)) {
                        categories.add(product.category)
                    }
                }
            }
        }
}

fun getProducts(
    dataBase: FirebaseFirestore,
    category: String,
    _products: SnapshotStateList<Product>,
    max: Long = 0
) {
    val docRef = dataBase.collection("products").whereGreaterThan("stock", 0)
    var query = if (category == "All") docRef else docRef.whereEqualTo("category", category)

    if (max > 0) query = query.limit(max)
    query.get()
        .addOnSuccessListener {
            _products.clear()
            for (document in it.documents) {
                document.toObject<Product>()?.let { product ->
                    product.id = document.id
                    _products.add(product)
                }
            }
        }
        .addOnFailureListener {
            it.message?.let { it1 -> Log.e("getProducts", it1) }
        }
}

fun getProduct(
    dataBase: FirebaseFirestore,
    productId: String,
    product: MutableState<Product>
) {
    val docRef = dataBase.collection("products").document(productId)

    docRef.get()
        .addOnSuccessListener {
            product.value = it.toObject<Product>()!!
            product.value.id = productId
        }
        .addOnFailureListener {
            product.value = Product()
        }
}

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavController = rememberNavController(),
    dataBase: FirebaseFirestore?,
) {
    val _products = remember {
        mutableStateListOf<Product>()
    }
    val categories = remember { mutableStateListOf<String>() }

    val categorie = remember { mutableStateOf("All") }

    val loading = remember { mutableStateOf(dataBase != null) }

    if (dataBase != null) {
        if (Firebase.auth.currentUser == null) {
            navController.navigate(SPLASH)
        }
        getProducts(dataBase = dataBase, category = categorie.value, _products = _products)
        getCategories(dataBase = dataBase, categories = categories)
        loading.value = categories.isEmpty() || _products.isEmpty()
    } else {
        categories.add("a")
        categories.add("b")
        categories.add("c")
        _products.add(
            Product(
                title = "product n°1",
                category = "a",
                price = 100.78f,
                image = "https://picsum.photos/200"
            ),
        )
        _products.add(
            Product(
                title = "product n°2",
                category = "b",
                price = 100.78f,
                image = "https://picsum.photos/200"
            )
        )
        _products.add(
            Product(
                title = "product n°3",
                category = "c",
                price = 100.78f,
                image = "https://picsum.photos/200"
            )
        )
    }
    if (loading.value) {
        Loading()
    } else {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {

            Row(
                modifier = Modifier
                    .horizontalScroll(state = rememberScrollState())
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                for (category in categories) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 5.dp, vertical = 30.dp)
                            .height(50.dp),
                        shape = MaterialTheme.shapes.extraLarge,
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 50.dp,
                            pressedElevation = 10.dp
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onTertiaryContainer),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        Button(
                            onClick = {
                                if (dataBase != null) {
                                    categorie.value = category
                                    _products.removeAll(_products)
                                }
                            },
                            modifier = Modifier.fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        ) {
                            Text(text = category)
                        }
                    }
                }
            }

            ProductListComposable(
                modifier = Modifier.fillMaxHeight(),
                navController = navController,
                products = _products,
            )
        }
    }
}

@Composable
fun ProductListComposable(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavController = rememberNavController(),
    products: List<Product>
) {
    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
    ) {
        for (product in products) {
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                onClick = { if (product.id != "") navController.navigate("${PRODUCT_DETAILS}/${product.id}") })
            {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.3f)
                            .padding(5.dp),
                        model = product.image,
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
                                text = product.title,
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
                                text = "${product.price}€",
                                fontStyle = FontStyle.Normal,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraLight,
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                }
            }
        }
    }

}


/*TODO: Put Product Title in the navbar*/
@Composable
fun ProductDetails(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavController = rememberNavController(),
    dataBase: FirebaseFirestore?,
    productId: String? = null
) {
    var user = remember { mutableStateOf(User()) }
    val product = remember { mutableStateOf(Product()) }
    val products = remember { mutableStateListOf<Product>() }
    val selection = remember {
        mutableStateOf(0)
    }

    val loading = remember { mutableStateOf(dataBase != null) }

    if (dataBase != null) {
        if (Firebase.auth.getCurrentUser() == null) {
            navController.navigate(SPLASH)
        }
        LaunchedEffect("productDetails") {
            getUser(dataBase = dataBase, navController = navController, user = user)
            getProduct(dataBase = dataBase, productId = productId!!, product = product)
            loading.value = false
        }
        getProducts(dataBase = dataBase, category = product.value.category, products, max = 4)

    } else {
        product.value = Product(
            title = "product n°1",
            category = "a",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type ",
            image = "https://picsum.photos/200",
            price = 1 + (Random.nextFloat()) * 501,

            )

        products.add(
            Product(
                title = "product n°1",
                category = "a",
                price = 100.78f,
                image = "https://picsum.photos/200"
            )
        )

        products.add(
            Product(
                title = "product n°2",
                category = "a",
                price = 100.78f,
                image = "https://picsum.photos/200"
            )
        )

        products.add(
            Product(
                title = "product n°3",
                category = "a",
                price = 100.78f,
                image = "https://picsum.photos/200"
            )
        )

        products.add(
            Product(
                title = "product n°3",
                category = "a",
                price = 100.78f,
                image = "https://picsum.photos/200"
            )
        )

    }



    if (loading.value) {
        Loading()
    } else {
        LazyColumn(
            modifier = modifier
                .nestedScroll(rememberNestedScrollInteropConnection()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        )
        {
            item {
                Card(
                    modifier = Modifier
                        .fillParentMaxHeight(0.3f)
                        .padding(vertical = 0.dp, horizontal = 30.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    )
                    {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            model = product.value.image,
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )

                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxHeight(0.3f)
                        .padding(vertical = 10.dp, horizontal = 0.dp)
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .fillParentMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(5.dp),
                    )
                    {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.1f),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceAround
                        )
                        {
                            Text(
                                text = product.value.title,
                                fontStyle = FontStyle.Italic,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "${product.value.price}€",
                                fontStyle = FontStyle.Normal,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraLight,
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.6f),
                            verticalAlignment = Alignment.Top
                        )
                        {
                            Text(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .verticalScroll(rememberScrollState()),
                                text = product.value.description,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Left,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }

                        Row(
                            modifier = Modifier
                                .padding(2.dp)
                                .fillParentMaxWidth()
                                .fillParentMaxHeight(0.03f),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Bottom,
                        ) {
                            Text(
                                text = "Stock: ${
                                    if (dataBase == null) Random.nextInt(
                                        1,
                                        500
                                    ) else product.value.stock
                                } left",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Light,
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.33f),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { selection.value = selection.value - 1 },
                                    enabled = selection.value > 0
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowLeft,
                                        contentDescription = "More",
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(0.7f)
                                        .clip(CircleShape)
                                        .background(color = MaterialTheme.colorScheme.tertiaryContainer)
                                        .padding(3.dp),
                                    contentAlignment = Alignment.Center
                                )
                                {
                                    Text(
                                        text = "${selection.value}",
                                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    )
                                }

                                IconButton(
                                    onClick = { selection.value = selection.value + 1 },
                                    enabled = selection.value <= product.value.stock
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowRight,
                                        contentDescription = "Less",
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    )
                                }
                            }

                            Button(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.66f),
                                enabled = selection.value > 0,
                                onClick = {
                                    if (dataBase != null) {
                                        user.value.cart.items.add(
                                            element = Order(
                                                product = product.value,
                                                amount = selection.value
                                            )
                                        )
                                        updateUser(dataBase = dataBase, user = user)
                                        navController.navigate(CART)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            ) {
                                Text(
                                    text = "Add To Cart",
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        }

                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .fillParentMaxHeight(0.04f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        text = "Products in the same Category:",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

            }

            if (products.isNotEmpty()) {
                this.item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(topEnd = 30.dp, topStart = 20.dp)
                            )
                            .padding(10.dp),
                    )
                    {
                        Card(
                            modifier = Modifier
                                .fillParentMaxHeight(0.23f)
                                .fillMaxWidth(0.5f)
                                .padding(vertical = 10.dp, horizontal = 20.dp)
                                .clickable {
                                    if (products[0].id != "") navController.navigate(
                                        "$PRODUCT_DETAILS/${products[0].id}"
                                    )
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            )
                        )
                        {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.7f),
                                contentScale = ContentScale.Crop,
                                model = products[0].image,
                                contentDescription = products[0].title,
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(2.dp),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally
                            )
                            {
                                Text(
                                    text = products[0].title,
                                )
                                Text(
                                    text = " ${products[0].price}€",
                                )
                            }
                        }


                        if (products.count() >= 2) {

                            Card(
                                modifier = Modifier
                                    .fillParentMaxHeight(0.23f)
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp, horizontal = 20.dp)
                                    .clickable {
                                        if (products[1].id != "") navController.navigate(
                                            "$PRODUCT_DETAILS/${products[1].id}"
                                        )
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                            )
                            {
                                AsyncImage(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.7f),
                                    contentScale = ContentScale.Crop,
                                    model = products[1].image,
                                    contentDescription = products[1].title,
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(2.dp),
                                    verticalArrangement = Arrangement.Bottom,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                )
                                {
                                    Text(
                                        text = products[1].title,
                                    )
                                    Text(
                                        text = "${products[1].price}€",
                                    )
                                }

                            }
                        }
                    }
                }
            }
            if (products.count() >= 3) {
                item {

                    //Top Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(10.dp),
                    )
                    {
                        Card(
                            modifier = Modifier
                                .fillParentMaxHeight(0.23f)
                                .fillMaxWidth(0.5f)
                                .padding(vertical = 10.dp, horizontal = 20.dp)
                                .clickable {
                                    if (products[2].id != "") navController.navigate(
                                        "$PRODUCT_DETAILS/${products[2].id}"
                                    )
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            )

                        )
                        {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.7f),
                                contentScale = ContentScale.Crop,
                                model = products[2].image,
                                contentDescription = products[2].title,
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(2.dp),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally
                            )
                            {
                                Text(
                                    text = products[2].title,
                                )
                                Text(
                                    text = "${products[2].price}€",
                                )
                            }

                        }
                        if (products.count() == 4) {
                            Card(
                                modifier = Modifier
                                    .fillParentMaxHeight(0.23f)
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp, horizontal = 20.dp)
                                    .clickable {
                                        if (products[3].id != "") navController.navigate(
                                            "$PRODUCT_DETAILS/${products[3].id}"
                                        )
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                )

                            )
                            {
                                AsyncImage(
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                    model = products[3].image,
                                    contentDescription = products[3].title,
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(2.dp),
                                    verticalArrangement = Arrangement.Bottom,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                )
                                {
                                    Text(
                                        text = products[3].title,
                                    )
                                    Text(
                                        text = "${products[3].price}€",
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
fun ProductListPreview() {
    ShoppingAppTheme {
        ProductListScreen(dataBase = null)
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailsPreview() {
    ShoppingAppTheme {
        ProductDetails(
            dataBase = null
        )
    }
}