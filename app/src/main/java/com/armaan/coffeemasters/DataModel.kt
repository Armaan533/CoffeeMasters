package com.armaan.coffeemasters

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Product(
    @SerializedName("id") @Expose var id: Int? = null,
    @SerializedName("name") @Expose var name: String? = null,
    @SerializedName("price") @Expose var price: Double? = null,
    @SerializedName("description") @Expose var description: String? = null,
    @SerializedName("image") @Expose var image: String? = null
)


class Category(
    @SerializedName("name") @Expose var name: String? = null,
    @SerializedName("products") @Expose var products: List<Product>? = null
)

class ItemInCart(
    @SerializedName("product") @Expose var product: Product? = null,
    @SerializedName("quantity") @Expose var quantity: Int? = null
)


data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val username: String?,
    val pfpUrl: String?
)