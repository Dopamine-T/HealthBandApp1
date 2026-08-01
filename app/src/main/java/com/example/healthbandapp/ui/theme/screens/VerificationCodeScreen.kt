package com.example.healthbandapp.ui.theme.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.healthbandapp.api.RetrofitInstance
import com.example.healthbandapp.model.RegisterRequest

import kotlinx.coroutines.launch



@Composable
fun VerificationCodeScreen() {



    val scope = rememberCoroutineScope()



    var message by remember {

        mutableStateOf("")

    }




    var phone by remember {

        mutableStateOf("")

    }



    var password by remember {

        mutableStateOf("")

    }



    var confirmPassword by remember {

        mutableStateOf("")

    }



    var verificationCode by remember {

        mutableStateOf("")

    }



    var sendButtonText by remember {

        mutableStateOf("发送验证码")

    }






    Column(


        modifier = Modifier

            .fillMaxSize()

            .verticalScroll(

                rememberScrollState()

            )

            .padding(32.dp),



        horizontalAlignment = Alignment.CenterHorizontally,



        verticalArrangement = Arrangement.Center



    ) {



        Text(

            text = "注册",

            fontSize = 36.sp,

            fontWeight = FontWeight.Bold

        )





        Spacer(

            Modifier.height(40.dp)

        )







        /*
        手机号 + 验证码按钮
         */


        Row(

            modifier = Modifier.fillMaxWidth(),


            verticalAlignment = Alignment.CenterVertically


        ) {



            OutlinedTextField(


                value = phone,


                onValueChange = {


                    phone = it


                },


                label = {


                    Text("手机号")


                },


                singleLine = true,


                keyboardOptions = KeyboardOptions(

                    keyboardType = KeyboardType.Phone

                ),


                modifier = Modifier.weight(1f)


            )






            Spacer(

                Modifier.width(8.dp)

            )






            Button(


                onClick = {


                    if(phone.length != 11){


                        message = "请输入正确手机号"


                        return@Button


                    }




                    scope.launch {


                        try {


                            val result =


                                RetrofitInstance

                                    .verificationCodeApi

                                    .sendCode(phone)





                            message = result.data



                            sendButtonText = "已发送"





                        }catch(e:Exception){



                            message =

                                "发送失败:${e.message}"


                        }


                    }



                },


                modifier = Modifier.height(55.dp)


            ){


                Text(

                    text = sendButtonText,

                    fontSize = 12.sp

                )


            }



        }







        Spacer(

            Modifier.height(16.dp)

        )







        /*
        密码
         */


        OutlinedTextField(


            value = password,


            onValueChange = {


                password = it


            },


            label = {


                Text("密码")


            },


            singleLine = true,


            visualTransformation = PasswordVisualTransformation(),


            keyboardOptions = KeyboardOptions(

                keyboardType = KeyboardType.Password

            ),


            modifier = Modifier.fillMaxWidth()


        )







        Spacer(

            Modifier.height(16.dp)

        )








        /*
        确认密码
         */


        OutlinedTextField(


            value = confirmPassword,


            onValueChange = {


                confirmPassword = it


            },


            label = {


                Text("确认密码")


            },


            singleLine = true,


            visualTransformation = PasswordVisualTransformation(),


            keyboardOptions = KeyboardOptions(

                keyboardType = KeyboardType.Password

            ),


            modifier = Modifier.fillMaxWidth()


        )








        Spacer(

            Modifier.height(16.dp)

        )









        /*
        验证码
         */


        OutlinedTextField(


            value = verificationCode,


            onValueChange = {


                verificationCode = it


            },


            label = {


                Text("验证码")


            },


            singleLine = true,


            keyboardOptions = KeyboardOptions(

                keyboardType = KeyboardType.Number

            ),


            modifier = Modifier.fillMaxWidth()


        )








        Spacer(

            Modifier.height(16.dp)

        )







        /*
        后台返回信息
         */


        if(message.isNotEmpty()){


            Text(

                text = message,


                color = MaterialTheme.colorScheme.primary,


                fontSize = 15.sp


            )


        }








        Spacer(

            Modifier.height(32.dp)

        )









        /*
        完成注册
         */


        Button(


            onClick = {



                if(password != confirmPassword){


                    message = "两次密码不一致"


                    return@Button


                }





                if(

                    phone.isEmpty()
                    ||
                    password.isEmpty()
                    ||
                    verificationCode.isEmpty()

                ){


                    message = "请填写完整信息"


                    return@Button


                }







                scope.launch {



                    try {



                        val result =


                            RetrofitInstance

                                .registerApi

                                .register(



                                    RegisterRequest(


                                        phone = phone,


                                        password = password,


                                        code = verificationCode


                                    )



                                )







                        message = result.data







                    }catch(e:Exception){



                        message =

                            "注册失败:${e.message}"



                    }



                }



            },


            modifier = Modifier

                .fillMaxWidth()

                .height(55.dp)



        ){



            Text(

                text = "完成注册",

                fontSize = 18.sp


            )



        }









        Spacer(

            Modifier.height(80.dp)

        )





    }



}