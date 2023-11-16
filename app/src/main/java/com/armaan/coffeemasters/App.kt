package com.armaan.coffeemasters

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.armaan.coffeemasters.pages.InfoPage
import com.armaan.coffeemasters.pages.MenuPage
import com.armaan.coffeemasters.pages.OffersPage
import com.armaan.coffeemasters.pages.OrderPage
import com.armaan.coffeemasters.ui.theme.Alternative1
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

    val navController = rememberNavController()

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
//            NavBar(
//                selectedRoute = selectedRoute.value,
//                onChange = {
//                newRoute ->
//                selectedRoute.value = newRoute
//            })
            NavigationBar (
                containerColor = MaterialTheme.colorScheme.primary
            ){
                Routes.pages.forEach{navItem ->
                    NavigationBarItem(
                        selected = selectedRoute.value == navItem.route,
                        label = {
                                Text(text = navItem.name)
                        },
                        onClick = {
                            selectedRoute.value = navItem.route
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = navItem.name
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Alternative1,
                            selectedTextColor = Alternative1,
                            indicatorColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.tertiary,
                            unselectedTextColor = MaterialTheme.colorScheme.tertiary
                        )
                    )
                }
            }
        }
    ) {paddingVal ->
//        when(selectedRoute.value)
//        {
//            Routes.MenuPage.route -> MenuPage(dataManager = dataManager, padding = it)
//            Routes.OffersPage.route -> OffersPage(padding = it)
//            Routes.InfoPage.route -> InfoPage(padding = it)
//            Routes.OrdersPage.route -> OrderPage(dataManager = dataManager, padding = it)
//        }
        NavHost(
            navController = navController,
            startDestination = Routes.MenuPage.route,
            modifier = Modifier.padding(paddingValues = paddingVal)
        ) {
            composable(Routes.MenuPage.route) {
                MenuPage(dataManager = dataManager)
            }
            composable(Routes.OffersPage.route) {
                OffersPage(padding = paddingVal)
            }
            composable(Routes.InfoPage.route) {
                InfoPage(padding = paddingVal)
            }
            composable(Routes.OrdersPage.route) {
                OrderPage(dataManager = dataManager, padding = paddingVal)
            }

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