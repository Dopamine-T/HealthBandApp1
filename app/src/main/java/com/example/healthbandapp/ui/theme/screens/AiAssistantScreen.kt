package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun AiAssistantScreen() {


    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {


        Text(

            text = "🤖 AI健康助手",

            style = MaterialTheme.typography.headlineMedium

        )


        Text(
            "根据你的健康数据生成建议"
        )



        HealthAdviceCard(
            "❤️ 心率",
            "当前心率正常，保持良好状态"
        )


        HealthAdviceCard(
            "😴 睡眠",
            "建议保持每天7-8小时睡眠"
        )


        HealthAdviceCard(
            "🚶 运动",
            "今日运动量偏少，建议增加20分钟活动"
        )


    }

}



@Composable
fun HealthAdviceCard(
    title:String,
    text:String
){

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp)

    ){

        Column(
            modifier = Modifier.padding(20.dp)
        ){

            Text(
                title,
                style = MaterialTheme.typography.titleMedium
            )


            Spacer(
                Modifier.height(8.dp)
            )


            Text(text)

        }

    }

}