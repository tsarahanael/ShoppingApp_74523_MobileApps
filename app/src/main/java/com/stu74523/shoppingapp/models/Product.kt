package com.stu74523.shoppingapp.models

import kotlin.random.Random


data class Product(
    var id:String = "",
    var title:String = "",
    var category: String = "",
    var description:String = "",
    var image: String = "",
    var price: Float = 0f,
    var stock: Int = Random.nextInt(from = 1, until = 100),
    )