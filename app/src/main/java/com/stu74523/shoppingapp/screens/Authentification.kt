@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.stu74523.shoppingapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.stu74523.shoppingapp.R
import com.stu74523.shoppingapp.models.Address
import com.stu74523.shoppingapp.models.Geolocation
import com.stu74523.shoppingapp.models.Name
import com.stu74523.shoppingapp.models.User
import com.stu74523.shoppingapp.routes.*
import com.stu74523.shoppingapp.ui.theme.ShoppingAppTheme

fun signUp(
    email: String,
    password: String,
    navController: NavController,
    dataBase: FirebaseFirestore
) {
    Firebase.auth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener {
            val user = User(id = it.user!!.uid)
            dataBase.collection("users").add(user)
            navController.navigate(CREATE_USER)
        }
        .addOnFailureListener {
            /*TODO: Error Handler*/
        }
}

fun signIn(email: String, password: String, navController: NavController)
{
    Firebase.auth.signInWithEmailAndPassword(email, password)
        .addOnSuccessListener {
            navController.navigate(SPLASH)

        }
        .addOnFailureListener {
            navController.navigate(SIGN_UP)
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavController = rememberNavController()
) {
    val storeIcon = painterResource(R.drawable.store)
    val googleIcon = painterResource(R.drawable.google)
    val appleIcon = painterResource(R.drawable.apple)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        //Top Illustration and message
        Column(
            modifier = Modifier
                .width(500.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        )
        {
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                painter = storeIcon,
                contentDescription = null
            )
            Row(
                modifier = Modifier
                    .height(70.dp),
                verticalAlignment = Alignment.Bottom,
            )
            {
                Text(text = "Welcome back you've been missed!")
            }
        }

        //User Input
        Column(
            modifier = Modifier
                .width(500.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                //Email
                var email by remember { mutableStateOf("") }
                Box(
                    modifier = Modifier
                        .padding(5.dp),
                )
                {

                    TextField(modifier = Modifier
                        .width(350.dp)
                        .height(60.dp),
                        placeholder = { Text(text = "Email") },
                        shape = RoundedCornerShape(80.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                        value = email,
                        onValueChange = { email = it })
                }

                //Password
                var password by remember { mutableStateOf("") }
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                )
                {

                    TextField(modifier = Modifier
                        .width(350.dp)
                        .height(60.dp),
                        placeholder = { Text(text = "Password") },
                        shape = RoundedCornerShape(80.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                        value = password,
                        onValueChange = { password = it })
                }

                //Forgot Password
                Row(
                    modifier = Modifier
                        .width(350.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.End,
                )
                {
                    TextButton(
                        onClick = { navController.navigate(WORK_IN_PROGRESS) },
                        contentPadding = PaddingValues(0.dp, 0.dp),
                        modifier = Modifier.height(20.dp)
                    )
                    {
                        Text(
                            text = "Forgot Password?",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }

                //Login
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                )
                {

                    Button(
                        onClick = {
                            signIn(
                                email = email,
                                password = password,
                                navController = navController
                            )
                        },
                        modifier = Modifier
                            .width(350.dp)
                            .height(70.dp),
                        shape = RoundedCornerShape(5.dp),
                        enabled = password.isNotEmpty() && email.isNotEmpty()
                    )
                    {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        )
                        {
                            Text(
                                text = "Login",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }

                }
            }
        }

        //Special options
        Column(
            modifier = Modifier
                .width(500.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        )
        {
            //Continue With
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,

                )
            {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(4.dp)
                        .padding(1.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(1.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .padding(1.dp)
                )
                {
                    Text(text = "Or Continue With", fontSize = 15.sp)
                }
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(4.dp)
                        .padding(1.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(1.dp)
                        )
                )

            }

            // Sign In Options
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .padding(10.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center,
                )
                {
                    Image(
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp),
                        painter = googleIcon,
                        contentDescription = null
                    )
                }

                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .padding(10.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center,
                )
                {
                    Image(
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp),
                        painter = appleIcon,
                        contentDescription = null
                    )
                }
            }

            //Register now
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
            )
            {
                Row(
                    modifier = Modifier
                        .padding(1.dp)
                )
                {
                    Text(text = "Not a member?", fontSize = 15.sp)
                    TextButton(
                        onClick = { navController.navigate(SIGN_UP) },
                        contentPadding = PaddingValues(0.dp, 0.dp),
                        modifier = Modifier.height(20.dp)
                    )
                    {
                        Text(
                            text = "Register Now",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavController = rememberNavController(),
    dataBase: FirebaseFirestore?,
) {
    val storeIcon = painterResource(R.drawable.store)
    val googleIcon = painterResource(R.drawable.google)
    val appleIcon = painterResource(R.drawable.apple)
    var errMsg: String? by remember { mutableStateOf(null) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        //Top Illustration and message
        Column(
            modifier = Modifier
                .width(500.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        )
        {
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                painter = storeIcon,
                contentDescription = null
            )
            Row(
                modifier = Modifier
                    .height(50.dp),
                verticalAlignment = Alignment.Bottom,
            )
            {
                Text(text = "Let's create an account for you")
            }
            if (errMsg != null) {
                Row(
                    modifier = Modifier
                        .height(20.dp),
                    verticalAlignment = Alignment.Bottom,
                )
                {
                    Text(text = errMsg!!, color = MaterialTheme.colorScheme.error)
                }
            }
        }

        //User Input
        Column(
            modifier = Modifier
                .width(500.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                var email by remember { mutableStateOf("") }

                //Email
                Box(
                    modifier = Modifier
                        .padding(5.dp),
                )
                {

                    TextField(modifier = Modifier
                        .width(350.dp)
                        .height(60.dp),
                        placeholder = { Text(text = "Email") },
                        shape = RoundedCornerShape(80.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                        value = email,
                        onValueChange = { email = it })
                }

                //Password
                var password by remember { mutableStateOf("") }
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                )
                {

                    TextField(modifier = Modifier
                        .width(350.dp)
                        .height(60.dp),
                        placeholder = { Text(text = "Password") },
                        shape = RoundedCornerShape(80.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ), value = password,
                        onValueChange = { password = it })
                }

                //Confirm Password
                var confirmPassword by remember { mutableStateOf("") }
                val isMatching by remember { derivedStateOf { (password == confirmPassword) } }
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                )
                {

                    TextField(modifier = Modifier
                        .width(350.dp)
                        .height(60.dp),
                        placeholder = { Text(text = "Confirm Password"/*, color = textColor*/) },
                        shape = RoundedCornerShape(80.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ), value = confirmPassword,
                        isError = !isMatching,
                        onValueChange = { confirmPassword = it })
                }
                //Sign Up
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                )
                {
                    Button(
                        onClick = {
                            if (dataBase != null) {
                                signUp(
                                    email = email,
                                    password = password,
                                    navController = navController,
                                    dataBase = dataBase
                                )
                            }
                        },
                        modifier = Modifier
                            .width(350.dp)
                            .height(70.dp),
                        shape = RoundedCornerShape(5.dp),
                        enabled = (isMatching && password.isNotEmpty() && email.isNotEmpty()),
                    )
                    {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        )
                        {
                            Text(
                                text = "Sign Up",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(5.dp),
                                fontWeight = FontWeight.Black
                            )
                        }
                    }

                }
            }

        }


        //Special options
        Column(
            modifier = Modifier
                .width(500.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        )
        {
            //Continue With
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,

                )
            {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(4.dp)
                        .padding(1.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(1.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .padding(1.dp)
                )
                {
                    Text(text = "Or Continue With", fontSize = 15.sp)
                }
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(4.dp)
                        .padding(1.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(1.dp)
                        )
                )

            }

            // Sign In Options
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .padding(10.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center,
                )
                {
                    Image(
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp),
                        painter = googleIcon,
                        contentDescription = null
                    )
                }

                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .padding(10.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center,
                )
                {
                    Image(
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp),
                        painter = appleIcon,
                        contentDescription = null
                    )
                }
            }

            //Login now
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
            )
            {
                Row(
                    modifier = Modifier
                        .padding(1.dp)
                )
                {
                    Text(text = "Already a member?", fontSize = 15.sp)
                    TextButton(
                        onClick = { navController.navigate(LOGIN) },
                        contentPadding = PaddingValues(0.dp, 0.dp),
                        modifier = Modifier.height(20.dp)
                    )
                    {
                        Text(
                            text = "Login Now",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUserScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavController = rememberNavController(),
    dataBase: FirebaseFirestore?
) {
    val userIcon = painterResource(R.drawable.user)
    var errMsg: String? by remember { mutableStateOf(null) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        //Top Illustration and message
        Column(
            modifier = Modifier
                .width(500.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        )
        {
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                painter = userIcon,
                contentDescription = null
            )
            Row(
                modifier = Modifier
                    .height(50.dp),
                verticalAlignment = Alignment.Bottom,
            )
            {
                Text(text = "Tell us more about You!")
            }
            if (errMsg != null) {
                Row(
                    modifier = Modifier
                        .height(20.dp),
                    verticalAlignment = Alignment.Bottom,
                )
                {
                    Text(text = errMsg!!, color = MaterialTheme.colorScheme.error)
                }
            }
        }

        //User Input
        Column(
            modifier = Modifier
                .width(500.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                var userName by remember { mutableStateOf("") }
                //Username
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .height(60.dp)
                        .fillMaxWidth(),
                )
                {

                    TextField(modifier = Modifier
                        .fillMaxSize(),
                        placeholder = { Text(text = "Username") },
                        shape = RoundedCornerShape(80.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                        value = userName,
                        onValueChange = { userName = it })
                }

                //Name
                var firstName by remember { mutableStateOf("") }
                var lastName by remember { mutableStateOf("") }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .height(60.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    //first Name
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight()
                    )
                    {

                        TextField(modifier = Modifier
                            .fillMaxSize(),
                            placeholder = { Text(text = "Fist Name") },
                            shape = RoundedCornerShape(80.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ), value = firstName,
                            onValueChange = { firstName = it })
                    }

                    //Last Name
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                    {

                        TextField(modifier = Modifier
                            .fillMaxSize(),
                            placeholder = { Text(text = "Last Name") },
                            shape = RoundedCornerShape(80.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ), value = lastName,
                            onValueChange = { lastName = it })
                    }

                }

                var phoneNumber by remember { mutableStateOf("") }
                //Username
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .height(60.dp)
                        .fillMaxWidth(),
                )
                {

                    TextField(modifier = Modifier
                        .fillMaxSize(),
                        placeholder = { Text(text = "PhoneNumber") },
                        shape = RoundedCornerShape(80.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it })
                }

                //City
                var city by remember { mutableStateOf("") }
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                )
                {

                    TextField(modifier = Modifier
                        .fillMaxSize(),
                        placeholder = { Text(text = "City") },
                        shape = RoundedCornerShape(80.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ), value = city,
                        onValueChange = { city = it })
                }

                //Zipcode
                var zipcode by remember { mutableStateOf("") }
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                )
                {

                    TextField(modifier = Modifier
                        .fillMaxSize(),
                        placeholder = { Text(text = "Zipcode") },
                        shape = RoundedCornerShape(80.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ), value = zipcode,
                        onValueChange = { zipcode = it })
                }

                //Address
                var number by remember { mutableStateOf("") }
                var street by remember { mutableStateOf("") }
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    //Street Number
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .fillMaxHeight()
                    )
                    {

                        TextField(modifier = Modifier
                            .fillMaxSize(),
                            placeholder = { Text(text = "Number") },
                            shape = RoundedCornerShape(80.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ), value = number,
                            onValueChange = { number = it })
                    }

                    //Street
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                    {

                        TextField(modifier = Modifier
                            .fillMaxSize(),
                            placeholder = { Text(text = "Street") },
                            shape = RoundedCornerShape(80.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ), value = street,
                            onValueChange = { street = it })
                    }
                }


                //Confirm
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                )
                {

                    /*TODO: Create User*/

                    Button(
                        onClick = {
                            if (dataBase != null) {
                                updateUser(
                                    navController = navController,
                                    dataBase = dataBase,
                                    username = userName,
                                    name = Name(first = firstName, last = lastName),
                                    address = Address(
                                        number = number,
                                        street = street,
                                        city = city,
                                        zipcode = zipcode,
                                    ),
                                    phoneNumber = phoneNumber,
                                )
                            }
                        },
                        modifier = Modifier
                            .width(350.dp)
                            .height(70.dp),
                        shape = RoundedCornerShape(5.dp),
                        /*TODO: fill in this*/
                        //enabled = (password.isNotEmpty() && userName.isNotEmpty()),
                    )
                    {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        )
                        {
                            Text(
                                text = "Confirm",
                                fontSize = 18.sp,
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


@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    ShoppingAppTheme {
        SignUpScreen(dataBase = null)
    }
}

@Preview(showBackground = true)
@Composable
fun CreateUserPreview() {
    ShoppingAppTheme {
        CreateUserScreen(dataBase = null)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    ShoppingAppTheme {
        LoginScreen()
    }
}