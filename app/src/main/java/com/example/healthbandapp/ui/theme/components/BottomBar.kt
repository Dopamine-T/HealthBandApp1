package com.example.healthbandapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector


data class BottomItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)


@Composable
fun BottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {

    val items = listOf(
        BottomItem("首页", "home", Icons.Default.Home),
        BottomItem("健康", "health", Icons.Default.Favorite),
        BottomItem("设备", "device", Icons.Default.PhoneAndroid),
        BottomItem("我的", "profile", Icons.Default.Person)
    )


    NavigationBar {

        items.forEach { item ->

            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    onNavigate(item.route)
                },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.name
                    )
                },
                label = {
                    Text(item.name)
                }
            )

        }

    }
}