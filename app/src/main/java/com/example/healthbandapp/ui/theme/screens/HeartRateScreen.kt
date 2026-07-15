package com.example.healthbandapp.ui.theme.screens


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random


@Composable
fun HeartRateScreen(){


    var heartRate by remember{
        mutableStateOf(76)
    }


    Column(

        modifier =
            Modifier
                .fillMaxSize()
                .padding(20.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally

    ){


        Text(

            "❤️ 实时心率监测",

            fontSize = 24.sp

        )



        Spacer(
            Modifier.height(30.dp)
        )



        //仪表盘


        Box(

            modifier =
                Modifier
                    .size(220.dp)
                    .background(
                        Color(0xffffeeee),
                        CircleShape
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

                    contentDescription=null,

                    tint=Color.Red,

                    modifier =
                        Modifier.size(45.dp)

                )


                Text(

                    "$heartRate",

                    fontSize = 55.sp,

                    color=Color.Red

                )


                Text(
                    "BPM"
                )


            }



        }



        Spacer(
            Modifier.height(30.dp)
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
                    "实时状态",
                    fontSize=20.sp
                )


                Text(
                    "❤️ 心率正常"
                )


                Text(
                    "静息心率 65 BPM"
                )


                Text(
                    "最高 125 BPM"
                )


            }

        }




        Spacer(
            Modifier.height(20.dp)
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

                    "实时心电波形",

                    fontSize=20.sp

                )


                Spacer(
                    Modifier.height(10.dp)
                )



                Canvas(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(100.dp)

                ){


                    val path =
                        androidx.compose.ui.graphics.Path()



                    path.moveTo(
                        0f,
                        size.height/2
                    )


                    repeat(30){


                        path.lineTo(

                            it*25f,

                            size.height/2+
                                    Random.nextInt(-30,30)

                        )


                    }


                    drawPath(

                        path,

                        Color.Red,

                        style =
                            androidx.compose.ui.graphics.drawscope
                                .Stroke(
                                    width=4f,
                                    cap=StrokeCap.Round
                                )

                    )


                }


            }


        }



        Spacer(
            Modifier.height(20.dp)
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

                    "24小时心率",

                    fontSize=20.sp

                )


                Text(

                    """
120 ┤      *
100 ┤   *     *
 80 ┤************
 60 ┤

00   06   12   18   24

""",

                    fontSize=15.sp

                )



            }


        }


    }



}
