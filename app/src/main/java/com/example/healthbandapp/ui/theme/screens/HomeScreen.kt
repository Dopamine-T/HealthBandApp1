package com.example.healthbandapp.ui.theme.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.healthbandapp.ui.theme.*


@Composable
fun HomeScreen() {


    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {


        Text(

            text = "你好 👋",

            style = MaterialTheme.typography.headlineMedium

        )


        Text(

            text = "今天状态良好",

            color = Color.Gray

        )


        HealthCard(

            title = "❤️ 心率",

            value = "82",

            unit = "BPM",

            color = HeartRed

        )


        HealthCard(

            title = "🩸 血氧",

            value = "98",

            unit = "%",

            color = OxygenBlue

        )


        HealthCard(

            title = "🚶 步数",

            value = "6582",

            unit = "步",

            color = StepGreen

        )


        HealthCard(

            title = "😴 睡眠",

            value = "7.5",

            unit = "小时",

            color = SleepPurple

        )



        Button(

            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),


            shape = RoundedCornerShape(28.dp),


            onClick = {}

        ){

            Text("连接手环")

        }


    }


}



@Composable
fun HealthCard(

    title:String,

    value:String,

    unit:String,

    color: Color

){


    Card(

        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),


        shape = RoundedCornerShape(24.dp),


        colors = CardDefaults.cardColors(

            containerColor = Color.White

        )

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


            Row{


                Text(

                    value,

                    style = MaterialTheme.typography.displayMedium,

                    color = color

                )


                Spacer(
                    Modifier.width(8.dp)
                )


                Text(

                    unit,

                    modifier = Modifier.padding(top = 25.dp)

                )


            }


        }


    }


}