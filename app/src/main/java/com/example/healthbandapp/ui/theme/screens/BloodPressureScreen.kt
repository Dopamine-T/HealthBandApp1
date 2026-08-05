package com.example.healthbandapp.ui.theme.screens


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlin.random.Random



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodPressureScreen(
    navController: NavHostController
){

    var systolic by remember {
        mutableIntStateOf(118)
    }

    var diastolic by remember {
        mutableIntStateOf(76)
    }


    var tab by remember {
        mutableIntStateOf(0)
    }



    val systolicData =
        remember {
            List(24){
                Random.nextInt(110,135)
            }
        }


    val diastolicData =
        remember {
            List(24){
                Random.nextInt(65,90)
            }
        }



    var selectedIndex by remember {
        mutableIntStateOf(-1)
    }



    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){

                        Icon(
                            Icons.Filled.Bloodtype,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )


                        Spacer(
                            Modifier.width(8.dp)
                        )


                        Text(
                            "血压监测",
                            fontWeight = FontWeight.Bold
                        )

                    }

                },


                navigationIcon = {

                    IconButton(

                        onClick = {
                            navController.popBackStack()
                        }

                    ){

                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )

                    }

                }

            )

        }


    ){ padding ->



        Column(

            modifier = Modifier

                .fillMaxSize()

                .padding(padding)

                .verticalScroll(
                    rememberScrollState()
                )

                .padding(16.dp),


            verticalArrangement =
                Arrangement.spacedBy(16.dp)

        ){






// 后续第二部分继续
            //========================
            // 血压仪表盘
            //========================


            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(22.dp),

                colors = CardDefaults.cardColors(

                    containerColor = Color.Transparent

                ),

                elevation = CardDefaults.cardElevation(

                    defaultElevation = 5.dp

                )

            ){


                Box(

                    modifier = Modifier

                        .background(

                            Brush.linearGradient(

                                listOf(

                                    Color(0xFFB71C1C),

                                    Color(0xFFE53935)

                                )

                            )

                        )

                        .fillMaxWidth(),


                    contentAlignment = Alignment.Center

                ){



                    Column(

                        modifier = Modifier.padding(18.dp),

                        horizontalAlignment =
                            Alignment.CenterHorizontally

                    ){



                        Text(

                            "当前血压",

                            color = Color.White.copy(
                                alpha = 0.8f
                            ),

                            fontSize = 14.sp

                        )



                        Spacer(

                            Modifier.height(8.dp)

                        )




                        Box(

                            modifier = Modifier

                                .size(170.dp)

                                .clip(CircleShape)

                                .background(

                                    Color.White.copy(
                                        alpha = 0.15f
                                    )

                                ),


                            contentAlignment =
                                Alignment.Center


                        ){



                            Column(

                                horizontalAlignment =
                                    Alignment.CenterHorizontally

                            ){



                                Icon(

                                    Icons.Filled.MonitorHeart,

                                    contentDescription = null,

                                    tint = Color.White,

                                    modifier =
                                        Modifier.size(35.dp)

                                )



                                Text(

                                    "$systolic/$diastolic",

                                    fontSize = 38.sp,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color = Color.White

                                )



                                Text(

                                    "mmHg",

                                    fontSize = 13.sp,

                                    color =
                                        Color.White.copy(
                                            alpha = 0.8f
                                        )

                                )



                            }



                        }



                        Spacer(

                            Modifier.height(8.dp)

                        )



                        Text(

                            "状态正常",

                            color = Color.White,

                            fontWeight = FontWeight.Bold,

                            fontSize = 16.sp

                        )



                    }


                }


            }





            //========================
            // 24小时血压趋势
            //========================


            BloodPressureCard(

                title = "24小时血压趋势",

                icon = Icons.AutoMirrored.Filled.ShowChart,

                iconTint = Color(0xFFE53935),

                containerColor = Color.White

            ){



                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween

                ){



                    Text(

                        "收缩压 / 舒张压",

                        color = Color.Gray,

                        fontSize = 13.sp

                    )



                    Row{


                        Text(

                            "● 高压",

                            color = Color.Red,

                            fontSize = 12.sp

                        )


                        Spacer(
                            Modifier.width(8.dp)
                        )


                        Text(

                            "● 低压",

                            color = Color.Blue,

                            fontSize = 12.sp

                        )


                    }



                }



                Spacer(

                    Modifier.height(8.dp)

                )




                BloodPressureChart(

                    systolic = systolicData,

                    diastolic = diastolicData,

                    selectedIndex = selectedIndex,

                    onSelect = {

                        selectedIndex = it

                    }

                )



                Spacer(

                    Modifier.height(5.dp)

                )



                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween

                ){



                    Text(
                        "0:00",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )


                    Text(
                        "6:00",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )


                    Text(
                        "12:00",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )


                    Text(
                        "18:00",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )


                    Text(
                        "24:00",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )


                }



            }





            //========================
            // 点击详情
            //========================


            if(selectedIndex >= 0){


                Card(

                    modifier =
                        Modifier.fillMaxWidth(),


                    colors =
                        CardDefaults.cardColors(

                            containerColor =
                                Color(0xFFE3F2FD)

                        )

                ){



                    Row(

                        modifier =
                            Modifier.padding(12.dp),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ){



                        Icon(

                            Icons.Filled.Info,

                            contentDescription = null,

                            tint =
                                MaterialTheme.colorScheme.primary

                        )


                        Spacer(

                            Modifier.width(8.dp)

                        )



                        Text(

                            "${selectedIndex}:00  "

                                    +
                                    "收缩压 ${systolicData[selectedIndex]} "

                                    +
                                    "舒张压 ${diastolicData[selectedIndex]} mmHg",

                            fontSize = 13.sp,

                            color =
                                MaterialTheme.colorScheme.primary

                        )


                    }


                }



            }





            //========================
            // 今日概览
            //========================


            BloodPressureCard(

                title = "今日概览",

                icon = Icons.Filled.Info,

                iconTint = Color(0xFF1565C0),

                containerColor =
                    Color(0xFFE3F2FD)

            ){



                BloodInfoRow(

                    "平均血压",

                    "118/76 mmHg"

                )



                BloodInfoRow(

                    "最高血压",

                    "135/88 mmHg"

                )



                BloodInfoRow(

                    "最低血压",

                    "105/65 mmHg"

                )


            }



            Spacer(

                Modifier.height(30.dp)

            )



        }


    }


}
@Composable
fun BloodPressureCard(

    title:String,

    icon:ImageVector,

    iconTint:Color,

    containerColor:Color,

    content:@Composable ColumnScope.()->Unit

){


    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(

            containerColor = containerColor

        ),

        elevation = CardDefaults.cardElevation(

            defaultElevation = 3.dp

        )

    ){


        Column(

            modifier =
                Modifier.padding(14.dp)

        ){


            Row(

                verticalAlignment =
                    Alignment.CenterVertically

            ){


                Box(

                    modifier =
                        Modifier

                            .size(32.dp)

                            .clip(CircleShape)

                            .background(

                                iconTint.copy(
                                    alpha = 0.12f
                                )

                            ),


                    contentAlignment =
                        Alignment.Center


                ){


                    Icon(

                        icon,

                        contentDescription = null,

                        tint = iconTint,

                        modifier =
                            Modifier.size(18.dp)

                    )


                }



                Spacer(

                    Modifier.width(10.dp)

                )


                Text(

                    title,

                    fontSize = 17.sp,

                    fontWeight =
                        FontWeight.Bold

                )



            }



            Spacer(

                Modifier.height(10.dp)

            )


            content()


        }


    }


}
@Composable
fun BloodInfoRow(

    title:String,

    value:String

){


    Row(

        modifier =
            Modifier

                .fillMaxWidth()

                .padding(vertical = 5.dp),


        horizontalArrangement =
            Arrangement.SpaceBetween


    ){



        Text(

            title,

            color = Color.Gray,

            fontSize = 14.sp

        )



        Text(

            value,

            fontSize = 14.sp,

            fontWeight =
                FontWeight.Bold

        )


    }


}
@Composable
fun BloodPressureChart(

    systolic:List<Int>,

    diastolic:List<Int>,

    selectedIndex:Int,

    onSelect:(Int)->Unit

){



    Canvas(

        modifier = Modifier

            .fillMaxWidth()

            .height(190.dp)

            .pointerInput(Unit){



                detectTapGestures{

                        offset ->



                    val index =

                        (

                                offset.x /

                                        size.width *

                                        (systolic.size-1)

                                )

                            .toInt()

                            .coerceIn(

                                0,

                                systolic.size-1

                            )



                    onSelect(index)



                }



            }


    ){



        val maxValue = 150f

        val minValue = 50f



        val step =

            size.width /

                    (systolic.size-1)



        fun getY(value:Int):Float{


            val ratio =

                (value-minValue)



            (maxValue-minValue)



            return size.height -

                    ratio *

                    size.height


        }





        //================
        // 背景虚线
        //================


        listOf(

            150,

            120,

            90,

            60

        ).forEach{


                value ->


            drawLine(

                color =
                    Color.Gray.copy(
                        alpha = 0.2f
                    ),


                start =
                    Offset(

                        0f,

                        getY(value)

                    ),


                end =
                    Offset(

                        size.width,

                        getY(value)

                    ),


                strokeWidth = 1f,


                pathEffect =
                    PathEffect.dashPathEffect(

                        floatArrayOf(
                            8f,
                            8f
                        )

                    )

            )


        }




        //================
        // 高压路径
        //================


        val highPath = Path()



        systolic.forEachIndexed{


                index,value ->



            val x =
                index * step


            val y =
                getY(value)



            if(index==0)

                highPath.moveTo(
                    x,
                    y
                )

            else

                highPath.lineTo(
                    x,
                    y
                )


        }




        // 高压填充


        val highFill = Path().apply{


            addPath(highPath)


            lineTo(
                size.width,
                size.height
            )


            lineTo(
                0f,
                size.height
            )


            close()


        }



        drawPath(

            highFill,

            brush =
                Brush.verticalGradient(

                    listOf(

                        Color.Red.copy(
                            alpha = 0.25f
                        ),

                        Color.Transparent

                    )

                )

        )



        drawPath(

            highPath,

            Color.Red,

            style =
                Stroke(

                    width = 4f,

                    cap =
                        StrokeCap.Round

                )

        )





        //================
        // 低压路径
        //================


        val lowPath = Path()



        diastolic.forEachIndexed{


                index,value ->



            val x =
                index * step



            val y =
                getY(value)



            if(index==0)

                lowPath.moveTo(
                    x,
                    y
                )

            else

                lowPath.lineTo(
                    x,
                    y
                )


        }





        drawPath(

            lowPath,

            Color.Blue,

            style =
                Stroke(

                    width = 4f,

                    cap =
                        StrokeCap.Round

                )

        )





        //================
        // 点击节点
        //================


        if(selectedIndex>=0){



            val x =
                selectedIndex*step



            val highY =
                getY(
                    systolic[selectedIndex]
                )


            val lowY =
                getY(
                    diastolic[selectedIndex]
                )



            drawLine(

                Color.Gray.copy(
                    alpha = 0.5f
                ),


                Offset(
                    x,
                    0f
                ),


                Offset(
                    x,
                    size.height
                ),


                strokeWidth = 2f


            )



            drawCircle(

                Color.White,

                8f,

                Offset(
                    x,
                    highY
                )

            )


            drawCircle(

                Color.Red,

                5f,

                Offset(
                    x,
                    highY
                )

            )



            drawCircle(

                Color.White,

                8f,

                Offset(
                    x,
                    lowY
                )

            )


            drawCircle(

                Color.Blue,

                5f,

                Offset(
                    x,
                    lowY
                )

            )



        }



    }



}