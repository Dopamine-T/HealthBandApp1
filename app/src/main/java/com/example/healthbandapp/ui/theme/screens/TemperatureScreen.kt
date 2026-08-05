package com.example.healthbandapp.ui.theme.screens


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlinx.coroutines.delay
import kotlin.random.Random



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureScreen(

    navController: NavHostController

){


    var temperature by remember {

        mutableStateOf(36.5)

    }



    var lastUpdateTime by remember {

        mutableStateOf("刚刚")

    }



    val temperatureData =
        remember {

            List(48){

                Random.nextDouble(
                    36.2,
                    37.0
                )

            }

        }



    LaunchedEffect(Unit){


        var count = 0


        while(true){


            delay(3000)


            temperature =

                (
                        temperature +
                                Random.nextDouble(
                                    -0.1,
                                    0.1
                                )

                        )
                    .coerceIn(
                        35.8,
                        38.0
                    )



            count++


            lastUpdateTime =
                "${count*3}秒前"


        }


    }





    val normal =

        temperature in 36.0..37.2





    val statusText =

        when{


            temperature < 36.0 ->

                "🟡 体温偏低"



            temperature <=37.2 ->

                "🟢 体温正常"



            else ->

                "🔴 可能发热"


        }





    val statusColor by animateColorAsState(


        targetValue =

            if(normal)

                Color(0xff43A047)

            else

                Color(0xffE53935),



        animationSpec =
            tween(300),


        label =
            "temperatureStatus"


    )





    val advice =

        when{


            temperature >37.2 ->

                "当前体温偏高，请注意休息并持续观察。"



            temperature <36.0 ->

                "体温偏低，请注意保暖。"



            else ->

                "体温正常，身体状态良好。"



        }







    Scaffold(


        topBar = {


            TopAppBar(


                title = {


                    Text(

                        text =
                            "🌡 体温监测",

                        fontWeight =
                            FontWeight.Bold

                    )


                },


                navigationIcon = {


                    IconButton(

                        onClick = {

                            navController
                                .popBackStack()

                        }

                    ){


                        Icon(

                            Icons.Default.ArrowBack,

                            null

                        )


                    }


                }


            )


        }



    ){padding ->






        Column(


            modifier =

                Modifier

                    .fillMaxSize()

                    .padding(padding)

                    .verticalScroll(

                        rememberScrollState()

                    )

                    .padding(20.dp)



        ){



            /*
             *
             * 当前体温卡片
             *
             */


            Card(


                modifier =

                    Modifier.fillMaxWidth(),



                shape =

                    RoundedCornerShape(24.dp),



                elevation =

                    CardDefaults.cardElevation(

                        defaultElevation = 4.dp

                    ),



                colors =

                    CardDefaults.cardColors(

                        containerColor =

                            Color(0xFFFFF8E1)

                    )


            ){



                Column(


                    modifier =

                        Modifier.padding(20.dp),



                    horizontalAlignment =

                        Alignment.CenterHorizontally



                ){



                    Text(

                        text =
                            "实时体温",

                        fontSize =
                            18.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color(0xFFF57F17)

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

                                    Color(0xFFFFECB3)

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

                                    Icons.Default.Thermostat,

                                contentDescription =

                                    null,

                                modifier =

                                    Modifier.size(45.dp),


                                tint =

                                    Color(0xFFF57F17)


                            )



                            Text(


                                text =

                                    "${String.format("%.1f",temperature)}℃",



                                fontSize =

                                    55.sp,


                                fontWeight =

                                    FontWeight.Bold,


                                color =

                                    Color(0xFFF57F17)



                            )



                        }



                    }
                    Spacer(

                        Modifier.height(15.dp)

                    )



                    Text(

                        text = statusText,

                        fontSize = 18.sp,

                        fontWeight = FontWeight.Bold,

                        color = statusColor

                    )



                    Spacer(

                        Modifier.height(4.dp)

                    )



                    Text(

                        text =
                            "最近更新：$lastUpdateTime",

                        fontSize = 12.sp,

                        color = Color.Gray

                    )


                }


            }





            Spacer(

                Modifier.height(20.dp)

            )






            /*
             *
             * 健康建议
             *
             */



            Card(


                modifier =

                    Modifier.fillMaxWidth(),



                shape =

                    RoundedCornerShape(24.dp),



                elevation =

                    CardDefaults.cardElevation(

                        defaultElevation = 4.dp

                    ),



                colors =

                    CardDefaults.cardColors(

                        containerColor =

                            Color(0xFFFFF3E0)

                    )


            ){



                Text(


                    text =

                        "💡 $advice",



                    modifier =

                        Modifier.padding(20.dp),



                    fontSize =

                        14.sp,


                    color =

                        Color(0xFFE65100)



                )



            }





            Spacer(

                Modifier.height(20.dp)

            )






            /*
             *
             * 24小时体温趋势
             *
             */



            Card(


                modifier =

                    Modifier.fillMaxWidth(),



                shape =

                    RoundedCornerShape(24.dp),



                elevation =

                    CardDefaults.cardElevation(

                        defaultElevation = 4.dp

                    ),



                colors =

                    CardDefaults.cardColors(

                        containerColor =

                            Color.White

                    )


            ){



                Column(



                    modifier =

                        Modifier.padding(20.dp)



                ){



                    Row(


                        verticalAlignment =

                            Alignment.CenterVertically



                    ){



                        Box(


                            modifier =

                                Modifier

                                    .size(32.dp)

                                    .background(

                                        Color(0xFFFFECB3),

                                        CircleShape

                                    ),


                            contentAlignment =

                                Alignment.Center



                        ){



                            Icon(


                                imageVector =

                                    Icons.Default.Thermostat,


                                contentDescription =

                                    null,


                                tint =

                                    Color(0xFFF57F17),


                                modifier =

                                    Modifier.size(18.dp)



                            )


                        }





                        Spacer(

                            Modifier.width(12.dp)

                        )





                        Text(


                            text =

                                "24小时体温变化",



                            fontSize =

                                18.sp,



                            fontWeight =

                                FontWeight.Bold



                        )



                    }





                    Spacer(

                        Modifier.height(20.dp)

                    )





                    TemperatureChart(

                        data = temperatureData

                    )




                }


            }



        }



    }



}
@Composable
fun TemperatureChart(

    data: List<Double>

){


    Canvas(


        modifier =

            Modifier

                .fillMaxWidth()

                .height(220.dp)


    ){



        val step =

            size.width /

                    (data.size - 1)




        val path =

            Path()



        val fillPath =

            Path()




        data.forEachIndexed{

                index,value ->



            val x =

                index * step




            val y =

                size.height -

                        (

                                (value - 35.5)

                                        /

                                        3.0

                                        *

                                        size.height

                                ).toFloat()




            if(index == 0){

                path.moveTo(x,y)

                fillPath.moveTo(x,y)

            }

            else{

                path.lineTo(x,y)

                fillPath.lineTo(x,y)

            }


        }




        // 填充区域

        fillPath.lineTo(

            size.width,

            size.height

        )


        fillPath.lineTo(

            0f,

            size.height

        )


        fillPath.close()



        drawPath(

            path = fillPath,

            brush = Brush.verticalGradient(

                listOf(

                    Color(0x55F57F17),

                    Color.Transparent

                )

            )

        )





        // 曲线


        drawPath(


            path = path,


            color =

                Color(0xFFF57F17),



            style =

                Stroke(

                    width = 4f

                )


        )



    }


}