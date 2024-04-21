package com.stu74523.shoppingapp.models

import com.google.firebase.Timestamp
import kotlin.random.Random

data class User(
    var id: String = "",
    var username: String = "",
    val name: Name = Name(),
    val address: Address = Address(),
    var phoneNumber: String = "00 00 00 00 00",
    val cart: Cart = Cart(),
    val history: MutableList<Cart> = mutableListOf()
)


data class Order(val product:Product = Product(), var amount:Int = 0)
data class Cart(val items:MutableList<Order> = mutableListOf(), var date:Timestamp = Timestamp(0,0))
{
    fun total():Float{
        var tot = 0f
        for(item in items)
        {
            tot += item.product.price * item.amount
        }
        return tot
    }
    fun empty()
    {
        items.removeAll(items)
    }
    fun copy(): Cart{
        val _items = mutableListOf<Order>()
        _items.addAll(items)
        return Cart(_items, date)
    }
}


data class Name(
    var first: String = "",
    var last: String = ""
)
{
    override fun toString(): String = "${first} ${last}"
}

data class Address(
    var geolocation: Geolocation = Geolocation(),
    var number: String = "",
    var street: String = "",
    var city: String = "",
    var zipcode: String = "",
)
{
    override fun toString(): String = "${number} ${street}, ${city}"

}

data class Geolocation(
    var longitude: Double = Random.nextDouble() * (90  + 90 ) - 90,
    var latitude: Double = Random.nextDouble() * (90  + 90 ) - 90,
)