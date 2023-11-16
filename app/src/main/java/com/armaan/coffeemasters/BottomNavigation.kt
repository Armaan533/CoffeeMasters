package com.armaan.coffeemasters

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class NavPage(var name: String, var icon: ImageVector, var route: String)



object Routes {
    val MenuPage = NavPage("Menu", Icons.Outlined.Menu, "menu")
    val OffersPage = NavPage("Offers", Icons.Outlined.Star, "star")
    val OrdersPage = NavPage("My orders", Icons.Outlined.ShoppingCart, "shoppingCart")
    val InfoPage = NavPage("Info", Icons.Outlined.Info, "info")

    val pages = listOf(MenuPage, OffersPage, OrdersPage, InfoPage)
}

//@Preview
//@Composable
//fun NavBarItem_Prev() {
//    NavBarItem(page = Routes.MenuPage, modifier = Modifier.padding(8.dp))
//}


//@Composable
//fun NavBar(selectedRoute: String,
//           onChange: (String)->Unit
//           ) {
//    Row(
//        horizontalArrangement = Arrangement.SpaceBetween,
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Primary)
//            .padding(16.dp)
//    ) {
//        for(page in Routes.pages){
//            NavBarItem(
//                page = page,
//                selected = selectedRoute == page.route,
//                modifier = Modifier.clickable {
//                    onChange(page.route)
//                }
//                )
//        }
//    }
//}

//@Composable
//fun NavBarItem(page: NavPage, selected: Boolean = false, modifier: Modifier = Modifier) {
////    Column(horizontalAlignment = Alignment.CenterHorizontally,
////        modifier = modifier.padding(horizontal = 12.dp)) {
////        Image(imageVector = page.icon, contentDescription = page.name, colorFilter = ColorFilter.tint(
////            if (selected) Alternative1 else Ternary
////        ),
////            modifier = Modifier
////                .padding(bottom = 8.dp)
////                .size(24.dp)
////        )
////        Text(text = page.name,
////            fontSize = 12.sp,
////            color = if (selected) Alternative1 else Ternary
////            )
////    }
//
//}