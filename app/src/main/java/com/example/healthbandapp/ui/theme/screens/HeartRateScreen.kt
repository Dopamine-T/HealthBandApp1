package com.example.healthbandapp.ui.theme.screens


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeartRateScreen(
    navController: NavHostController
) {


    // ========================
    // 固定心率数据
    // ========================

    val heartRate = 75


    val heartData = listOf(

        65,70,68,62,
        60,72,80,90,
        95,88,82,78,
        85,92,100,110,
        105,98,90,85,
        80,75,70,68

    )


    var selectedIndex by remember {
        mutableIntStateOf(-1)
    }



    val isNormal = heartRate in 50..120


    val statusText =
        if(isNormal)
            "状态正常"
        else
            "心率异常"



    val statusColor by animateColorAsState(

        targetValue =
            if(isNormal)
                Color(0xFF4CAF50)
            else
                Color.Red,

        animationSpec = tween(300),

        label = "status"

    )



    val healthAdvice =
        "当前心率处于健康范围，请保持规律运动和良好的作息习惯。"



    Scaffold(


        topBar = {


            TopAppBar(


                title = {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){


                        Icon(

                            Icons.Filled.MonitorHeart,

                            contentDescription = null,

                            tint = MaterialTheme.colorScheme.primary

                        )


                        Spacer(
                            Modifier.width(8.dp)
                        )


                        Text(
                            "心率监测",
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


                },


                actions = {


                    Icon(

                        Icons.Filled.Refresh,

                        contentDescription = "同步",

                        tint = MaterialTheme.colorScheme.primary,

                        modifier = Modifier.padding(end = 16.dp)

                    )


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

                .padding(20.dp),


            verticalArrangement = Arrangement.spacedBy(20.dp)


        ){



            // ========================
            // 1.实时心率
            // ========================


            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
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

                        .padding(24.dp),


                    contentAlignment = Alignment.Center


                ){


                    Column(

                        horizontalAlignment = Alignment.CenterHorizontally

                    ){


                        Text(

                            "实时心率",

                            color = Color.White.copy(alpha = 0.8f)

                        )


                        Spacer(
                            Modifier.height(15.dp)
                        )


                        Box(

                            modifier = Modifier

                                .size(220.dp)

                                .clip(CircleShape)

                                .background(
                                    Color.White.copy(alpha = 0.15f)
                                ),

                            contentAlignment = Alignment.Center

                        ){


                            Column(

                                horizontalAlignment = Alignment.CenterHorizontally

                            ){


                                Icon(

                                    Icons.Filled.MonitorHeart,

                                    contentDescription = null,

                                    tint = Color.White,

                                    modifier = Modifier.size(45.dp)

                                )


                                Text(

                                    "$heartRate",

                                    fontSize = 60.sp,

                                    fontWeight = FontWeight.Bold,

                                    color = Color.White

                                )


                                Text(

                                    "BPM",

                                    color = Color.White

                                )


                            }


                        }



                        Spacer(
                            Modifier.height(15.dp)
                        )


                        Text(

                            statusText,

                            color = statusColor,

                            fontWeight = FontWeight.Bold,

                            fontSize = 18.sp

                        )


                        Text(

                            "数据来源：健康手环",

                            color = Color.White.copy(alpha = 0.7f),

                            fontSize = 12.sp

                        )


                    }


                }


            }
            // ========================
            // 2.健康建议
            // ========================

            HeartCard(

                title = "健康建议",

                icon = Icons.Filled.Lightbulb,

                iconTint = Color(0xFF006064),

                containerColor = Color(0xFFE0F7FA)

            ){

                Text(

                    text = healthAdvice,

                    fontSize = 14.sp,

                    color = Color(0xFF006064)

                )

            }



            // ========================
            // 3.24小时心率曲线
            // ========================


            HeartCard(

                title = "24小时连续心率",

                icon = Icons.Filled.ShowChart,

                iconTint = Color(0xFFEF5350)

            ){


                Row(

                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = Arrangement.SpaceBetween,

                    verticalAlignment = Alignment.CenterVertically

                ){


                    Text(

                        "全天趋势",

                        color = Color.Gray,

                        fontSize = 14.sp

                    )


                    Text(

                        "平均: 80 BPM",

                        color = Color(0xFFEF5350),

                        fontWeight = FontWeight.Bold,

                        fontSize = 14.sp

                    )


                }



                Spacer(
                    Modifier.height(15.dp)
                )



                HeartChart(

                    data = heartData,

                    selectedIndex = selectedIndex,

                    onSelect = {

                        selectedIndex = it

                    }

                )



                Spacer(
                    Modifier.height(10.dp)
                )



                Row(

                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = Arrangement.SpaceBetween

                ){


                    Text("0:00", fontSize = 12.sp, color = Color.Gray)

                    Text("6:00", fontSize = 12.sp, color = Color.Gray)

                    Text("12:00", fontSize = 12.sp, color = Color.Gray)

                    Text("18:00", fontSize = 12.sp, color = Color.Gray)

                    Text("24:00", fontSize = 12.sp, color = Color.Gray)


                }


            }




            if(selectedIndex >= 0){


                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),

                    verticalAlignment = Alignment.CenterVertically

                ){


                    Icon(

                        Icons.Filled.AccessTime,

                        contentDescription = null,

                        modifier = Modifier.size(16.dp),

                        tint = MaterialTheme.colorScheme.primary

                    )


                    Spacer(
                        Modifier.width(6.dp)
                    )



                    Text(

                        "${selectedIndex}:00 时心率：${heartData[selectedIndex]} BPM",

                        fontWeight = FontWeight.Bold,

                        color = MaterialTheme.colorScheme.primary

                    )


                }


            }




            // ========================
            // 4.今日概览
            // ========================


            HeartCard(

                title = "今日概览",

                icon = Icons.Filled.Info,

                iconTint = Color(0xFF1565C0),

                containerColor = Color(0xFFE3F2FD)

            ){



                InfoRow(

                    "平均心率",

                    "80 BPM"

                )


                InfoRow(

                    "峰谷心率",

                    "55~120 BPM"

                )


                InfoRow(

                    "峰值心率",

                    "140 BPM"

                )


            }



            Spacer(
                Modifier.height(40.dp)
            )


        }


    }


}






//================================
// 通用Card组件
//================================


@Composable
fun HeartCard(

    title:String,

    icon:ImageVector,

    iconTint:Color,

    containerColor:Color =
        MaterialTheme.colorScheme.surface,

    content:@Composable ColumnScope.()->Unit

){


    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(

            containerColor = containerColor

        ),

        elevation = CardDefaults.cardElevation(

            defaultElevation = 4.dp

        )

    ){



        Column(

            modifier = Modifier.padding(20.dp)

        ){


            Row(

                verticalAlignment = Alignment.CenterVertically

            ){



                Box(

                    modifier = Modifier

                        .size(32.dp)

                        .clip(CircleShape)

                        .background(

                            iconTint.copy(alpha = 0.1f)

                        ),

                    contentAlignment = Alignment.Center

                ){



                    Icon(

                        icon,

                        contentDescription = title,

                        tint = iconTint,

                        modifier = Modifier.size(20.dp)

                    )


                }



                Spacer(
                    Modifier.width(10.dp)
                )


                Text(

                    title,

                    fontSize = 18.sp,

                    fontWeight = FontWeight.Bold

                )


            }



            Spacer(
                Modifier.height(15.dp)
            )



            content()


        }


    }


}






//================================
// 信息行
//================================


@Composable
fun InfoRow(

    label:String,

    value:String

){


    Row(

        modifier = Modifier

            .fillMaxWidth()

            .padding(vertical = 5.dp),

        horizontalArrangement = Arrangement.SpaceBetween

    ){


        Text(

            label,

            color = Color.Gray

        )


        Text(

            value,

            fontWeight = FontWeight.Bold

        )


    }


}






//================================
// 心率曲线
//================================


@Composable
fun HeartChart(

    data:List<Int>,

    selectedIndex:Int,

    onSelect:(Int)->Unit

){


    Canvas(

        modifier = Modifier

            .fillMaxWidth()

            .height(230.dp)

            .pointerInput(Unit){


                detectTapGestures {


                        offset ->



                    val index =

                        (

                                offset.x /

                                        size.width *

                                        (data.size-1)

                                )

                            .toInt()

                            .coerceIn(
                                0,
                                data.size-1
                            )


                    onSelect(index)


                }


            }


    ){



        val step =
            size.width/(data.size-1)



        val max = 140

        val min = 40



        val path = Path()



        data.forEachIndexed{

                index,value ->



            val x =
                index*step


            val y =

                size.height -

                        ((value-min).toFloat()
                                /
                                (max-min)
                                *
                                size.height)



            if(index==0)

                path.moveTo(x,y)

            else

                path.lineTo(x,y)



        }




        drawPath(

            path,

            color = Color(0xFFE53935),

            style = Stroke(

                width = 5f,

                cap = StrokeCap.Round

            )

        )




        if(selectedIndex>=0){


            val x =
                selectedIndex*step



            val y =

                size.height -

                        ((data[selectedIndex]-min)
                            .toFloat()
                                /
                                (max-min)
                                *
                                size.height)



            drawCircle(

                color = Color.White,

                radius = 10f,

                center = Offset(x,y)

            )


            drawCircle(

                color = Color(0xFFE53935),

                radius = 7f,

                center = Offset(x,y)

            )


        }


    }


}