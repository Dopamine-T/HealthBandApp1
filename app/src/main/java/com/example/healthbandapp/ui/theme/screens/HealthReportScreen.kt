package com.example.healthbandapp.ui.theme.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun HealthReportScreen(){

    println("进入健康报告页面")

    Column(

        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(20.dp)

    ){



        Text(

            text = "📋 今日健康报告",

            fontSize = 28.sp

        )



        Spacer(

            modifier = Modifier.height(20.dp)

        )



        ReportCard(

            title = "❤️ 心率状态",

            content =
                """
            当前状态：正常
            
            平均心率：80 BPM
            
            静息心率：59 BPM
            
            最大心率：140 BPM
            
            风险等级：低
            """

        )





        ReportCard(

            title = "🫁 血氧状态",

            content =
                """
            当前血氧：98%
            
            夜间最低：92%
            
            血氧下降事件：0次
            
            呼吸风险：低
            
            状态：良好
            """

        )





        ReportCard(

            title = "🌡 体温 & HRV",

            content =
                """
            当前体温：36.5℃
            
            HRV指数：65ms
            
            压力等级：低
            
            恢复状态：良好
            """

        )





        ReportCard(

            title = "😴 睡眠分析",

            content =
                """
            睡眠时长：7小时20分钟
            
            深睡比例：正常
            
            夜间心率稳定
            
            睡眠质量：一般
            """

        )






        ReportCard(

            title = "🚶 运动情况",

            content =
                """
            今日步数：6500步
            
            活动时间：45分钟
            
            消耗热量：320 kcal
            
            运动建议：
            
            增加20分钟有氧运动
            """

        )






        ReportCard(

            title = "💡 AI健康建议",

            content =
                """
            ✅ 今日健康状态良好
            
            建议：
            
            1. 保持规律睡眠
            
            2. 增加日常运动
            
            3. 继续监测心率变化
            
            4. 保持良好饮食习惯
            """

        )



    }


}







@Composable
fun ReportCard(

    title:String,

    content:String

){


    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        shape = MaterialTheme.shapes.large

    ){


        Column(

            modifier = Modifier
                .padding(18.dp)

        ){



            Text(

                text = title,

                fontSize = 20.sp

            )



            Spacer(

                modifier = Modifier.height(10.dp)

            )



            Text(

                text = content,

                fontSize = 15.sp

            )


        }


    }


}