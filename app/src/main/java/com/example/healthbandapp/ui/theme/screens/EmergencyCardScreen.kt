package com.example.healthbandapp.ui.theme.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyCardScreen(
    navController: NavController
){


    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        "医疗急救卡",
                        fontWeight = FontWeight.Bold
                    )

                },

                colors = TopAppBarDefaults.topAppBarColors(

                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer

                )

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

                .padding(16.dp)

        ){



            //========================
            // 顶部渐变信息卡
            //========================


            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(22.dp),

                colors =
                    CardDefaults.cardColors(
                        Color.Transparent
                    )

            ){


                Box(

                    modifier = Modifier

                        .background(

                            Brush.verticalGradient(

                                listOf(

                                    Color(0xFF536DA8),

                                    Color(0xFF7D91C4)

                                )

                            )

                        )

                        .padding(24.dp)

                ){



                    Column {



                        Row(

                            verticalAlignment =
                                Alignment.CenterVertically

                        ){


                            Box(

                                modifier =
                                    Modifier

                                        .size(48.dp)

                                        .clip(CircleShape)

                                        .background(
                                            Color.White.copy(
                                                alpha = 0.25f
                                            )
                                        ),

                                contentAlignment =
                                    Alignment.Center

                            ){

                                Icon(

                                    Icons.Default.Warning,

                                    contentDescription = null,

                                    tint = Color.White

                                )

                            }



                            Spacer(
                                Modifier.width(12.dp)
                            )



                            Text(

                                "紧急医疗信息",

                                color = Color.White,

                                fontSize = 18.sp,

                                fontWeight =
                                    FontWeight.Bold

                            )


                        }



                        Spacer(
                            Modifier.height(20.dp)
                        )



                        Text(

                            "姓名",

                            color =
                                Color.White.copy(
                                    alpha = 0.8f
                                ),

                            fontSize = 14.sp

                        )


                        Text(

                            EmergencyData.name,

                            color = Color.White,

                            fontSize = 32.sp,

                            fontWeight =
                                FontWeight.Bold

                        )



                        Spacer(
                            Modifier.height(18.dp)
                        )



                        Row(

                            horizontalArrangement =
                                Arrangement.SpaceBetween,

                            modifier =
                                Modifier.fillMaxWidth()

                        ){



                            EmergencyMini(

                                Icons.Default.Bloodtype,

                                EmergencyData.blood

                            )



                            EmergencyMini(

                                Icons.Default.Phone,

                                EmergencyData.phone

                            )


                        }



                    }


                }


            }





            Spacer(
                Modifier.height(24.dp)
            )





            Text(

                "急救信息",

                fontSize = 18.sp,

                fontWeight =
                    FontWeight.Bold

            )



            Spacer(
                Modifier.height(12.dp)
            )




            EmergencyInfoCard(

                Icons.Default.Person,

                "姓名",

                EmergencyData.name

            )



            EmergencyInfoCard(

                Icons.Default.Phone,

                "手机号",

                EmergencyData.phone

            )



            EmergencyInfoCard(

                Icons.Default.Bloodtype,

                "血型",

                EmergencyData.blood

            )



            EmergencyInfoCard(

                Icons.Default.ContactPhone,

                "紧急联系人",

                EmergencyData.contact

            )



            EmergencyInfoCard(

                Icons.Default.Warning,

                "过敏史",

                EmergencyData.allergy

            )





            Spacer(
                Modifier.height(20.dp)
            )





            Button(

                onClick = {

                    navController.navigate(
                        "emergencyEdit"
                    )

                },

                modifier = Modifier

                    .fillMaxWidth()

                    .height(52.dp),

                shape =
                    RoundedCornerShape(18.dp)

            ){


                Icon(

                    Icons.Default.Edit,

                    null

                )


                Spacer(
                    Modifier.width(8.dp)
                )


                Text(
                    "编辑急救信息"
                )


            }


        }


    }


}





@Composable
fun EmergencyMini(

    icon: androidx.compose.ui.graphics.vector.ImageVector,

    text:String

){


    Row(

        verticalAlignment =
            Alignment.CenterVertically

    ){

        Icon(

            icon,

            null,

            tint = Color.White

        )


        Spacer(
            Modifier.width(6.dp)
        )


        Text(

            text,

            color = Color.White,

            fontSize = 14.sp

        )

    }

}





@Composable
fun EmergencyInfoCard(

    icon: androidx.compose.ui.graphics.vector.ImageVector,

    title:String,

    value:String

){


    Card(

        modifier =
            Modifier

                .fillMaxWidth()

                .padding(
                    vertical = 5.dp
                ),

        shape =
            RoundedCornerShape(16.dp),

        elevation =
            CardDefaults.cardElevation(
                1.dp
            )

    ){



        Row(

            modifier =
                Modifier.padding(16.dp),

            verticalAlignment =
                Alignment.CenterVertically

        ){



            Box(

                modifier =
                    Modifier

                        .size(44.dp)

                        .clip(CircleShape)

                        .background(
                            MaterialTheme.colorScheme.primaryContainer
                        ),

                contentAlignment =
                    Alignment.Center

            ){

                Icon(

                    icon,

                    null,

                    tint =
                        MaterialTheme.colorScheme.primary

                )

            }



            Spacer(
                Modifier.width(16.dp)
            )



            Column {


                Text(

                    title,

                    fontSize = 12.sp,

                    color = Color.Gray

                )


                Text(

                    value,

                    fontSize = 16.sp,

                    fontWeight =
                        FontWeight.Bold

                )


            }



        }


    }


}