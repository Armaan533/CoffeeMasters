package com.armaan.coffeemasters.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.armaan.coffeemasters.R
import com.armaan.coffeemasters.ui.theme.Alternative2
import com.armaan.coffeemasters.ui.theme.TextLight

//  comp+tab -> snippet for a composable function
//  prev+tab -> snippet for a preview composable function


//@Preview(showBackground = true, widthDp = 400)
//@Composable
//private fun Offer_Preview() {
//    Offer(title = "This is a title", description = "This is a description")
//}


//@Preview(showBackground = true)
@Composable
fun OffersPage() {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Offer(title = "Early Coffee", description = "10% off. Offer valid from 6am to 9am.")
        Offer(title = "Welcome Gift", description = "25% off on your first order.")
        Offer(title = "Welcome Gift", description = "25% off on your first order.")
    }
}




//@Preview(showBackground = true, widthDp = 400)
@Composable
fun Offer(title: String, description: String) {
//    val style = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )

    Box (
        Modifier.padding(16.dp)
    ){

        Image(painter = painterResource(R.drawable.background_pattern),
            contentDescription = "Background pattern",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {


            Text(
                text = title,
                //            style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Alternative2)
                    .padding(16.dp),
                color = TextLight
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = description,
                modifier = Modifier
                    .background(Alternative2)
                    .padding(16.dp),
                color = TextLight
                //            style = MaterialTheme.typography.h6
            )
        }
    }
}