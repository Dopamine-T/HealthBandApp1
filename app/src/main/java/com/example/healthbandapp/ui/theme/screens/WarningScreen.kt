package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun WarningScreen(){


    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)

    ){


        Text(

            "⚠️ 异常提醒",

            style = MaterialTheme.typography.headlineMedium

        )



        Card(

            modifier = Modifier.fillMaxWidth()

        ){


            Text(

                text = "暂无异常数据",

                modifier = Modifier.padding(20.dp)

            )


        }



        Text(
            "历史记录"
        )


        Text(
            "暂无历史异常"
        )


    }

}