package com.stu74523.shoppingapp.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.stu74523.shoppingapp.R
import com.stu74523.shoppingapp.models.Address
import com.stu74523.shoppingapp.models.Geolocation
import com.stu74523.shoppingapp.models.Name
import com.stu74523.shoppingapp.models.User
import com.stu74523.shoppingapp.routes.ABOUT_THIS_APP
import com.stu74523.shoppingapp.routes.CREATE_USER
import com.stu74523.shoppingapp.routes.LOGIN
import com.stu74523.shoppingapp.routes.ORDER_HISTORY
import com.stu74523.shoppingapp.routes.SIGN_UP
import com.stu74523.shoppingapp.routes.SPLASH
import com.stu74523.shoppingapp.ui.theme.ShoppingAppTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


fun getUser(dataBase: FirebaseFirestore, navController: NavController, user: MutableState<User>) {
    if(Firebase.auth.uid == null)
    {
        navController.navigate(SPLASH)
    }
    val docRef = dataBase.collection("users").whereEqualTo("id", Firebase.auth.uid)

    docRef.get()
        .addOnSuccessListener {
            if (it.isEmpty) {
                navController.navigate(CREATE_USER)
            }
            for (doc in it.documents) {
                user.value = doc.toObject<User>()!!
            }
        }
        .addOnFailureListener {
            navController.navigate(SPLASH)
        }

}

fun updateUser(dataBase: FirebaseFirestore, user: MutableState<User>) {
    val docRef = dataBase.collection("users")
    val query = docRef.whereEqualTo("id", Firebase.auth.uid)

    query.get()
        .addOnSuccessListener {
            for (doc in it.documents) {
                doc.reference.set(user.value)
            }
        }
}


fun updateUser(
    navController: NavController,
    dataBase: FirebaseFirestore,
    username: String? = null,
    name: Name? = null,
    address: Address? = null,
    phoneNumber: String? = null,
) {
    val ref = dataBase.collection("users").whereEqualTo("id", Firebase.auth.currentUser!!.uid).get()
        .addOnSuccessListener { snapshot ->
            for (document in snapshot.documents) {
                val userRef = document.reference

                if (username != null) {
                    userRef.update("username", username)
                }

                if (phoneNumber != null) {
                    userRef.update("phoneNumber", phoneNumber)
                }

                if (name != null) {
                    userRef.update("name", name)
                }

                if (address != null) {
                    userRef.update("address", address)
                }
            }

            navController.navigate(SPLASH)
        }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavController = rememberNavController(),
    dataBase: FirebaseFirestore?,
) {
    var user = remember { mutableStateOf(User()) }
    val loading = remember { mutableStateOf(dataBase != null) }

    if (dataBase != null) {
        if (Firebase.auth.getCurrentUser() == null) {
            navController.navigate(SPLASH)
        }

        getUser(dataBase = dataBase, navController = navController, user = user)
        loading.value = user.value.id == ""
    }

    if (loading.value) {
        Loading()
    } else {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.Top
        ) {
            stickyHeader {
                //Top Illustration and message
                Column(
                    modifier = Modifier
                        .width(500.dp)
                        .padding(bottom = 20.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(bottomEnd = 50.dp, bottomStart = 50.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                )
                {
                    Image(
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp),
                        painter = painterResource(R.drawable.user),
                        contentDescription = null
                    )

                    val username = remember {
                        mutableStateOf(
                            if (dataBase == null || user.value.name.first == "" || user.value.name.last == "") {
                                "UserName"
                            } else {
                                user.value.username
                            }
                        )
                    }
                    val keyboardController = LocalSoftwareKeyboardController.current
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = username.value,
                        onValueChange = { username.value = it },
                        textStyle = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.Center),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            containerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                if (dataBase != null) {
                                    updateUser(
                                        navController = navController,
                                        dataBase = dataBase,
                                        username = username.value
                                    )
                                }
                            }),

                        )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Create,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 20.dp)
                        .clip(RoundedCornerShape(30.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    val nameDroped = remember { mutableStateOf(false) }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.primaryContainer),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .height(75.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (dataBase == null) {
                                    "Name: FirstName LastName"
                                } else {
                                    "Name: ${user.value.name}"
                                },
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                            IconButton(onClick = { nameDroped.value = !nameDroped.value }) {
                                Icon(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(100.dp),
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = "DropDown Icon",
                                    tint = MaterialTheme.colorScheme.secondaryContainer
                                )
                            }
                        }
                        if (nameDroped.value) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .background(color = MaterialTheme.colorScheme.tertiaryContainer)
                                    .height(200.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                )
                                {
                                    var firstName by remember { mutableStateOf("") }
                                    var lastName by remember { mutableStateOf("") }

                                    TextField(
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .fillMaxWidth()
                                            .fillMaxHeight(0.33f),
                                        placeholder = { Text(text = "First Name") },
                                        shape = RoundedCornerShape(80.dp),
                                        colors = TextFieldDefaults.textFieldColors(
                                            disabledTextColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent,
                                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        ),
                                        value = firstName,
                                        onValueChange = { firstName = it },
                                    )

                                    TextField(modifier = Modifier
                                        .padding(2.dp)
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.5f),
                                        placeholder = { Text(text = "Last Name") },
                                        shape = RoundedCornerShape(80.dp),
                                        colors = TextFieldDefaults.textFieldColors(
                                            disabledTextColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent,
                                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        ),
                                        value = lastName,
                                        onValueChange = { lastName = it })

                                    Button(
                                        onClick = {
                                            if (dataBase != null) {
                                                updateUser(
                                                    navController = navController,
                                                    dataBase = dataBase,
                                                    name = Name(first = firstName, last = lastName),
                                                )
                                            }
                                        },
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        shape = RoundedCornerShape(10.dp),
                                        elevation = ButtonDefaults.buttonElevation(
                                            defaultElevation = 20.dp,
                                            pressedElevation = 0.dp,
                                            disabledElevation = 0.dp
                                        ),
                                        border = BorderStroke(
                                            2.dp,
                                            MaterialTheme.colorScheme.onSecondaryContainer
                                        ),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center,
                                        )
                                        {
                                            Text(
                                                text = "Confirm Changes",
                                                modifier = Modifier.padding(5.dp),
                                                fontWeight = FontWeight.Black
                                            )
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 20.dp)
                        .clip(RoundedCornerShape(30.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    //Name
                    val phoneNumberDroped = remember { mutableStateOf(false) }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.primaryContainer),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .height(75.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (dataBase == null) {
                                    "Phone Number: 00 00 00 00 00"
                                } else {
                                    "Phone Number: ${user.value.phoneNumber}"
                                },
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                            IconButton(onClick = {
                                phoneNumberDroped.value = !phoneNumberDroped.value
                            }) {
                                Icon(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(100.dp),
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = "DropDown Icon",
                                    tint = MaterialTheme.colorScheme.secondaryContainer
                                )
                            }
                        }
                        if (phoneNumberDroped.value) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .background(color = MaterialTheme.colorScheme.tertiaryContainer)
                                    .height(150.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                )
                                {
                                    var phoneNumber by remember { mutableStateOf("") }

                                    TextField(modifier = Modifier
                                        .padding(2.dp)
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.5f),
                                        placeholder = { Text(text = "Phone Number") },
                                        shape = RoundedCornerShape(80.dp),
                                        colors = TextFieldDefaults.textFieldColors(
                                            disabledTextColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent,
                                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        ),
                                        value = phoneNumber,
                                        onValueChange = { phoneNumber = it })

                                    Button(
                                        onClick = {
                                            if (dataBase != null) {
                                                updateUser(
                                                    navController = navController,
                                                    dataBase = dataBase,
                                                    phoneNumber = phoneNumber,
                                                )
                                            }
                                        },
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        shape = RoundedCornerShape(10.dp),
                                        elevation = ButtonDefaults.buttonElevation(
                                            defaultElevation = 20.dp,
                                            pressedElevation = 0.dp,
                                            disabledElevation = 0.dp
                                        ),
                                        border = BorderStroke(
                                            2.dp,
                                            MaterialTheme.colorScheme.onSecondaryContainer
                                        ),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                        )

                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center,
                                        )
                                        {
                                            Text(
                                                text = "Confirm Changes",
                                                modifier = Modifier.padding(5.dp),
                                                fontWeight = FontWeight.Black
                                            )
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 20.dp)
                        .clip(RoundedCornerShape(30.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {

                    val addressDroped = remember { mutableStateOf(false) }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.primaryContainer),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .height(250.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (dataBase == null) {
                                    "Address: 00 street, city"
                                } else {
                                    "Address: ${user.value.address}"
                                },
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                                    .height(250.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val cameraPositionState = rememberCameraPositionState {
                                    position = CameraPosition.fromLatLngZoom(
                                        LatLng(
                                            user.value.address.geolocation.latitude,
                                            user.value.address.geolocation.longitude
                                        ), 10f
                                    )
                                }
                                GoogleMap(
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f)
                                        .padding(5.dp)
                                        .fillMaxHeight(),
                                    cameraPositionState = cameraPositionState
                                ) {
                                    Marker(
                                        state = MarkerState(
                                            position = cameraPositionState.position.target
                                        )
                                    )
                                }

                                IconButton(onClick = {
                                    addressDroped.value = !addressDroped.value
                                }) {
                                    Icon(
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(100.dp),
                                        imageVector = Icons.Filled.ArrowDropDown,
                                        contentDescription = "DropDown Icon",
                                        tint = MaterialTheme.colorScheme.secondaryContainer
                                    )
                                }
                            }
                        }

                        if (addressDroped.value) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .background(color = MaterialTheme.colorScheme.tertiaryContainer)
                                    .height(300.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                )
                                {

                                    //City
                                    var city by remember { mutableStateOf("") }
                                    TextField(modifier = Modifier
                                        .padding(2.dp)
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.25f),
                                        placeholder = { Text(text = "City") },
                                        shape = RoundedCornerShape(80.dp),
                                        colors = TextFieldDefaults.textFieldColors(
                                            disabledTextColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent,
                                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        ),
                                        value = city,
                                        onValueChange = { city = it })


                                    //Zipcode
                                    var zipcode by remember { mutableStateOf("") }
                                    TextField(modifier = Modifier
                                        .padding(2.dp)
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.33f),
                                        placeholder = { Text(text = "zipcode") },
                                        shape = RoundedCornerShape(80.dp),
                                        colors = TextFieldDefaults.textFieldColors(
                                            disabledTextColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent,
                                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        ),
                                        value = zipcode,
                                        onValueChange = { zipcode = it })

                                    //Address
                                    var number by remember { mutableStateOf("") }
                                    var street by remember { mutableStateOf("") }
                                    Row(
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .fillMaxWidth()
                                            .fillMaxHeight(0.5f),
                                        horizontalArrangement = Arrangement.SpaceAround,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {


                                        TextField(modifier = Modifier
                                            .fillParentMaxWidth(0.33f)
                                            .fillMaxHeight(),
                                            placeholder = { Text(text = "Numberr") },
                                            shape = RoundedCornerShape(80.dp),
                                            colors = TextFieldDefaults.textFieldColors(
                                                disabledTextColor = Color.Transparent,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                disabledIndicatorColor = Color.Transparent,
                                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                            ),
                                            value = number,
                                            onValueChange = { number = it })

                                        TextField(modifier = Modifier
                                            .fillMaxSize(),
                                            placeholder = { Text(text = "Street") },
                                            shape = RoundedCornerShape(80.dp),
                                            colors = TextFieldDefaults.textFieldColors(
                                                disabledTextColor = Color.Transparent,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                disabledIndicatorColor = Color.Transparent,
                                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                            ),
                                            value = street,
                                            onValueChange = { street = it })

                                    }

                                    Button(
                                        onClick = {
                                            if (dataBase != null) {
                                                updateUser(
                                                    navController = navController,
                                                    dataBase = dataBase,
                                                    address = Address(
                                                        number = number,
                                                        street = street,
                                                        city = city,
                                                        zipcode = zipcode,
                                                    ),
                                                )
                                            }
                                        },
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        shape = RoundedCornerShape(10.dp),
                                        elevation = ButtonDefaults.buttonElevation(
                                            defaultElevation = 20.dp,
                                            pressedElevation = 0.dp,
                                            disabledElevation = 0.dp
                                        ),
                                        border = BorderStroke(
                                            2.dp,
                                            MaterialTheme.colorScheme.onSecondaryContainer
                                        ),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center,
                                        )
                                        {
                                            Text(
                                                text = "Confirm Changes",
                                                modifier = Modifier.padding(5.dp),
                                                fontWeight = FontWeight.Black
                                            )
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }

            item {
                Button(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(75.dp)
                        .padding(vertical = 5.dp, horizontal = 20.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    onClick = { navController.navigate(ORDER_HISTORY) })
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .height(75.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "See Order History",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondaryContainer
                        )
                    }
                }
            }

            item {
                Button(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(75.dp)
                        .padding(vertical = 5.dp, horizontal = 20.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    onClick = {
                        Firebase.auth.signOut()
                        navController.navigate(SPLASH)
                    },
                )
                {
                    Text(text = "Log Out")
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillParentMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextButton(
                        onClick = { navController.navigate(ABOUT_THIS_APP) },
                        contentPadding = PaddingValues(0.dp, 0.dp),
                        modifier = Modifier.height(20.dp)
                    )
                    {
                        Text(
                            text = "About this App",
                            fontSize = 15.sp,
                        )
                    }
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserDetailsPreview() {
    ShoppingAppTheme {
        UserDetailsScreen(dataBase = null)
    }
}
