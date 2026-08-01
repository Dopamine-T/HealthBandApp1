package com.example.healthbandapp.ui.theme.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

import com.example.healthbandapp.viewmodel.HealthViewModel

import com.example.healthbandapp.ui.theme.card.BloodPressureCard
import com.example.healthbandapp.ui.theme.card.HeartCard
import com.example.healthbandapp.ui.theme.card.HrvCard
import com.example.healthbandapp.ui.theme.card.OxygenCard
import com.example.healthbandapp.ui.theme.card.SleepSummaryCard



@Composable
fun HealthScreen(

    navController: NavHostController,

    viewModel: HealthViewModel = viewModel()

) {


    // 健康数据显示
    val healthInfo by
    viewModel.healthInfo.collectAsState()


    // AI回复
    val aiResult by
    viewModel.aiResult.collectAsState()


    // AI状态
    val isAnalyzing by
    viewModel.isAnalyzing.collectAsState()



    Column(

        modifier = Modifier

            .fillMaxSize()

            .verticalScroll(
                rememberScrollState()
            )

            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )

            .navigationBarsPadding()

    ) {



        /*
        原健康数据卡片
        */


        HeartCard(navController)


        Spacer(
            modifier = Modifier.height(16.dp)
        )



        OxygenCard(navController)


        Spacer(
            modifier = Modifier.height(16.dp)
        )



        SleepSummaryCard(navController)


        Spacer(
            modifier = Modifier.height(16.dp)
        )



        HrvCard(navController)


        Spacer(
            modifier = Modifier.height(16.dp)
        )



        BloodPressureCard(navController)



        Spacer(
            modifier = Modifier.height(24.dp)
        )





        /*
        ==========================
        AI健康分析区域
        ==========================
        */


        Card(

            modifier = Modifier

                .fillMaxWidth(),


            shape = MaterialTheme.shapes.large

        ) {



            Column(

                modifier = Modifier

                    .padding(20.dp)

            ) {



                Text(

                    text = "🤖 AI健康分析",

                    style =
                        MaterialTheme.typography.titleLarge

                )



                Spacer(
                    modifier = Modifier.height(12.dp)
                )



                Text(

                    text = healthInfo,

                    style =
                        MaterialTheme.typography.bodyMedium

                )



                Spacer(
                    modifier = Modifier.height(20.dp)
                )





                Button(

                    onClick = {

                        viewModel.askAI()

                    },


                    enabled = !isAnalyzing,


                    modifier =
                        Modifier.fillMaxWidth()

                ) {


                    Text(

                        text =

                            if(isAnalyzing)

                                "分析中..."

                            else

                                "开始分析"

                    )


                }





                Spacer(
                    modifier = Modifier.height(20.dp)
                )





                if(aiResult.isNotEmpty()) {


                    Divider()


                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )



                    Text(

                        text = "AI建议",

                        style =
                            MaterialTheme.typography.titleMedium

                    )



                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )



                    Text(

                        text = aiResult,

                        style =
                            MaterialTheme.typography.bodyLarge

                    )


                }




            }


        }



        Spacer(
            modifier = Modifier.height(30.dp)
        )



    }


}