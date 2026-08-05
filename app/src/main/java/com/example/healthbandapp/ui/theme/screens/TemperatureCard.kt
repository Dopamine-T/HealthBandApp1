package com.example.healthbandapp.ui.theme.card


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController



@Composable
fun TemperatureCard(

    navController: NavHostController

){


    Card(

        modifier =
            Modifier

                .fillMaxWidth()

                .height(150.dp)

                .clickable {

                    navController.navigate(
                        "temperature"
                    )

                },


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
                Modifier.padding(18.dp)

        ){



            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically

            ){



                // 温度图标背景

                Box(

                    modifier =
                        Modifier

                            .size(42.dp)

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
                            Modifier.size(24.dp)

                    )


                }




                Spacer(
                    Modifier.width(14.dp)
                )




                Text(

                    text =
                        "体温监测",

                    fontSize =
                        18.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Color(0xFFF57F17)

                )




                Spacer(
                    Modifier.weight(1f)
                )




                Icon(

                    imageVector =
                        Icons.Default.ChevronRight,

                    contentDescription =
                        null,

                    tint =
                        Color(0xFFF57F17)

                )


            }





            Spacer(
                Modifier.height(18.dp)
            )





            Text(

                text =
                    "体温：36.5℃",

                fontSize =
                    15.sp,

                color =
                    Color(0xFFE65100)

            )





            Spacer(
                Modifier.height(6.dp)
            )





            Text(

                text =
                    "状态：正常",

                fontSize =
                    15.sp,

                color =
                    Color(0xFFE65100)

            )



        }


    }


}