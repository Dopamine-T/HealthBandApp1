package com.example.healthbandapp.ui.theme.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthbandapp.api.RetrofitInstance
import com.example.healthbandapp.model.Warning



@Composable
fun WarningScreen() {


    var warningList by remember {
        mutableStateOf<List<Warning>>(emptyList())
    }


    var loading by remember {
        mutableStateOf(true)
    }


    var errorMessage by remember {
        mutableStateOf("")
    }



    LaunchedEffect(Unit) {


        try {


            val result =
                RetrofitInstance.api.getWarningList()


            warningList =
                result.data


        } catch (e:Exception) {


            errorMessage =
                "请求失败:\n${e.message}"


        }


        loading=false


    }





    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFF7F8FC)
            )
            .padding(16.dp)

    ){



        Text(

            text = "⚠️ 健康预警",

            fontSize = 28.sp,

            fontWeight =
                FontWeight.Bold

        )



        Spacer(
            Modifier.height(20.dp)
        )




        when{


            loading -> {


                Box(

                    modifier =
                        Modifier.fillMaxSize(),

                    contentAlignment =
                        Alignment.Center

                ){

                    CircularProgressIndicator()

                }


            }




            errorMessage.isNotEmpty()->{


                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    colors =
                        CardDefaults.cardColors(

                            containerColor =
                                Color(0xFFFFEBEE)

                        ),

                    shape =
                        RoundedCornerShape(20.dp)

                ){


                    Text(

                        text =
                            errorMessage,

                        modifier =
                            Modifier.padding(20.dp),

                        color =
                            Color.Red

                    )


                }


            }





            warningList.isEmpty()->{


                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(20.dp)

                ){


                    Text(

                        text =
                            "🎉 当前暂无健康预警",

                        modifier =
                            Modifier.padding(20.dp),

                        fontSize =
                            18.sp

                    )


                }


            }





            else->{


                LazyColumn(

                    verticalArrangement =
                        Arrangement.spacedBy(14.dp)

                ){


                    items(warningList){warning ->


                        WarningCard(warning)


                    }


                }


            }


        }



    }


}







@Composable
fun WarningCard(

    warning: Warning

){



    val levelColor = when(warning.level){


        "高风险",
        "严重",
        "危险" ->
            Color(0xFFE53935)


        "中风险",
        "注意" ->
            Color(0xFFFF9800)


        else ->
            Color(0xFF43A047)

    }





    Card(

        modifier =
            Modifier.fillMaxWidth(),


        shape =
            RoundedCornerShape(24.dp),


        elevation =
            CardDefaults.cardElevation(
                4.dp
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

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically

            ){



                Text(

                    text =
                        warning.level,

                    fontSize =
                        22.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        levelColor

                )




                Surface(

                    shape =
                        RoundedCornerShape(50.dp),

                    color =
                        levelColor.copy(
                            alpha = 0.15f
                        )

                ){


                    Text(

                        text =
                            if(warning.status == "1")
                                "处理中"
                            else
                                "已记录",

                        modifier =
                            Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 6.dp
                            ),

                        fontSize =
                            12.sp,

                        color =
                            levelColor

                    )


                }



            }





            Spacer(
                Modifier.height(16.dp)
            )



            Text(

                text =
                    warning.content,

                fontSize =
                    16.sp,

                lineHeight =
                    24.sp

            )




            Spacer(
                Modifier.height(12.dp)
            )




            HorizontalDivider()



            Spacer(
                Modifier.height(12.dp)
            )



            Text(

                text =
                    "类型：${warning.type}",


                fontSize =
                    14.sp,

                color =
                    Color.Gray

            )



        }



    }


}