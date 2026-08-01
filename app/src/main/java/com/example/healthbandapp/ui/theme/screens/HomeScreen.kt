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
fun HomeScreen(navController: NavController) {


    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            // ⭐增加屏幕上下滑动
            .verticalScroll(rememberScrollState()),

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



        DashboardCard(

            title = "⭐ 健康评分",

            content = "92 分\n状态良好",

            color = Color(0xFF34C759),

            onClick = {
                navController.navigate("score")
            }

        )



        DashboardCard(

            title = "🤖 AI健康助手",

            content = "根据你的身体数据\n生成今日健康建议",

            color = Color(0xFF007AFF),

            onClick = {
                navController.navigate("ai")
            }

        )



        DashboardCard(

            title = "⚠️ 异常提醒",

            content = "暂无异常数据",

            color = Color(0xFFFF9500),

            onClick = {
                navController.navigate("warning")
            }

        )



        DashboardCard(

            title = "📋 今日报告",

            content = "查看今日心率、睡眠、运动分析",

            color = Color(0xFF5856D6),

            onClick = {
                navController.navigate("report")
            }

        )



        Text(

            text = "快捷操作",

            style = MaterialTheme.typography.titleMedium

        )



        Row(

            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.spacedBy(12.dp)

        ){


            SmallActionButton(

                text = "📱连接设备"

            )


            SmallActionButton(

                text = "❤️测心率"

            )


        }


        // 测试滑动效果，可以后续删除
        Spacer(
            modifier = Modifier.height(300.dp)
        )


    }


}




@Composable
fun DashboardCard(
    title: String,
    content: String,
    color: Color,
    onClick: () -> Unit
) {


    Card(

        modifier = Modifier

            .fillMaxWidth()

            .clickable {

                onClick()

            }

    ) {


        Column(

            modifier = Modifier.padding(20.dp)

        ) {


            Text(

                text = title,

                color = color

            )


            Text(

                text = content

            )


        }

    }

}




@Composable
fun SmallActionButton(

    text:String,

    modifier: Modifier = Modifier

){


    Button(

        onClick = {},

        modifier = modifier,

        shape = RoundedCornerShape(20.dp)

    ){

        Text(text)

    }

}