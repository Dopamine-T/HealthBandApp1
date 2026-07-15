package com.example.healthbandapp.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.healthbandapp.ui.components.BottomBar
import com.example.healthbandapp.ui.theme.screens.DeviceScreen
import com.example.healthbandapp.ui.theme.screens.HealthScreen
import com.example.healthbandapp.ui.theme.screens.HomeScreen
import com.example.healthbandapp.ui.theme.screens.ProfileScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.example.healthbandapp.ui.theme.screens.AiAssistantScreen
import com.example.healthbandapp.ui.theme.screens.HeartRateScreen
import com.example.healthbandapp.ui.theme.screens.ReportScreen
import com.example.healthbandapp.ui.theme.screens.ScoreScreen
import com.example.healthbandapp.ui.theme.screens.WarningScreen


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

                    navController.navigate(route) {

                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }

                        launchSingleTop = true

                        restoreState = true
                    }

                }
            )

        }

    ){ padding ->


        NavHost(

            navController = navController,

            startDestination = "home",

            modifier = Modifier
                .padding(padding)

        ) {


            composable("home") {
                HomeScreen(
                    navController = navController
                )
            }


            composable("health") {
                HealthScreen(
                    navController = navController
                )
            }

            composable("heartRate") {

                HeartRateScreen()

            }

            composable("device") {
                DeviceScreen()
            }


            composable("profile") {
                ProfileScreen()
            }

            composable("ai"){
                AiAssistantScreen()
            }

            composable("report"){
                ReportScreen()
            }

            composable("warning"){
                WarningScreen()
            }

            composable("score"){
                ScoreScreen()
            }

        }

    }
}