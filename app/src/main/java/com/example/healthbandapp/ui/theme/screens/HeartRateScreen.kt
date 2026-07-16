package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random



@Composable
fun HeartRateScreen(){


    //实时心率

    var heartRate by remember {

        mutableStateOf(82)

    }



    //异常提醒

    var warning by remember {

        mutableStateOf(true)

    }


    var highLimit by remember {

        mutableStateOf(120)

    }


    var lowLimit by remember {

        mutableStateOf(50)

    }



    //24小时数据

    val heartData =
        remember {


            List(48){

                Random.nextInt(
                    55,
                    130
                )

            }


        }




    Column(

        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)

    ){



        Text(

            "❤️ 心率监测",

            fontSize = 28.sp

        )



        Spacer(
            Modifier.height(20.dp)
        )



        //================
        //实时仪表盘
        //================


        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(25.dp)

        ){


            Column(

                modifier =
                    Modifier.padding(20.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally

            ){


                Text(

                    "实时心率",

                    fontSize=20.sp

                )



                Spacer(
                    Modifier.height(20.dp)
                )




                Box(

                    modifier =
                        Modifier
                            .size(220.dp)
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

                            Icons.Default.Favorite,

                            contentDescription="",

                            tint=Color.Red,

                            modifier =
                                Modifier.size(45.dp)

                        )



                        Text(

                            "$heartRate",

                            fontSize=55.sp,

                            color=Color.Red

                        )



                        Text(
                            "次/分钟"
                        )

                    }


                }


            }


        }




        Spacer(
            Modifier.height(20.dp)
        )




        //================
        //24小时曲线
        //================


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
                    Modifier.height(20.dp)
                )


                HeartChart(
                    heartData
                )

            }

        }



        Spacer(
            Modifier.height(20.dp)
        )





        //================
        //关键指标
        //================



        Card(

            modifier =
                Modifier.fillMaxWidth()

        ){


            Column(

                modifier =
                    Modifier.padding(20.dp)

            ){


                Text(

                    "今日概览",

                    fontSize=20.sp

                )


                Text(
                    "静息心率   59 次/分"
                )


                Text(
                    "运动峰值心率   140 次/分"
                )


                Text(
                    "平均心率   80 次/分"
                )


                Text(
                    "夜间心率波动   55~70 次/分"
                )


            }


        }




        Spacer(
            Modifier.height(20.dp)
        )





        //================
        //异常提醒
        //================



        Card(

            modifier =
                Modifier.fillMaxWidth()

        ){


            Column(

                modifier =
                    Modifier.padding(20.dp)

            ){


                Text(

                    "⚠ 心率异常提醒",

                    fontSize=20.sp

                )


                Text(

                    "高心率阈值：$highLimit"

                )


                Text(

                    "低心率阈值：$lowLimit"

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
                            warning,

                        onCheckedChange = {

                            warning=it

                        }

                    )


                }



            }


        }




        Spacer(
            Modifier.height(20.dp)
        )





        //================
        //活动状态
        //================



        Card(

            modifier =
                Modifier.fillMaxWidth()

        ){


            Column(

                modifier =
                    Modifier.padding(20.dp)

            ){


                Text(

                    "活动状态分布",

                    fontSize=20.sp

                )



                Text(
                    "🚶 步行   85 次/分"
                )


                Text(
                    "🏃 运动   135 次/分"
                )


                Text(
                    "😴 睡眠   60 次/分"
                )


                Text(
                    "😰 压力状态   95 次/分"
                )


            }


        }


        Spacer(
            Modifier.height(50.dp)
        )


    }


}




//====================
//心率曲线
//====================


@Composable
fun HeartChart(

    data:List<Int>

){



    Canvas(

        modifier =
            Modifier
                .fillMaxWidth()
                .height(220.dp)

    ){


        val step =
            size.width /
                    (data.size-1)



        for(i in 0 until data.size-1){



            drawLine(

                color =
                    Color.Red,


                start =
                    Offset(

                        i*step,

                        size.height -
                                data[i]

                    ),



                end =
                    Offset(

                        (i+1)*step,

                        size.height -
                                data[i+1]

                    ),



                strokeWidth = 5f


            )



        }


    }


}