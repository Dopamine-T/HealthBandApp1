package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ReportScreen(){


    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)

    ){


        Text(
            "📋 今日健康报告",
            style = MaterialTheme.typography.headlineMedium
        )



        ReportItem(
            "⭐ 健康评分",
            "92 分"
        )


        ReportItem(
            "❤️ 平均心率",
            "78 BPM"
        )


        ReportItem(
            "🩸 血氧",
            "98%"
        )


        ReportItem(
            "🚶 步数",
            "6582 步"
        )


        ReportItem(
            "😴 睡眠",
            "7小时30分钟"
        )


    }

}



@Composable
fun ReportItem(
    title:String,
    value:String
){

    Card(

        modifier = Modifier.fillMaxWidth()

    ){

        Row(

            modifier = Modifier.padding(20.dp),

            horizontalArrangement = Arrangement.SpaceBetween

        ){

            Text(title)

            Text(value)

        }

    }

}