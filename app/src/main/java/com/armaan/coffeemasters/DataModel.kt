package com.armaan.coffeemasters

class Product(var id: Int, var name: String, var price: Double, private var image: String){
    val imageUrl get() = "https://firtman.github.io/coffeemasters/api/images/${image}"
}


class Category(var name: String, var products: MutableList<Product>)

class ItemInCart(var product: Product, var quantity: Int)