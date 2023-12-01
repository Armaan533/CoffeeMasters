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

//    private fun fetchData() {
//        viewModelScope.launch {
//            menu = API.menuService.fetchMenu()
//        }
//        var name = ""
//        var prod: List<Product> = emptyList()
//        var m = menu.toMutableList()
//        for (i in 0..4) {
//            menuCollection.document("$i").get().addOnSuccessListener {
//                name = it.get("name").toString()
//            }
//            menuCollection.document("$i").collection("product").get().addOnSuccessListener {
//                prod = it.toObjects(Product::class.java).toMutableList()
//            }
//            m.add(
//                Category(
//                    name,
//                    prod.toMutableList()
//                )
//            )
//            menuCollection.document("$i").get().addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    if (task.result.exists()) {
//                        name = menu.
//                    }
//                }
//            }
//        }
//        menu = m.toTypedArray().toList()
//        Log.d("menu", menu.toString())
//    }

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
//        collRef.get().addOnSuccessListener {
//            cart = it.toObjects(ItemInCart::class.java)
//        }
//            .addOnFailureListener {
//                Log.d(null, it.message.toString())
//            }
        collRef.get().addOnCompleteListener { task ->
            if(task.isSuccessful) {
                cart = task.result.toObjects(ItemInCart::class.java)
                Log.d("cart", cart.toString())
            }
        }
    }

//    private fun refreshCart(user: UserData) {
//        val cartColl = userCollection.document(user.userId).collection("cart")
////        cart.forEach {
////            if(cartColl.whereEqualTo("product.id", it.product?.id).get().result.isEmpty) {
////                cartColl.add(it)
////            }
////        }
//
//    }

    fun cartAdd(product: Product, user: UserData){
        var found = false
        val cartColl = userCollection.document(user.userId).collection("cart")
        cart.forEach{
            if (product.id == it.product?.id!!) {
                it.quantity = it.quantity?.plus(1)
                cartColl.whereEqualTo(FieldPath.of("product", "id"),product.id).get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val doc = task.result.documents[0]
                        doc?.getDocumentReference("quantity")?.update("quantity", it.quantity)
                        Log.i("cartUpdate", doc.toString())
                    }
                }
                found = true
            }
        }
        if (!found) {
            cart = listOf(*cart.toTypedArray(), ItemInCart(product, 1))
        }

//        refreshCart(user)

    }

    fun clear() {
        cart = listOf()
//        refreshCart(user)
    }


    fun cartRemove(product: Product, user: UserData) {
        val aux = cart.toMutableList()
        var multiple = false
        var q = 0
        cart.forEach {
            if (product.id == it.product?.id && it.quantity!! > 1) {
                q = it.quantity!!
                multiple = true
            }
        }
        aux.removeAll{ it.product?.id == product.id}
        cart = if(!multiple)
            listOf(*aux.toTypedArray())
        else
            listOf(*aux.toTypedArray(), ItemInCart(product, q-1))
//        refreshCart(user)
    }
}