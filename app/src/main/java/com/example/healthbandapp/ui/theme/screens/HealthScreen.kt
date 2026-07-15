package com.example.healthbandapp.ui.theme.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Canvas
import androidx.compose.animation.core.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random
import androidx.navigation.NavHostController



@Composable
fun HealthScreen(
    navController: NavHostController
){


    // 手环心率数据
    var heartRate by remember {
        mutableStateOf(75)
    }

    // ===== 心率分析数据 =====


    var restingHeartRate by remember {
        mutableStateOf(59)
    }


    var maxHeartRate by remember {
        mutableStateOf(140)
    }


    var minHeartRate by remember {
        mutableStateOf(32)
    }


    var averageHeartRate by remember {
        mutableStateOf(80)
    }


//异常阈值

    var highLimit by remember {
        mutableStateOf(120)
    }


    var lowLimit by remember {
        mutableStateOf(50)
    }


//24小时数据

    val heartHistory = remember {

        mutableStateListOf<Int>().apply {

            repeat(48){

                add(
                    Random.nextInt(55,120)
                )

            }

        }

    }


    // 手环电量
    var battery by remember {
        mutableStateOf(85)
    }


    // 蓝牙状态
    var connected by remember {
        mutableStateOf(true)
    }



    //========== 新增健康指标 ==========


    //血氧
    var oxygen by remember {
        mutableStateOf(98)
    }


    //体表温度
    var temperature by remember {
        mutableStateOf(36.5)
    }


    //呼吸率
    var breathingRate by remember {
        mutableStateOf(16)
    }


    //HRV指数
    var hrv by remember {
        mutableStateOf(65)
    }


    //压力等级
    var stress by remember {
        mutableStateOf("低")
    }


    //血压估算
    var bloodPressure by remember {
        mutableStateOf("118/76")
    }

    //心率详细展开状态
    var showHeartDetail by remember {
        mutableStateOf(false)
    }


//心率异常提醒
    var heartWarning by remember {
        mutableStateOf(true)
    }


//异常阈值
    var highHeartLimit by remember {
        mutableStateOf(120)
    }


    var lowHeartLimit by remember {
        mutableStateOf(50)
    }




    Column(

        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ){
//1，身体指标
        Spacer(
            Modifier.height(15.dp)
        )


        Card(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable {

                        navController.navigate("heart")

                    },

            shape = RoundedCornerShape(18.dp)

        ){

            Row(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(18.dp),

                verticalAlignment = Alignment.CenterVertically

            ){

                Text(
                    "❤️",
                    fontSize = 22.sp
                )


                Spacer(
                    Modifier.width(10.dp)
                )


                Text(
                    "心率",
                    fontSize = 18.sp,
                    modifier =
                        Modifier.weight(1f)
                )


                Text(
                    "查看 >",
                    color = Color.Gray
                )


            }


        }

            Column(

                modifier =
                    Modifier.padding(20.dp)

            ){

                Text(

                    "实时身体指标",

                    fontSize = 20.sp

                )


                Spacer(
                    Modifier.height(10.dp)
                )


                Text(
                    "🫁 血氧饱和度 : $oxygen%"
                )


                Text(
                    "🌡 体表温度 : %.1f ℃"
                        .format(temperature)
                )


                Text(
                    "呼吸率 : $breathingRate 次/分"
                )


            }

        }


//2 心率分析

        Spacer(
            Modifier.height(15.dp)
        )


        Card(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable {

                        navController.navigate("heartRate")

                    }

        ){

            Column(

                modifier =
                    Modifier.padding(20.dp)

            ){

                Text(

                    "❤️ 心率分析",

                    fontSize=20.sp

                )


                Spacer(
                    Modifier.height(10.dp)
                )


                Text(
                    "静息心率 : 65 BPM"
                )


                Text(
                    "运动峰值心率 : 125 BPM"
                )


                Text(
                    "夜间心率范围 : 55~70 BPM"
                )


                Text(
                    "异常提醒 : 已开启"
                )


            }

        }

        //==============================
// 心率详细分析展开区域
//==============================


        if(showHeartDetail){



            Spacer(
                Modifier.height(15.dp)
            )



//实时心率仪表盘


            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(20.dp)

            ){

                Column(

                    modifier =
                        Modifier
                            .padding(20.dp),

                    horizontalAlignment =
                        Alignment.CenterHorizontally

                ){


                    Text(

                        "实时心率监测",

                        fontSize=20.sp

                    )



                    Spacer(
                        Modifier.height(20.dp)
                    )



                    Box(

                        modifier =
                            Modifier
                                .size(200.dp)
                                .clip(CircleShape)
                                .background(
                                    Color(0xffffdddd)
                                ),

                        contentAlignment =
                            Alignment.Center

                    ){



                        Column(

                            horizontalAlignment =
                                Alignment.CenterHorizontally

                        ){


                            Icon(

                                imageVector =
                                    Icons.Default.Favorite,

                                contentDescription="",

                                tint =
                                    Color.Red,

                                modifier =
                                    Modifier.size(40.dp)

                            )



                            Text(

                                "$heartRate",

                                fontSize=55.sp,

                                color=Color.Red

                            )


                            Text(
                                "BPM"
                            )



                        }



                    }


                    Spacer(
                        Modifier.height(15.dp)
                    )



                    Text(

                        if(
                            heartRate>highHeartLimit
                            ||
                            heartRate<lowHeartLimit
                        )

                            "⚠ 心率异常"

                        else

                            "状态正常"

                    )


                }

            }





            Spacer(
                Modifier.height(15.dp)
            )



//24小时心率曲线


            Card(

                modifier =
                    Modifier.fillMaxWidth()

            ){


                Column(

                    modifier =
                        Modifier.padding(20.dp)

                ){


                    Text(

                        "24小时连续心率",

                        fontSize=20.sp

                    )


                    Spacer(
                        Modifier.height(10.dp)
                    )



                    Text(

                        """
120 ┤       *
100 ┤    *     *
 80 ┤************
 60 ┤

     0  6  12  18  24

""",

                        fontSize=15.sp

                    )


                }


            }






            Spacer(
                Modifier.height(15.dp)
            )


//关键指标


            Card(

                modifier =
                    Modifier.fillMaxWidth()

            ){


                Column(

                    modifier =
                        Modifier.padding(20.dp)

                ){


                    Text(

                        "心率关键指标",

                        fontSize=20.sp

                    )



                    Text(
                        "静息心率：65 BPM"
                    )


                    Text(
                        "运动峰值心率：125 BPM"
                    )


                    Text(
                        "夜间心率波动：55~70 BPM"
                    )



                }


            }





            Spacer(
                Modifier.height(15.dp)
            )





//异常提醒


            Card(

                modifier =
                    Modifier.fillMaxWidth()

            ){


                Column(

                    modifier =
                        Modifier.padding(20.dp)

                ){



                    Text(

                        "心率异常提醒",

                        fontSize=20.sp

                    )



                    Text(
                        "高心率阈值：$highHeartLimit BPM"
                    )


                    Text(
                        "低心率阈值：$lowHeartLimit BPM"
                    )



                    Row(

                        verticalAlignment =
                            Alignment.CenterVertically

                    ){


                        Text(
                            "开启提醒"
                        )


                        Switch(

                            checked =
                                heartWarning,

                            onCheckedChange = {

                                heartWarning = it

                            }

                        )


                    }



                }


            }





            Spacer(
                Modifier.height(15.dp)
            )





//活动状态分析


            Card(

                modifier =
                    Modifier.fillMaxWidth()

            ){


                Column(

                    modifier =
                        Modifier.padding(20.dp)

                ){


                    Text(

                        "活动状态心率分布",

                        fontSize=20.sp

                    )



                    Text(
                        "🚶 步行：85 BPM"
                    )



                    Text(
                        "😴 睡眠：62 BPM"
                    )



                    Text(
                        "😰 压力状态：95 BPM"
                    )



                }


            }



        }

//3 血氧与呼吸
        Spacer(
            Modifier.height(15.dp)
        )


        Card(

            modifier =
                Modifier.fillMaxWidth()

        ){

            Column(

                modifier =
                    Modifier.padding(20.dp)

            ){

                Text(

                    "🫁 血氧与呼吸分析",

                    fontSize=20.sp

                )



                Text(
                    "夜间最低血氧 : 92%"
                )


                Text(
                    "血氧下降事件 : 0次"
                )


                Text(
                    "呼吸暂停次数 : 1次"
                )


                Text(
                    "睡眠呼吸风险 : 低"
                )


            }

        }


//4 体温HRV
        Spacer(
            Modifier.height(15.dp)
        )


        Card(

            modifier =
                Modifier.fillMaxWidth()

        ){

            Column(

                modifier =
                    Modifier.padding(20.dp)

            ){


                Text(

                    "🌡 体温 & HRV",

                    fontSize=20.sp

                )


                Text(

                    "HRV指数 : $hrv ms"

                )


                Text(

                    "压力等级 : $stress"

                )


                Text(

                    "建议 : 进行5分钟呼吸训练"

                )


            }

        }


//5 血压估算
        Spacer(
            Modifier.height(15.dp)
        )


        Card(

            modifier =
                Modifier.fillMaxWidth()

        ){

            Column(

                modifier =
                    Modifier.padding(20.dp)

            ){


                Text(

                    "🩸 血压估算",

                    fontSize=20.sp

                )


                Text(

                    "$bloodPressure mmHg",

                    fontSize=25.sp

                )


                Text(

                    "⚠️ 基于PPG算法估算，仅供参考，非医疗诊断"

                )


            }

        }
//6 健康报告
        Spacer(
            Modifier.height(15.dp)
        )


        Card(

            modifier =
                Modifier.fillMaxWidth()

        ){

            Column(

                modifier =
                    Modifier.padding(20.dp)

            ){


                Text(

                    "📋 今日健康报告",

                    fontSize=20.sp

                )


                Spacer(
                    Modifier.height(10.dp)
                )


                Text(
                    "❤️ 心率状态：正常"
                )


                Text(
                    "🫁 血氧状态：良好"
                )


                Text(
                    "😴 睡眠质量：一般"
                )


                Text(
                    "建议：保持运动，睡前进行呼吸训练"
                )


            }

        }

        Spacer(
            Modifier.height(80.dp)
        )

        
    }


