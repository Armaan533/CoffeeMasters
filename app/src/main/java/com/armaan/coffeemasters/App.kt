package com.armaan.coffeemasters

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.armaan.coffeemasters.pages.InfoPage
import com.armaan.coffeemasters.pages.MenuPage
import com.armaan.coffeemasters.pages.OffersPage
import com.armaan.coffeemasters.pages.OrderPage
import com.armaan.coffeemasters.ui.theme.Primary


//@Preview
//@Composable
//fun App_Preview() {
//    CoffeeMastersTheme {
//        App()
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(dataManager: DataManager) {
    val selectedRoute = remember {
        mutableStateOf("menu")
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { AppTitle() },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Primary
                )
                )
        },
        bottomBar = {
            NavBar(
                selectedRoute = selectedRoute.value,
                onChange = {
                newRoute ->
                selectedRoute.value = newRoute
            })
        }
    ) {
        when(selectedRoute.value)
        {
            Routes.MenuPage.route -> MenuPage(dataManager = dataManager, padding = it)
            Routes.OffersPage.route -> OffersPage(padding = it)
            Routes.InfoPage.route -> InfoPage(padding = it)
            Routes.OrdersPage.route -> OrderPage(dataManager = dataManager, padding = it)
        }
    }
}


@Composable
fun AppTitle() {
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ){
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "CoffeeMasters Logo")
    }
}