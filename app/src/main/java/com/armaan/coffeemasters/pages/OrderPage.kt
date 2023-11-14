package com.armaan.coffeemasters.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.armaan.coffeemasters.DataManager
import com.armaan.coffeemasters.ItemInCart
import com.armaan.coffeemasters.Product
import com.armaan.coffeemasters.R


@Composable
fun OrderPage(dataManager: DataManager, padding: PaddingValues) {
    if (dataManager.cart.isEmpty()) {
        Box(
            modifier = Modifier.background(Color.White)
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.empty_cart),
                    contentDescription = "Empty Cart",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
    else {
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {

            items(items = dataManager.cart){
                CartItem(it = it, onDelete = { product ->
                    dataManager.cartRemove(product)
                })
            }
            item {
                Divider()
                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ){
                    Text(text = "Total Amount")

                }
            }
        }
    }
}

private fun totalSum(cart: List<ItemInCart>) : Double
{
    var sum = 0.0
    cart.forEach {
        sum += (it.product.price * it.quantity)
    }
    return sum
}


@Composable
fun CartItem(it: ItemInCart, onDelete: (Product)->Unit) {
    Row (
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ){
        Text(text = "${it.quantity}x")
        Text(
            text = it.product.name,
            modifier = Modifier.width(150.dp)
        )
        Text(
            text = "$${(it.quantity * it.product.price).format(2)}",
            modifier = Modifier.width(50.dp)
        )
        Image(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier.clickable{
                onDelete(it.product)
            }
        )
    }
}