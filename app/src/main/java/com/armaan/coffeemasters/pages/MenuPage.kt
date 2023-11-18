package com.armaan.coffeemasters.pages

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.armaan.coffeemasters.CustomSnackbar
import com.armaan.coffeemasters.DataManager
import com.armaan.coffeemasters.Product
import com.armaan.coffeemasters.ui.theme.Alternative1
import com.armaan.coffeemasters.ui.theme.Primary
import kotlinx.coroutines.launch

fun Double.format(digits: Int) = "%.${digits}f".format(this)

//@Preview
@Composable
fun MenuPage(dataManager: DataManager, snackbarHostState: SnackbarHostState) {
    val scope = rememberCoroutineScope()

    LazyColumn(){
        items(dataManager.menu) {
            Divider()
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = it.name,
                    color = Primary,
                    modifier = Modifier
                        .padding(10.dp, 20.dp, 10.dp, 10.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
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
                        .background(Color.White)
                        .padding(12.dp)
                ) {
                    ProductItem(product = product, onAdd = {
                        dataManager.cartAdd(product)
                         scope.launch{
                            snackbarHostState.showSnackbar(
                                message = "${product.name} added successfully to your cart",
                                actionLabel = "Hide",
                                duration = SnackbarDuration.Short
                            )
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
            .background(Color.White)
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = "Image for ${product.name}",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(text = product.name, fontWeight = FontWeight.Bold)
                Text(text = "$${product.price.format(2)} ea")
            }
            Button(
                onClick = {
                    onAdd(product)
                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Alternative1,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Add")
            }
        }
    }
}