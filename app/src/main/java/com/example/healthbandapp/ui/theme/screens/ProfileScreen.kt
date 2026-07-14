package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//==============================
// 用户信息
//==============================

data class UserInfo(

    var phone:String = "13800138000",

    //昵称
    var name:String = "健康用户",

    //头像
    var avatar:String = "👤",

    var age:String = "25",

    var gender:String = "男",

    var height:String = "170",

    var weight:String = "65"

)





@Composable
fun ProfileScreen(){


    var userInfo by remember {

        mutableStateOf(
            UserInfo()
        )

    }



    var page by remember {

        mutableStateOf("main")

    }



    when(page){

        //主页

        "main" -> {


            ProfileMain(

                userInfo=userInfo,

                onClick={

                    page=it

                }

            )


        }



        //个人信息查看

        "info" -> {


            UserInfoScreen(

                userInfo=userInfo,

                onEdit={

                    page="edit"

                },

                onBack={

                    page="main"

                }

            )


        }



        //编辑

        "edit" -> {


            EditUserInfoScreen(

                userInfo=userInfo,

                onSave={

                    userInfo=it

                    page="info"

                }

            )


        }



        //健康详情

        else->{


            HealthDetailScreen(

                title=page,

                onBack={

                    page="main"

                }

            )


        }


    }



}








//==============================
// 个人主页
//==============================


@Composable
fun ProfileMain(

    userInfo:UserInfo,

    onClick:(String)->Unit

){


    Column(

        modifier=Modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment=Alignment.CenterHorizontally

    ){



        //头像

        Box(

            modifier=Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.LightGray),

            contentAlignment=Alignment.Center

        ){

            Text(

                text=userInfo.avatar,

                fontSize=50.sp

            )

        }




        Spacer(
            Modifier.height(15.dp)
        )



        //昵称

        Text(

            text=userInfo.name,

            fontSize=24.sp,

            fontWeight=FontWeight.Bold

        )



        //ID

        Text(

            text="ID:${userInfo.phone}",

            color=Color.Gray

        )



        Spacer(
            Modifier.height(25.dp)
        )




        val list=listOf(

            "info" to "👤 个人信息",

            "🚶 步数" to "🚶 步数",

            "📏 距离" to "📏 距离",

            "🔥 热量" to "🔥 热量",

            "📅 打卡记录" to "📅 打卡记录",

            "😴 睡眠" to "😴 睡眠",

            "❤️ 心率" to "❤️ 心率",

            "😰 压力" to "😰 压力"

        )



        list.forEach {item->



            Card(

                modifier=Modifier

                    .fillMaxWidth()

                    .padding(vertical=5.dp)

                    .clickable {


                        onClick(item.first)


                    }

            ){



                Row(

                    modifier=Modifier
                        .fillMaxWidth()
                        .padding(18.dp),

                    horizontalArrangement =
                        Arrangement.SpaceBetween

                ){


                    Text(

                        item.second,

                        fontSize=18.sp

                    )



                    Text(

                        "查看>",

                        color=Color.Gray

                    )


                }


            }


        }


    }


}








//==============================
// 个人信息页面
//==============================


@Composable
fun UserInfoScreen(

    userInfo:UserInfo,

    onEdit:()->Unit,

    onBack:()->Unit

){


    Column(

        modifier=Modifier
            .fillMaxSize()
            .padding(20.dp)

    ){


        Button(
            onClick=onBack
        ){

            Text("返回")

        }



        Spacer(
            Modifier.height(20.dp)
        )



        Text(

            "个人信息",

            fontSize=26.sp,

            fontWeight=FontWeight.Bold

        )



        Spacer(
            Modifier.height(20.dp)
        )



        Text("头像：${userInfo.avatar}")

        Text("昵称：${userInfo.name}")

        Text("手机号：${userInfo.phone}")

        Text("年龄：${userInfo.age}岁")

        Text("性别：${userInfo.gender}")

        Text("身高：${userInfo.height}cm")

        Text("体重：${userInfo.weight}kg")



        Spacer(
            Modifier.height(30.dp)
        )



        Button(

            onClick=onEdit,

            modifier=Modifier.fillMaxWidth()

        ){

            Text("修改资料")

        }



    }


}








//==============================
// 编辑个人资料
//==============================


@Composable
fun EditUserInfoScreen(

    userInfo:UserInfo,

    onSave:(UserInfo)->Unit

){



    var avatar by remember {

        mutableStateOf(userInfo.avatar)

    }



    var name by remember {

        mutableStateOf(userInfo.name)

    }



    var phone by remember {

        mutableStateOf(userInfo.phone)

    }



    var age by remember {

        mutableStateOf(userInfo.age)

    }



    var gender by remember {

        mutableStateOf(userInfo.gender)

    }



    var height by remember {

        mutableStateOf(userInfo.height)

    }



    var weight by remember {

        mutableStateOf(userInfo.weight)

    }




    Column(

        modifier=Modifier
            .fillMaxSize()
            .padding(20.dp)

    ){



        Text(

            "修改个人资料",

            fontSize=26.sp

        )



        Spacer(
            Modifier.height(15.dp)
        )



        Text("选择头像")



        Row{


            listOf(
                "👤",
                "😀",
                "👨",
                "👩",
                "🧑‍⚕️",
                "🏃"
            ).forEach{


                Text(

                    text=it,

                    fontSize=35.sp,

                    modifier=Modifier
                        .padding(6.dp)
                        .clickable {

                            avatar=it

                        }

                )


            }


        }




        OutlinedTextField(

            value=name,

            onValueChange={name=it},

            label={Text("昵称")}

        )



        OutlinedTextField(

            value=phone,

            onValueChange={phone=it},

            label={Text("手机号")}

        )



        OutlinedTextField(

            value=age,

            onValueChange={age=it},

            label={Text("年龄")}

        )



        OutlinedTextField(

            value=gender,

            onValueChange={gender=it},

            label={Text("性别")}

        )



        OutlinedTextField(

            value=height,

            onValueChange={height=it},

            label={Text("身高(cm)")}

        )



        OutlinedTextField(

            value=weight,

            onValueChange={weight=it},

            label={Text("体重(kg)")}

        )



        Spacer(
            Modifier.height(20.dp)
        )



        Button(

            onClick={


                onSave(

                    UserInfo(

                        phone=phone,

                        name=name,

                        avatar=avatar,

                        age=age,

                        gender=gender,

                        height=height,

                        weight=weight

                    )

                )


            },

            modifier=Modifier.fillMaxWidth()

        ){

            Text("保存")

        }



    }


}








//==============================
// 健康数据详情
//==============================


@Composable
fun HealthDetailScreen(

    title:String,

    onBack:()->Unit

){


    Column(

        modifier=Modifier
            .fillMaxSize()
            .padding(20.dp)

    ){


        Button(
            onClick=onBack
        ){

            Text("返回")

        }



        Spacer(
            Modifier.height(20.dp)
        )



        Text(

            title,

            fontSize=26.sp,

            fontWeight=FontWeight.Bold

        )



        Spacer(
            Modifier.height(20.dp)
        )



        when(title){


            "🚶 步数"->{

                Text("今日步数：8560步")
                Text("目标：10000步")

            }



            "📏 距离"->{

                Text("今日距离：6.8km")

            }



            "🔥 热量"->{

                Text("今日消耗：420kcal")

            }



            "📅 打卡记录"->{

                Text("连续打卡：15天")

            }



            "😴 睡眠"->{

                Text("睡眠时间：7.5小时")
                Text("睡眠评分：88")

            }



            "❤️ 心率"->{

                Text("平均心率：75bpm")
                Text("最高心率：120bpm")

            }



            "😰 压力"->{

                Text("压力指数：35")
                Text("状态：正常")

            }


        }


    }


}