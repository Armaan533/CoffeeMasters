package com.armaan.coffeemasters

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackbar(
    snackbarHostState: SnackbarHostState
) {
    SnackbarHost (
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
        ,
        hostState = snackbarHostState
//        snackbar = {
//            Snackbar(
//                modifier = Modifier.constrainAs(snack){
//                                                      bottom.linkTo(parent.bottom)
//                },
//                action = {
//                    TextButton(
//                        onClick = {
//                            snackbarHostState.currentSnackbarData?.dismiss()
//                        }
//                    ) {
//                        Text(
//                            text = snackbarHostState.currentSnackbarData?.visuals?.actionLabel ?: "",
//                            style = TextStyle(color = Color.White)
//                        )
//                    }
//                }
//            ) {
//                Text(snackbarHostState.currentSnackbarData?.visuals?.message?: "")
//            }
//        }
    )
}