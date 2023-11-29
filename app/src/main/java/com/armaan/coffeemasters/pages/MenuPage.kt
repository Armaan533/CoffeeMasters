package com.armaan.coffeemasters.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.armaan.coffeemasters.CustomSnackbar
import com.armaan.coffeemasters.DataManager
import com.armaan.coffeemasters.Product
import com.armaan.coffeemasters.Routes
import com.armaan.coffeemasters.sign_in.AuthUIClient
import kotlinx.coroutines.launch

fun Double.format(digits: Int) = "%.${digits}f".format(this)

//@Preview
@Composable
fun MenuPage(
    dataManager: DataManager,
    snackbarHostState: SnackbarHostState,
    authUIClient: AuthUIClient,
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ){
        items(dataManager.menu) {
            Divider()
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = it.name,
                    modifier = Modifier
                        .padding(10.dp, 20.dp, 10.dp, 10.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            it.products.forEach{ product ->
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp,
                        hoveredElevation = 100.dp
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(12.dp)
                ) {
                    ProductItem(product = product, onAdd = {
                        if(authUIClient.getSignedInUser() != null) {
                            dataManager.cartAdd(product)
                             scope.launch{
                                snackbarHostState.showSnackbar(
                                    message = "${product.name} added successfully to your cart",
                                    actionLabel = "Hide",
                                    duration = SnackbarDuration.Short
                                )
                        }
                        }
                        else {
                            navController.navigate(Routes.SignInPage.route)
                        }
                    },
                        )
                }
            }
        }
    }
    CustomSnackbar(snackbarHostState = snackbarHostState)
}

//@Preview
//@Composable
//fun ProductItemPreview() {
//    ProductItem(product = Product(1, "Dummy", 1.25, ""), onAdd = {})
//}

@Composable
fun ProductItem(product: Product, onAdd: (Product)->Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(12.dp)
            )
            .background(MaterialTheme.colorScheme.background)
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = "Image for ${product.name}",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp)),
            alignment = Alignment.TopCenter
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = product.name, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "$${product.price.format(2)}",
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Button(
                onClick = {
                    onAdd(product)
                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = "Add")
            }
        }
    }
}