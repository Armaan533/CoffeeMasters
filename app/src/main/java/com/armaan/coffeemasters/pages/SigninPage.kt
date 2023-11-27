package com.armaan.coffeemasters.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.armaan.coffeemasters.R
import com.armaan.coffeemasters.sign_in.SignInState
import com.armaan.coffeemasters.ui.theme.Alternative1
import com.armaan.coffeemasters.ui.theme.Alternative2

@Composable
fun SignInPage(
    state: SignInState,
    onSignInClick: () -> Unit
//    onGuestClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let {error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Alternative2)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cat),
            contentDescription = "cute_cat",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onSignInClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Alternative1,
                contentColor = Color.White
            )
        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.google_logo),
//                contentDescription = "google_logo"
//            )
            Text(
                text = "Sign In"
                // TODO -> Update the font
            )
        }
    }


}