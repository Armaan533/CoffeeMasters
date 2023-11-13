package com.armaan.coffeemasters.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.armaan.coffeemasters.DataManager
import com.armaan.coffeemasters.Product
import com.armaan.coffeemasters.R
import com.armaan.coffeemasters.ui.theme.Alternative1

fun Double.format(digits: Int) = "%.${digits}f".format(this)

//@Preview
@Composable
fun MenuPage(dataManager: DataManager, padding: PaddingValues) {
    LazyColumn(
        modifier = Modifier.padding(padding)
    ){
        items(5){
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
                ProductItem(product = Product(1, "Dummy", 1.25, ""), onAdd = {})
            }
        }
    }
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
        Image(
            painter = painterResource(id = R.drawable.black_coffee),
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
                onClick = {onAdd(product)},
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