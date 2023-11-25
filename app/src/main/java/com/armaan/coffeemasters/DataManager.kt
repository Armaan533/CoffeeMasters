package com.armaan.coffeemasters

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

class DataManager(app: Application): AndroidViewModel(app) {
    var menu: List<Category> by mutableStateOf(listOf())
    var cart: List<ItemInCart> by mutableStateOf(listOf())
    val db = Firebase.firestore

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            menu = API.menuService.fetchMenu()
        }
    }

    fun cartAdd(product: Product){
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
}