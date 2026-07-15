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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.activity.compose.BackHandler



//============================
// 用户信息
//============================

data class UserInfo(

    var phone:String="13800138000",

    var name:String="健康用户",

    var avatar:String="👤",

    var age:String="25",

    var gender:String="男",

    var height:String="170",

    var weight:String="65"

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
    val pageStack = remember {

        mutableStateListOf("main")

    }
    fun navigateTo(target:String){

        pageStack.add(target)

        page = target

    }
    //返回上一层
    fun goBack(){

        if(pageStack.size > 1){


            pageStack.removeAt(
                pageStack.size-1
            )


            page = pageStack.last()


        }

    }

    BackHandler {

        goBack()

    }


    when(page){



        "main" -> {


            ProfileMain(

                userInfo=userInfo,

                onClick={

                    navigateTo(it)

                }

            )

        }




        "健康数据"->{


            HealthDataPage(

                onClick={

                    navigateTo(it)

                },

                onBack={

                    goBack()

                }

            )


        }





        "个人信息"->{


            UserInfoPage(

                userInfo=userInfo,

                onEdit={

                    page="编辑资料"

                },

                onBack={

                    page="健康数据"

                }

            )


        }





        "编辑资料"->{


            EditUserPage(

                userInfo=userInfo,

                onSave={

                    userInfo=it

                    page="个人信息"

                }

            )


        }




        else->{


            DetailPage(

                title=page,

                onBack={

                    goBack()

                }

            )


        }


    }



}








//============================
// 个人主页
//============================

@Composable
fun ProfileMain(

    userInfo:UserInfo,

    onClick:(String)->Unit

){



    Column(

        modifier=Modifier
            .fillMaxSize()
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 20.dp,
                bottom = 120.dp
            )
            .verticalScroll(
                rememberScrollState()
            )

    ){



        //头像昵称

        Row(

            verticalAlignment=Alignment.CenterVertically

        ){


            Box(

                modifier=Modifier
                    .size(85.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),

                contentAlignment=Alignment.Center

            ){


                Text(

                    text=userInfo.avatar,

                    fontSize=40.sp

                )


            }



            Spacer(
                Modifier.width(20.dp)
            )



            Column{


                Text(

                    userInfo.name,

                    fontSize=24.sp,

                    fontWeight=FontWeight.Bold

                )



                Text(

                    "ID:${userInfo.phone}",

                    color=Color.Gray

                )


            }


        }





        Spacer(
            Modifier.height(30.dp)
        )




        //====================
        //健康数据入口
        //====================






        ProfileItem(

            "🏥 健康数据",

            {

                onClick("健康数据")

            }

        )












        //====================
        //其他功能
        //====================





        val list=listOf(

            "🏆 我的成就",

            "🏃 运动记录",

            "🆘 医疗急救卡",

            "💚 健康关怀",

            "📊 运动周报",

            "🔄 检查更新",

            "ℹ️ 关于"

        )




        list.forEach{


            ProfileItem(

                it,

                {

                    onClick(it)

                }

            )


        }



    }


}








//============================
// 健康数据页面
//============================


@Composable
fun HealthDataPage(

    onClick:(String)->Unit,

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

            "健康数据",

            fontSize=26.sp,

            fontWeight=FontWeight.Bold

        )



        Spacer(
            Modifier.height(15.dp)
        )




        val list=listOf(

            "个人信息",

            "🚶 步数",

            "📏 距离",

            "🔥 热量",

            "📅 打卡记录",

            "😴 睡眠",

            "❤️ 心率",

            "😰 压力"

        )



        list.forEach{


            ProfileItem(

                it,

                {

                    onClick(it)

                }

            )


        }



    }


}








//============================
// 通用列表
//============================

@Composable
fun ProfileItem(

    title:String,

    onClick:()->Unit

){


    Card(

        modifier=Modifier

            .fillMaxWidth()

            .padding(vertical=5.dp)

            .clickable {

                onClick()

            }

    ){


        Row(

            modifier=Modifier
                .fillMaxWidth()
                .padding(18.dp),


            horizontalArrangement=
                Arrangement.SpaceBetween


        ){


            Text(

                title,

                fontSize=18.sp

            )



            Text(

                ">",

                color=Color.Gray

            )


        }


    }


}








//============================
//个人信息查看
//============================


@Composable
fun UserInfoPage(

    userInfo:UserInfo,

    onEdit:()->Unit,

    onBack:()->Unit

){


    Column(

        Modifier
            .fillMaxSize()
            .padding(20.dp)

    ){



        Button(
            onClick=onBack
        ){

            Text("返回")

        }



        Text(

            "个人信息",

            fontSize=26.sp

        )



        Spacer(
            Modifier.height(20.dp)
        )


        Text("头像：${userInfo.avatar}")

        Text("昵称：${userInfo.name}")

        Text("手机号：${userInfo.phone}")

        Text("年龄：${userInfo.age}")

        Text("性别：${userInfo.gender}")

        Text("身高：${userInfo.height}cm")

        Text("体重：${userInfo.weight}kg")




        Button(

            onClick=onEdit

        ){

            Text("修改资料")

        }


    }


}








//============================
//编辑资料
//============================


@Composable
fun EditUserPage(

    userInfo:UserInfo,

    onSave:(UserInfo)->Unit

){


    var name by remember {
        mutableStateOf(userInfo.name)
    }


    var avatar by remember {
        mutableStateOf(userInfo.avatar)
    }



    Column(

        Modifier
            .fillMaxSize()
            .padding(20.dp)

    ){



        Text(

            "修改资料",

            fontSize=26.sp

        )



        Row{


            listOf(
                "👤",
                "😀",
                "👨",
                "👩",
                "🏃"
            ).forEach{


                Text(

                    it,

                    fontSize=35.sp,

                    modifier=Modifier
                        .padding(8.dp)
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



        Button(

            onClick={

                onSave(

                    userInfo.copy(

                        name=name,

                        avatar=avatar

                    )

                )

            }

        ){

            Text("保存")

        }


    }


}








//============================
//详情页面
//============================


@Composable
fun DetailPage(

    title:String,

    onBack:()->Unit

){


    Column(

        Modifier
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



        Text(

            when(title){


                "🚶 步数" ->
                    "今日步数：8560步\n目标：10000步"



                "📏 距离" ->
                    "今日距离：6.8km"



                "🔥 热量" ->
                    "今日消耗：420kcal"



                "📅 打卡记录" ->
                    "连续打卡15天"



                "😴 睡眠" ->
                    "睡眠7.5小时\n评分88"



                "❤️ 心率" ->
                    "平均心率75bpm"



                "😰 压力" ->
                    "压力指数35"



                "🏆 我的成就" ->

                    """
        🏆 我的成就
        
        已获得勋章：
        
        🥇 连续运动达人
        
        🥈 步数挑战达人
        
        🥉 睡眠管理达人
        
        
        健康积分：
        1280 分
        
        累计运动：
        68 次
        
        连续打卡：
        15 天
        """.trimIndent()



                //运动记录

                "🏃 运动记录" ->

                    """
        🏃 运动记录
        
        今日运动：
        
        跑步 3.2 km
        
        时间：
        35 分钟
        
        消耗：
        260 kcal
        
        
        本周：
        
        运动次数：
        5 次
        
        总距离：
        28.6 km
        
        总消耗：
        1800 kcal
        """.trimIndent()



                //医疗急救卡

                "🆘 医疗急救卡" ->

                    """
        🆘 医疗急救卡
        
        姓名：
        健康用户
        
        手机：
        13800138000
        
        血型：
        未设置
        
        紧急联系人：
        未设置
        
        过敏史：
        无
        """.trimIndent()



                //健康关怀

                "💚 健康关怀" ->

                    """
        💚 健康关怀
        
        今日健康评分：
        
        92 分
        
        
        健康建议：
        
        ✓ 保持每天运动
        
        ✓ 保证7小时睡眠
        
        ✓ 注意饮水
        """.trimIndent()



                //运动周报

                "📊 运动周报" ->

                    """
        📊 运动周报
        
        本周统计：
        
        
        步数：
        56000 步
        
        距离：
        42.5 km
        
        消耗：
        3200 kcal
        
        运动：
        5 次
        
        睡眠：
        平均7.5小时
        """.trimIndent()




                //检查更新

                "🔄 检查更新" ->

                    """
        🔄 检查更新
        
        当前版本：
        
        v1.0.0
        
        已是最新版本
        """.trimIndent()



                //关于

                "ℹ️ 关于" ->

                    """
        ℹ️ 关于
        
        健康手环 App
        
        版本：
        v1.0.0
        
        用于记录健康、
        运动和生活数据。
        """.trimIndent()



                else ->

                    "功能开发中"



            }

        )


    }


}