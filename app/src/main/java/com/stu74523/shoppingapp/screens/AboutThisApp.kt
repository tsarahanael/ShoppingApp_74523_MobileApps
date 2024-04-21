package com.stu74523.shoppingapp.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.stu74523.shoppingapp.R
import com.stu74523.shoppingapp.routes.LOGIN
import com.stu74523.shoppingapp.ui.theme.ShoppingAppTheme

@Composable
fun AboutThisAppScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavController = rememberNavController()
) {

    val uriHandler = LocalUriHandler.current

    val projectDoc =
        "https://docs.google.com/document/d/1_UNGIUfNxzPhBk7rhd_Zrq2-O5S-pqdmJH9TRkKos2U/edit?usp=sharing"
    val gitHub = "https://github.com/tsarahanael"

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        //Top Illustration and message
        Column(
            modifier = Modifier
                .width(500.dp)
                .padding(10.dp)
                .fillMaxHeight(0.33f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        )
        {
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                painter = painterResource(id = R.drawable.store),
                contentDescription = null
            )
            Row(
                modifier = Modifier
                    .height(50.dp),
                verticalAlignment = Alignment.Bottom,
            )
            {
                Text(text = "About This App")
            }
        }

        Text(text = "This App in a Assignment that i submitted in 2024 as part of my Semester in Dorset College as a EPITA Student.")

        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Button(
                onClick = { uriHandler.openUri(projectDoc) },
                modifier = Modifier
                    .height(60.dp)
                    .padding(5.dp)
            )
            {
                Text(
                    text = "How I made this App",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black
                )
            }
            Button(
                onClick = { uriHandler.openUri(gitHub) },
                modifier = Modifier
                    .height(60.dp)
                    .padding(5.dp)
            )
            {
                Text(
                    text = "Go to my GitHub",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = "< Go Back")
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun AboutThisAppPreview() {
    ShoppingAppTheme {
        AboutThisAppScreen()
    }
}