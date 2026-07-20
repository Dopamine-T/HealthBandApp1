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
import androidx.navigation.NavHostController
import com.example.healthbandapp.ui.theme.screens.AboutScreen
import com.example.healthbandapp.ui.theme.screens.AiAssistantScreen
import com.example.healthbandapp.ui.theme.screens.BloodPressureScreen
import com.example.healthbandapp.ui.theme.screens.EmergencyCardScreen
import com.example.healthbandapp.ui.theme.screens.EmergencyEditScreen
import com.example.healthbandapp.ui.theme.screens.HealthCareScreen
import com.example.healthbandapp.ui.theme.screens.HeartRateScreen
import com.example.healthbandapp.ui.theme.screens.HrvScreen
import com.example.healthbandapp.ui.theme.screens.LoginScreen
import com.example.healthbandapp.ui.theme.screens.OxygenScreen
import com.example.healthbandapp.ui.theme.screens.ReportScreen
import com.example.healthbandapp.ui.theme.screens.ScoreScreen
import com.example.healthbandapp.ui.theme.screens.SleepScreen
import com.example.healthbandapp.ui.theme.screens.SportRecordScreen
import com.example.healthbandapp.ui.theme.screens.SportWeeklyReportScreen
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
            modifier = Modifier.padding(padding)
        ) {

            composable("home") {
                HomeScreen(navController = navController)
            }

            composable("health") {
                HealthScreen(
                    navController = navController,
                    onBack = { navController.popBackStack() }
                )
            }

            composable("heartRate") {
                HeartRateScreen(navController)
            }

            composable("oxygen") {
                OxygenScreen(navController)
            }

            composable("sleep") {
                SleepScreen(navController)
            }

            composable("hrv") {
                HrvScreen(navController)
            }

            composable("bloodPressure") {
                BloodPressureScreen(navController)
            }

            composable("device") {
                DeviceScreen()
            }

            composable("healthData"){
                HealthDataScreen(navController)
            }

            composable("userInfo"){
                UserInfoScreen()
            }

            composable("sportRecord"){
                SportRecordScreen(navController= navController)
            }

            composable("emergencyEdit"){
                EmergencyEditScreen(navController= navController)
            }

            composable("achievement"){
                AchievementScreen()
            }

            composable("emergency"){
                EmergencyCardScreen(navController = navController)
            }

            // ✅ 修复点：补全 navController 参数
            composable("care"){
                HealthCareScreen(
                    navController = navController,
                    onBack = { navController.popBackStack() }
                )
            }

            composable("weekly"){
                SportWeeklyReportScreen(navController = navController)
            }

            composable("login") {
                LoginScreen(navController = navController)
            }

            composable("about"){
                AboutScreen(navController = navController)
            }

            composable("profile") {
                ProfileScreen(navController = navController)
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

@Composable
fun WeeklyReportScreen() {
    TODO("Not yet implemented")
}

@Composable
fun AchievementScreen() {
    TODO("Not yet implemented")
}

@Composable
fun UserInfoScreen() {
    TODO("Not yet implemented")
}

@Composable
fun HealthDataScreen(x0: NavHostController) {
    TODO("Not yet implemented")
}