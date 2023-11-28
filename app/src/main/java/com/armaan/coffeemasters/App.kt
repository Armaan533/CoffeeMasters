package com.armaan.coffeemasters

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.armaan.coffeemasters.pages.InfoPage
import com.armaan.coffeemasters.pages.MenuPage
import com.armaan.coffeemasters.pages.OffersPage
import com.armaan.coffeemasters.pages.OrderPage
import com.armaan.coffeemasters.pages.ProfilePage
import com.armaan.coffeemasters.pages.SignInPage
import com.armaan.coffeemasters.sign_in.AuthUIClient
import com.armaan.coffeemasters.sign_in.SignInViewModel
import com.armaan.coffeemasters.ui.theme.Alternative1
import com.armaan.coffeemasters.ui.theme.Primary
import kotlinx.coroutines.launch


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
fun App(
    dataManager: DataManager,
    authUIClient: AuthUIClient,
    lifecycleScope: LifecycleCoroutineScope,
    applicationContext: Context
) {
    val selectedRoute = remember {
        mutableStateOf("menu")
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

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
            if (currentBackStackEntry?.destination?.route != Routes.SignInPage.route) {
                NavigationBar (
                    containerColor = MaterialTheme.colorScheme.primary
                ){
                    Routes.pages.forEach{navItem ->
                        NavigationBarItem(
                            selected = currentBackStackEntry?.destination?.route == navItem.route,
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
                MenuPage(
                    dataManager = dataManager,
                    snackbarHostState = snackbarHostState,
                    authUIClient = authUIClient,
                    navController = navController
                )
            }
            composable(Routes.OffersPage.route) {
                OffersPage()
            }
            composable(Routes.InfoPage.route) {
                InfoPage()
            }
            composable(Routes.OrdersPage.route) {
                OrderPage(
                    dataManager = dataManager,
                    navController = navController
                )
            }
            composable(Routes.SignInPage.route) {
                val viewModel = viewModel<SignInViewModel>()
                val state = viewModel.state.collectAsStateWithLifecycle()
                
                LaunchedEffect(key1 = Unit) {
                    if (authUIClient.getSignedInUser() != null) {
                        navController.navigate(Routes.ProfilePage.route)
                    }
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if(result.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                                val signInResult = authUIClient.googleSignInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                viewModel.onSignInResult(signInResult)
                            }
                        }
                    }
                )

                LaunchedEffect(key1 = state.value.isSignInSuccessful) {
                    if (state.value.isSignInSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Sign-in successful",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.navigate(navController.previousBackStackEntry?.destination?.route!!)
                        viewModel.resetState()
                    }
                }

                SignInPage(
                    state = state.value,
                    onSignInClick = {
                        lifecycleScope.launch {
                            val signInIntentSender = authUIClient.googleSignIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    },
                    onSkipClick = {
                        navController.navigate(Routes.MenuPage.route)
                    }
                )

            }

            composable(Routes.ProfilePage.route) {
                LaunchedEffect(key1 = Unit) {
                    if (authUIClient.getSignedInUser() == null) {
                        navController.navigate(Routes.SignInPage.route)
                    }
                }

                ProfilePage(
                    userData = authUIClient.getSignedInUser(),
                    onSignOut = {
                        lifecycleScope.launch {
                            authUIClient.googleSignOut()
                            Toast.makeText(
                                applicationContext,
                                "Signed Out",
                                Toast.LENGTH_LONG
                            ).show()

                            navController.popBackStack()
                        }
                    }
                )
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