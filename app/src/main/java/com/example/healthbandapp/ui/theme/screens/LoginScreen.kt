package com.example.healthbandapp.ui.theme.screens


import android.content.Context

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController


import com.example.healthbandapp.api.RetrofitInstance
import com.example.healthbandapp.model.LoginRequest


import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(

    navController: NavController

){



    var account by remember {

        mutableStateOf("")

    }



    var password by remember {

        mutableStateOf("")

    }



    var passwordVisible by remember {

        mutableStateOf(false)

    }




    // 显示返回信息

    var message by remember {

        mutableStateOf("")

    }




    val context = LocalContext.current


    val scope = rememberCoroutineScope()







    Scaffold(


        topBar = {


            TopAppBar(

                title = {

                    Text(

                        "登录",

                        fontWeight = FontWeight.Bold

                    )

                }

            )

        }



    ){ padding ->





        Column(


            modifier = Modifier

                .fillMaxSize()

                .padding(padding)

                .padding(32.dp),



            horizontalAlignment = Alignment.CenterHorizontally,



            verticalArrangement = Arrangement.Center



        ){





            Text(

                text = "HealthBand",

                fontSize = 40.sp,

                fontWeight = FontWeight.Bold

            )





            Spacer(

                Modifier.height(40.dp)

            )







            OutlinedTextField(


                value = account,


                onValueChange = {

                    account = it

                },


                label = {


                    Text("手机号")

                },


                singleLine = true,


                keyboardOptions = KeyboardOptions(

                    keyboardType = KeyboardType.Phone

                ),



                modifier = Modifier.fillMaxWidth()



            )






            Spacer(

                Modifier.height(16.dp)

            )







            OutlinedTextField(


                value = password,


                onValueChange = {


                    password = it

                },


                label = {


                    Text("密码")

                },


                singleLine = true,



                visualTransformation =


                    if(passwordVisible)


                        VisualTransformation.None


                    else


                        PasswordVisualTransformation(),





                trailingIcon = {



                    IconButton(


                        onClick = {


                            passwordVisible =

                                !passwordVisible


                        }


                    ){



                        Icon(


                            imageVector =


                                if(passwordVisible)


                                    Icons.Default.Visibility


                                else


                                    Icons.Default.VisibilityOff,



                            contentDescription = null


                        )



                    }



                },



                keyboardOptions = KeyboardOptions(

                    keyboardType = KeyboardType.Password

                ),



                modifier = Modifier.fillMaxWidth()



            )








            Spacer(

                Modifier.height(30.dp)

            )








            Button(


                onClick = {



                    if(

                        account.isBlank()

                        ||

                        password.isBlank()

                    ){



                        message =

                            "请输入手机号和密码"



                        return@Button


                    }








                    scope.launch {



                        try {





                            val result =


                                RetrofitInstance


                                    .loginApi


                                    .login(


                                        LoginRequest(


                                            phone = account,


                                            password = password


                                        )


                                    )









                            if(result.code == 200){





                                val token = result.data







                                // 保存登录状态


                                val prefs =


                                    context.getSharedPreferences(


                                        "global_prefs",


                                        Context.MODE_PRIVATE


                                    )






                                prefs.edit()


                                    .putBoolean(


                                        "is_logged_in",


                                        true


                                    )



                                    .putString(


                                        "token",


                                        token


                                    )



                                    .putString(


                                        "current_account",


                                        account


                                    )



                                    .apply()







                                // 测试显示JWT


                                message =


                                    "登录成功\n\nJWT:\n$token"





                                /*

                                测试完成后打开


                                navController.navigate(
                                    "profile"
                                ){

                                    popUpTo("login"){

                                        inclusive=true

                                    }

                                }


                                */





                            }else{



                                message =

                                    result.message



                            }





                        }catch(e:Exception){





                            message =


                                "登录失败:${e.message}"





                        }



                    }



                },



                modifier = Modifier


                    .fillMaxWidth()


                    .height(50.dp)



            ){



                Text(


                    "登录",


                    fontSize = 18.sp


                )


            }









            Spacer(

                Modifier.height(20.dp)

            )








            if(message.isNotEmpty()){



                Text(


                    text = message,


                    color =

                        MaterialTheme.colorScheme.primary,


                    fontSize = 14.sp



                )



            }









            Spacer(

                Modifier.height(20.dp)

            )








            Row(


                modifier = Modifier.fillMaxWidth(),


                horizontalArrangement = Arrangement.SpaceBetween


            ){






                TextButton(



                    onClick = {



                        navController.navigate(

                            "verificationCode"

                        )


                    }



                ){



                    Text("新用户注册")

                }







                TextButton(



                    onClick = {



                    }


                ){



                    Text("忘记密码?")

                }




            }





        }






    }




}