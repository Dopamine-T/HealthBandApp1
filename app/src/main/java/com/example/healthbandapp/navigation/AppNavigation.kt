package com.example.healthbandapp.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.healthbandapp.ui.components.BottomBar
import com.example.healthbandapp.ui.theme.screens.DeviceScreen
import com.example.healthbandapp.ui.theme.screens.HealthScreen
import com.example.healthbandapp.ui.theme.screens.HomeScreen
import com.example.healthbandapp.ui.theme.screens.ProfileScreen


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    Scaffold(

        bottomBar = {

            BottomBar(
                currentRoute =
                    navController.currentBackStackEntryAsState()
                        .value
                        ?.destination
                        ?.route,

                onNavigate = { route ->

                    navController.navigate(route)

                }
            )

        }

    ) { padding ->


        NavHost(
            navController = navController,
            startDestination = "home"
        ) {


            composable("home") {
                HomeScreen()
            }


            composable("health") {
                HealthScreen()
            }


            composable("device") {
                DeviceScreen()
            }


            composable("profile") {
                ProfileScreen()
            }

        }

    }
}