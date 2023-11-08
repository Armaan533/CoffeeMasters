package com.armaan.coffeemasters

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.armaan.coffeemasters.ui.theme.CoffeeMastersTheme
import com.armaan.coffeemasters.ui.theme.Primary


@Preview
@Composable
fun App_Preview() {
    CoffeeMastersTheme {
        App()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App() {
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
            Text(text = "I am a bottom bar")
        }
    ) {
        OffersPage(it)
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