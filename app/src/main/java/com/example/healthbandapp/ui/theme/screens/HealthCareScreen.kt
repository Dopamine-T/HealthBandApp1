package com.example.healthbandapp.ui.theme.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun HealthCareScreen(

    onBack: () -> Unit,
    navController: NavHostController

){


    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)

    ){



        Spacer(

            Modifier.height(20.dp)

        )



        Text(

            text="💚 健康关怀",

            fontSize=26.sp

        )



        Spacer(

            Modifier.height(25.dp)

        )



        Card(

            modifier = Modifier
                .fillMaxWidth()

        ){


            Column(

                modifier=Modifier
                    .padding(20.dp)

            ){


                Text(

                    "今日健康评分：92分",

                    fontSize=18.sp

                )


                Spacer(
                    Modifier.height(15.dp)
                )



                Text(

                    "❤️ 心率状态：正常",

                    fontSize=18.sp

                )


                Spacer(
                    Modifier.height(10.dp)
                )



                Text(

                    "😴 睡眠状态：良好",

                    fontSize=18.sp

                )


                Spacer(
                    Modifier.height(10.dp)
                )



                Text(

                    "🚶 运动状态：达标",

                    fontSize=18.sp

                )


            }


        }



        Spacer(

            Modifier.height(20.dp)

        )



        Text(

            text="健康建议",

            fontSize=22.sp

        )



        Spacer(

            Modifier.height(10.dp)

        )



        Card(

            modifier=Modifier.fillMaxWidth()

        ){


            Column(

                modifier=Modifier.padding(20.dp)

            ){


                Text("✅ 今日饮水建议：1500ml")

                Spacer(
                    Modifier.height(8.dp)
                )

                Text("✅ 建议保持30分钟运动")

                Spacer(
                    Modifier.height(8.dp)
                )

                Text("✅ 保持7小时以上睡眠")

                Spacer(
                    Modifier.height(8.dp)
                )

                Text("✅ 注意压力调节")


            }


        }


    }


}