package com.example.healthbandapp.ui.theme.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.healthbandapp.api.RetrofitInstance
import com.example.healthbandapp.model.DeviceInfo



@Composable
fun DeviceTestScreen(){


    var result by remember {

        mutableStateOf(
            "等待请求..."
        )

    }



    LaunchedEffect(Unit){


        try {


            val response =

                RetrofitInstance
                    .deviceApi
                    .getDeviceInfo()



            val device:DeviceInfo =

                response.data



            result = """

请求成功

设备名称:
${device.deviceName}

设备编号:
${device.deviceId}

设备状态:
${device.status}

用户ID:
${device.userId}

设备ID:
${device.id}

""".trimIndent()



        }catch(e:Exception){


            result =
                """
请求失败

${e.message}

""".trimIndent()


        }



    }





    Column(

        modifier =
            Modifier.padding(20.dp)

    ){


        Text(
            result
        )


    }



}