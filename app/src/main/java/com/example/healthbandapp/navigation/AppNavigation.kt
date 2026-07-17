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
import com.example.healthbandapp.ui.theme.screens.BloodPressureScreen
import com.example.healthbandapp.ui.theme.screens.HeartRateScreen
import com.example.healthbandapp.ui.theme.screens.HrvScreen
import com.example.healthbandapp.ui.theme.screens.OxygenScreen
import com.example.healthbandapp.ui.theme.screens.ReportScreen
import com.example.healthbandapp.ui.theme.screens.ScoreScreen
import com.example.healthbandapp.ui.theme.screens.SleepScreen
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

                HeartRateScreen(navController)

            }

            composable("device") {
                DeviceScreen()
            }

            // 心率
            composable("heartRate") {
                HeartRateScreen(navController)
            }

// 血氧
            composable("oxygen") {
                OxygenScreen(navController)
            }

// 睡眠
            composable("sleep") {
                SleepScreen()
            }

// HRV
            composable("hrv") {
                HrvScreen()
            }

// 血压
            composable("bloodPressure") {
                BloodPressureScreen()
            }

// 健康报告
            composable("report") {
                ReportScreen()
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