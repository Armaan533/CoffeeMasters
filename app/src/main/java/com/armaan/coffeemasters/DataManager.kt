package com.armaan.coffeemasters

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.armaan.coffeemasters.sign_in.UserData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class DataManager(app: Application): AndroidViewModel(app) {
    var menu: List<Category> by mutableStateOf(listOf())
    var cart: List<ItemInCart> by mutableStateOf(listOf())
    private val db = Firebase.firestore
    private val userCollection = db.collection("users")
    private val menuCollection = db.collection("category")

    init {
        fetchData()
    }

    private fun fetchData() {
//        viewModelScope.launch {
//            menu = API.menuService.fetchMenu()
//        }
        menuCollection.get().addOnSuccessListener {
            menu = it.toObjects<Category>()
        }
    }

    private fun getCart(userData: UserData) {
        val docRef = userCollection.document(userData.userId)
        docRef.get().addOnSuccessListener {
            if (it.exists()) {
                cart = it.toObject<List<ItemInCart>>()!!
            }
            else{
                userCollection.document(userData.userId).set(cart)
            }
        }
            .addOnFailureListener {
                Log.d(null, "error: ", it)
            }
    }

    fun cartAdd(product: Product, user: UserData){
        var found = false
        cart.forEach{
            if (product.id == it.product.id) {
                it.quantity += 1
                found = true
            }
        }
        if (!found) {
            cart = listOf(*cart.toTypedArray(), ItemInCart(product, 1))
        }
        val ref = userCollection.document(user.userId)
    }

    fun clear() {
        cart = listOf()
    }


    fun cartRemove(product: Product) {
        val aux = cart.toMutableList()
        var multiple = false
        var q = 0
        cart.forEach {
            if (product.id == it.product.id && it.quantity > 1) {
                q = it.quantity
                multiple = true
            }
        }
        aux.removeAll{it.product.id == product.id}
        cart = if(!multiple)
            listOf(*aux.toTypedArray())
        else
            listOf(*aux.toTypedArray(), ItemInCart(product, q-1))
    }

    fun cartAdd(product: Product) {

    }
}