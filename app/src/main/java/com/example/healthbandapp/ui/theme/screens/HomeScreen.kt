package com.example.healthbandapp.ui.theme.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController



@Composable
fun HomeScreen(
    navController: NavController
) {


    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(
                rememberScrollState()
            ),

        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {


        Text(

            text = "你好 👋",

            style = MaterialTheme.typography.headlineMedium

        )


        Text(

            text = "今日健康概览",

            color = Color.Gray

        )



        // ==========================
        // 健康评分
        // ==========================

        DashboardCard(

            title = "⭐ 健康总体评分",

            content = """
                92 分
                
                状态良好
            """.trimIndent(),

            color = Color(0xFF34C759),

            onClick = {

                navController.navigate("score")

            }

        )




        // ==========================
        // 健康数据（暂时展示）
        // 不进行跳转
        // ==========================


        DashboardCard(

            title = "📊 今日健康数据",

            content = """
                
                ❤️ 心率
                75 bpm
                
         
                🏃 运动
                6500步
                
                📈 HRV
                65ms
                
            """.trimIndent(),

            color = Color(0xFF007AFF),

            onClick = {

                // 暂无跳转

            }

        )





        // ==========================
        // 异常提醒
        // ==========================


        DashboardCard(

            title = "⚠️ 异常提醒",

            content = """
                
                当前未发现异常
                
                身体状态稳定
                
            """.trimIndent(),

            color = Color(0xFFFF9500),

            onClick = {

                navController.navigate("warning")

            }

        )


    }

}






@Composable
fun DashboardCard(

    title:String,

    content:String,

    color:Color,

    onClick:()->Unit

){


    Card(

        modifier = Modifier

            .fillMaxWidth()

            .clickable {

                onClick()

            }

    ){


        Column(

            modifier = Modifier.padding(20.dp)

        ){


            Text(

                text = title,

                color = color,

                style = MaterialTheme.typography.titleMedium

            )


            Spacer(

                modifier = Modifier.height(8.dp)

            )


            Text(

                text = content

            )


        }


    }


}