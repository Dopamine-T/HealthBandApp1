package com.example.healthbandapp.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

import com.example.healthbandapp.ui.components.BottomBar
import com.example.healthbandapp.ui.theme.screens.*


@Composable
fun AppNavigation() {


    val navController = rememberNavController()



    Scaffold(

        bottomBar = {


            BottomBar(

                currentRoute =
                    navController
                        .currentBackStackEntryAsState()
                        .value
                        ?.destination
                        ?.route,


                onNavigate = { route ->


                    navController.navigate(route){


                        popUpTo(
                            navController.graph.startDestinationId
                        ){

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

            modifier =
                Modifier.padding(padding)

        ){



            // 首页

            composable("home"){

                HomeScreen(navController)

            }

            composable("device"){

                DeviceScreen()

            }




            // 健康主页

            composable("health"){

                HealthScreen(navController)

            }





            // 心率

            composable("heartRate"){

                HeartRateScreen(navController)

            }





            // 血氧

            composable("oxygen"){

                OxygenScreen(navController)

            }





            // 睡眠

            composable("sleep"){

                SleepScreen(navController)

            }





            // 体温

            composable("temperature"){

                TemperatureScreen(navController)

            }





            // HRV

            composable("hrv"){

                HrvScreen(navController)

            }





            // 血压

            composable("bloodPressure"){

                BloodPressureScreen(navController)

            }





            // 健康报告

            composable("report"){

                ReportScreen()

            }





            // 健康护理 ⭐

            composable("care"){


                HealthCareScreen(

                    onBack = {

                        navController.popBackStack()

                    }

                )


            }





            // 运动记录

            composable("sportRecord"){

                SportRecordScreen(navController)

            }





            // 医疗急救卡

            composable("emergency"){

                EmergencyCardScreen(navController)

            }





            // 编辑急救信息

            composable("emergencyEdit"){

                EmergencyEditScreen(navController)

            }





            // 周报

            composable("weekly"){

                SportWeeklyReportScreen(navController)

            }





            // 登录

            composable("login"){

                LoginScreen(navController)

            }





            // 个人中心

            composable("profile"){

                ProfileScreen(navController)

            }





            // AI

            composable("ai"){

                AiAssistantScreen()

            }





            // 预警

            composable("warning"){

                WarningScreen()

            }





            // 评分

            composable("score"){

                ScoreScreen()

            }





            // 关于

            composable("about"){

                AboutScreen(navController)

            }



        }


    }


}

@Composable
fun TemperatureScreen(x0: NavHostController) {
    TODO("Not yet implemented")
}