package com.armaan.coffeemasters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//  comp+tab -> snippet for a composable function
//  prev+tab -> snippet for a preview composable function

@Preview(showBackground = true, widthDp = 400)
@Composable
fun Offer() {
    val style = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ){
        Text(text = "My Title",
//            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(Color.Cyan)
                .padding(16.dp)
            )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Description",
            modifier = Modifier
                .background(Color.Cyan)
                .padding(16.dp)
//            style = MaterialTheme.typography.h6
            )
    }
}