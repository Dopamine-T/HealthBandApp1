package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ScoreScreen(){


    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ){


        Text(

            "⭐ 健康评分",

            style = MaterialTheme.typography.headlineMedium

        )


        Spacer(
            Modifier.height(40.dp)
        )


        Text(

            "92",

            style = MaterialTheme.typography.displayLarge

        )


        Text(
            "健康状态良好"
        )


        Spacer(
            Modifier.height(30.dp)
        )


        Text("❤️ 心率  ⭐⭐⭐⭐⭐")

        Text("😴 睡眠  ⭐⭐⭐⭐")

        Text("🚶 运动  ⭐⭐⭐")


    }

}