package com.armaan.coffeemasters

//import androidx.compose.foundation.layout.Row
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.armaan.coffeemasters.ui.theme.CoffeeMastersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var dataManager = ViewModelProvider(this).get(DataManager::class.java)

        setContent {
            CoffeeMastersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(dataManager)
                }
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
////@Preview(showBackground = true)
//@Composable
//fun FirstComposable()
//{
//    var name = remember {mutableStateOf("")}
//    Column {
//        Text("Hello ${name.value}", modifier = Modifier
//            .padding(16.dp)
//            .background(Color.Cyan)                     // the order of modifiers is really important
//            .padding(16.dp)
//        )
//
//        TextField(value = name.value, onValueChange = {
//            name.value = it
//        })
//    }
//
////    Row {
////        Text("Row stuff", modifier = Modifier
////            .padding(16.dp)
////            .background(Color.Gray)
////            .padding(16.dp)
////        )
////
////        Text("Another row text",
////            modifier = Modifier
////                .padding(16.dp)
////        )
////    }
//
//}