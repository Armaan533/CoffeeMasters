package com.armaan.coffeemasters.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.armaan.coffeemasters.DataManager
import com.armaan.coffeemasters.ItemInCart
import com.armaan.coffeemasters.Product
import com.armaan.coffeemasters.R
import com.armaan.coffeemasters.Routes
import com.armaan.coffeemasters.sign_in.AuthUIClient


@Composable
fun OrderPage(dataManager: DataManager, navController: NavController, authUIClient: AuthUIClient) {
    val user = authUIClient.getSignedInUser()
    if (dataManager.cart.isEmpty()) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ){
            Image(
                painter = painterResource(id = R.drawable.empty_cart),
                contentDescription = "Empty Cart",
                modifier = Modifier
                    .fillMaxSize()
            )
            Button(
                onClick = {
                    navController.navigate(Routes.MenuPage.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
            ) {
                Text(text = "Go to Menu")
            }
        }
    }
    else {
        LazyColumn {

            items(items = dataManager.cart){
                CartItem(it = it, onDelete = { product ->
                    dataManager.cartRemove(product, user!!)
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
                    Text(
                        text = "Total Amount",
                        modifier = Modifier.width(150.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "$${totalSum(dataManager.cart).format(2)}",
                        modifier = Modifier.width(50.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

private fun totalSum(cart: List<ItemInCart>) : Double
{
    var sum = 0.0
    cart.forEach {
        sum += (it.product?.price!! * it.quantity!!)
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
        Text(
            text = it.product?.name!!,
            modifier = Modifier.width(150.dp)
        )

        Text(text = "x${it.quantity}")

        Text(
            text = "$${(it.product!!.price?.let { it1 -> it.quantity?.times(it1) })?.format(2)}",
            modifier = Modifier.width(50.dp)
        )
        Image(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier.clickable{
                onDelete(it.product!!)
            }
        )
    }
}