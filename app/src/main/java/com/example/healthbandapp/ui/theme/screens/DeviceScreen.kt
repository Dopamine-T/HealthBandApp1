package com.example.healthbandapp.ui.theme.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun DeviceScreen() {


    var isConnected by remember {
        mutableStateOf(true)
    }

    var isSyncing by remember {
        mutableStateOf(false)
    }

    var isFinding by remember {
        mutableStateOf(false)
    }


    var wristRaiseEnabled by remember {
        mutableStateOf(true)
    }

    var sedentaryReminderEnabled by remember {
        mutableStateOf(false)
    }

    var doNotDisturbEnabled by remember {
        mutableStateOf(false)
    }


    val deviceName = "MyBand X1"
    val battery = 78



    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFF7F8FC)
            )
            .padding(16.dp)
            .verticalScroll(
                rememberScrollState()
            ),

        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {



        /*
        设备状态卡
         */

        Card(

            modifier = Modifier
                .fillMaxWidth(),

            shape = RoundedCornerShape(26.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8F5E9)
            ),

            elevation = CardDefaults.cardElevation(
                5.dp
            )

        ){


            Row(

                modifier = Modifier
                    .padding(20.dp),

                verticalAlignment = Alignment.CenterVertically

            ){


                Icon(

                    imageVector =
                        Icons.Default.Bluetooth,

                    contentDescription = null,

                    tint =
                        Color(0xFF43A047),

                    modifier =
                        Modifier.size(40.dp)

                )


                Spacer(
                    Modifier.width(16.dp)
                )


                Column {


                    Text(

                        text = deviceName,

                        fontSize = 22.sp,

                        fontWeight =
                            FontWeight.Bold

                    )


                    Spacer(
                        Modifier.height(6.dp)
                    )


                    Text(

                        text =
                            if(isConnected)
                                "● 已连接"
                            else
                                "● 未连接",

                        color =
                            if(isConnected)
                                Color(0xFF43A047)
                            else
                                Color.Gray,

                        fontSize = 16.sp

                    )

                }

            }


        }





        /*
        电池卡片
         */


        Card(

            modifier = Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(26.dp),

            colors =
                CardDefaults.cardColors(

                    containerColor =
                        Color(0xFFEAF7FF)

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


                    Icon(

                        Icons.Default.BatteryFull,

                        contentDescription = null,

                        tint =
                            Color(0xFF2196F3)

                    )


                    Spacer(
                        Modifier.width(10.dp)
                    )


                    Text(

                        "电池状态",

                        fontSize = 18.sp,

                        fontWeight =
                            FontWeight.Bold

                    )

                }



                Spacer(
                    Modifier.height(12.dp)
                )


                Text(

                    "$battery%",

                    fontSize = 36.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Color(0xFF2196F3)

                )


                LinearProgressIndicator(

                    progress = {
                        battery / 100f
                    },


                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(
                                RoundedCornerShape(10.dp)
                            ),

                    color =
                        Color(0xFF2196F3),

                    trackColor =
                        Color(0xFFDDEEFF)

                )


            }


        }





        /*
        设备信息
         */


        DeviceInfoCard()



        /*
        操作按钮
         */


        Button(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(55.dp),


            shape =
                RoundedCornerShape(30.dp),


            onClick = {

                isSyncing = true

            }

        ){


            Icon(
                Icons.Default.Sync,
                null
            )


            Spacer(
                Modifier.width(8.dp)
            )


            Text(
                if(isSyncing)
                    "正在同步..."
                else
                    "同步数据"
            )

        }




        Button(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(55.dp),


            shape =
                RoundedCornerShape(30.dp),


            colors =
                ButtonDefaults.buttonColors(

                    containerColor =
                        Color(0xFFFF9800)

                ),


            onClick = {

                isFinding = true

            }

        ){


            Icon(
                Icons.Default.VolumeUp,
                null
            )


            Spacer(
                Modifier.width(8.dp)
            )


            Text(

                if(isFinding)
                    "正在查找..."
                else
                    "查找手环"

            )

        }





        /*
        设置
         */


        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(26.dp)

        ){


            Column(

                modifier =
                    Modifier.padding(20.dp)

            ){


                Text(

                    "⚙️ 智能设置",

                    fontSize = 20.sp,

                    fontWeight =
                        FontWeight.Bold

                )


                Spacer(
                    Modifier.height(20.dp)
                )


                SettingItem(

                    title = "抬腕亮屏",

                    checked =
                        wristRaiseEnabled

                ){

                    wristRaiseEnabled = it

                }



                Spacer(
                    Modifier.height(16.dp)
                )


                SettingItem(

                    title = "久坐提醒",

                    checked =
                        sedentaryReminderEnabled

                ){

                    sedentaryReminderEnabled = it

                }



                Spacer(
                    Modifier.height(16.dp)
                )


                SettingItem(

                    title = "勿扰模式",

                    checked =
                        doNotDisturbEnabled

                ){

                    doNotDisturbEnabled = it

                }


            }


        }




        Button(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(55.dp),


            shape =
                RoundedCornerShape(30.dp),


            colors =
                ButtonDefaults.buttonColors(

                    containerColor =
                        Color(0xFFE53935)

                ),


            onClick = {

                isConnected=false

            }

        ){


            Text(
                "解除绑定"
            )


        }



        Spacer(
            Modifier.height(80.dp)
        )


    }


}





@Composable
fun DeviceInfoCard(){


    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(26.dp)

    ){


        Column(

            modifier =
                Modifier.padding(20.dp)

        ){


            Text(

                "📱 设备信息",

                fontSize = 20.sp,

                fontWeight =
                    FontWeight.Bold

            )


            Spacer(
                Modifier.height(15.dp)
            )


            InfoRow(
                "设备名称",
                "MyBand X1"
            )


            InfoRow(
                "MAC地址",
                "AA:BB:CC:DD"
            )


            InfoRow(
                "固件版本",
                "V1.2.3"
            )

        }

    }

}





@Composable
fun InfoRow(

    title:String,

    value:String

){


    Row(

        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),

        horizontalArrangement =
            Arrangement.SpaceBetween

    ){


        Text(

            title,

            color =
                Color.Gray

        )


        Text(

            value,

            fontWeight =
                FontWeight.Bold

        )


    }


}





@Composable
fun SettingItem(

    title:String,

    checked:Boolean,

    onChange:(Boolean)->Unit

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

            title,

            fontSize = 16.sp

        )


        Switch(

            checked = checked,

            onCheckedChange = onChange,

            colors =
                SwitchDefaults.colors(

                    checkedTrackColor =
                        Color(0xFF4CAF50)

                )

        )

    }


}