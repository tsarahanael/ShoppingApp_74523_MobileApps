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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.stu74523.shoppingapp.R
import com.stu74523.shoppingapp.ui.theme.ShoppingAppTheme

@Composable
fun WorkInProgressScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavController = rememberNavController()
) {

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
            val infiniteTransition = rememberInfiniteTransition(label = "scale i guess")
            val size by infiniteTransition.animateValue(
                initialValue = 100.dp,
                targetValue = 150.dp,
                typeConverter = Dp.VectorConverter,
                animationSpec = infiniteRepeatable(
                    animation = tween(800, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ), label = "scale i guess"
            )

            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .height(size)
                        .width(size),
                    painter = painterResource(id = R.drawable.icons8_work_in_progress_64),
                    contentDescription = null
                )
            }

            Row(
                modifier = Modifier
                    .height(70.dp),
                verticalAlignment = Alignment.Bottom,
            )
            {
                Text(text = "Sorry! not implemented yet", fontSize = 20.sp)
            }
            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = "< Go Back")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkInProgressPreview() {
    ShoppingAppTheme {
        WorkInProgressScreen()
    }
}