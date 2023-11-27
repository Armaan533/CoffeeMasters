package com.armaan.coffeemasters.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.armaan.coffeemasters.sign_in.UserData
import com.armaan.coffeemasters.ui.theme.Alternative1
import com.armaan.coffeemasters.ui.theme.Alternative2

@Composable
fun ProfilePage(
    userData: UserData?,
    onSignOut: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .background(Alternative2),
        shape = RoundedCornerShape(12.dp)
    ) {
        if(userData?.pfpUrl != null) {
            AsyncImage(
                model = userData.pfpUrl,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (userData?.username != null) {
            Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                softWrap = true,
                overflow = TextOverflow.Clip
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(
            onClick = onSignOut,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Alternative1,
                contentColor = Color.White
            )
        ) {
            Text(text = "Sign Out")
        }
    }
}