package com.armaan.coffeemasters

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.firestore

class DataManager(app: Application): AndroidViewModel(app) {
    var menu: List<Category> by mutableStateOf(listOf())
    var cart: List<ItemInCart> by mutableStateOf(listOf())
    private val db = Firebase.firestore
    private val userCollection = db.collection("users")
    private val menuCollection = db.collection("menu")

    init {
        fetchData()
    }


    private fun fetchData() {
        menuCollection.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if(!task.result.isEmpty) {
                    menu = task.result.toObjects(Category::class.java)
                    Log.d("menu", menu.toString())
                }
            }
        }
    }

    fun getCart(userData: UserData) {
        cart = listOf()
        val collRef = userCollection.document(userData.userId).collection("cart")
        collRef.get().addOnCompleteListener { task ->
            if(task.isSuccessful) {
                cart = task.result.toObjects(ItemInCart::class.java)
                Log.d("cart", cart.toString())
            }
        }
    }

    fun cartAdd(product: Product, user: UserData){
        var found = false
        val cartColl = userCollection.document(user.userId).collection("cart")
        cart.forEach{
            if (product.id == it.product?.id!!) {
                it.quantity = it.quantity?.plus(1)
                cartColl.whereEqualTo(FieldPath.of("product", "id"),product.id).get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val doc = task.result.documents[0]
                        doc?.reference?.update("quantity", it.quantity)
                    }
                }
                found = true
            }
        }
        if (!found) {
            cart = listOf(*cart.toTypedArray(), ItemInCart(product, 1))
            cartColl.add(ItemInCart(product, 1))
        }
    }

    fun clear() {
        cart = listOf()
    }


    fun cartRemove(product: Product, user: UserData) {
        val cartColl = userCollection.document(user.userId).collection("cart")
        val aux = cart.toMutableList()
        var multiple = false
        var q = 0
        cart.forEach {
            if (product.id == it.product?.id && it.quantity!! > 1) {
                q = it.quantity!!
                multiple = true
            }
        }
        cartColl.whereEqualTo(FieldPath.of("product", "id"),product.id).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result.documents[0].reference.delete()
            }
        }

        aux.removeAll{ it.product?.id == product.id}
        if(!multiple) {
            cart = listOf(*aux.toTypedArray())
        }
        else {
            cart = listOf(*aux.toTypedArray(), ItemInCart(product, q-1))
            cartColl.add(ItemInCart(product, q-1))
        }
    }
}