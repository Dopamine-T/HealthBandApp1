package com.example.healthbandapp.ui.theme.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.healthbandapp.api.RetrofitInstance
import com.example.healthbandapp.model.UserProfile
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController
) {
    val context = LocalContext.current

    // 1. 获取全局登录状态和当前账号
    val globalPrefs = context.getSharedPreferences("global_prefs", Context.MODE_PRIVATE)
    var isLoggedIn by remember { mutableStateOf(globalPrefs.getBoolean("is_logged_in", false)) }
    var currentAccount by remember { mutableStateOf(globalPrefs.getString("current_account", "") ?: "") }

    // 2. 获取当前账号的专属存储空间
    val userPrefsName = if (currentAccount.isNotEmpty()) "user_profile_$currentAccount" else "default_prefs"
    val userPrefs = context.getSharedPreferences(userPrefsName, Context.MODE_PRIVATE)

    // 从服务器获取用户信息

    var userProfile by remember {

        mutableStateOf<UserProfile?>(null)

    }



    LaunchedEffect(isLoggedIn) {


        if(isLoggedIn){


            try {


                val response =

                    RetrofitInstance.userApi.getUserProfile()



                if(response.code == 200){


                    userProfile = response.data


                }


            }catch(e:Exception){


                e.printStackTrace()


            }


        }


    }

    // 控制修改昵称弹窗的状态
    var showEditDialog by remember { mutableStateOf(false) }
    var tempNickName by remember { mutableStateOf("") }

    // 检查更新的弹窗变量
    var showUpdateSheet by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                userPrefs.edit().putString("avatar_uri", uri.toString()).apply()

            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    // ============================
    // 主页面内容
    // ============================
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(Modifier.height(24.dp))

        // 顶部头像昵称卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (!isLoggedIn) { navController.navigate("login") }
                    }
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .clickable(enabled = isLoggedIn) { launcher.launch(arrayOf("image/*")) }
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                )

                {
                    if(userProfile != null){


                        AsyncImage(

                            model = userProfile!!.avatar,

                            contentDescription = "头像",

                            modifier = Modifier.fillMaxSize(),

                            contentScale = ContentScale.Crop

                        )


                    }else{


                        Icon(

                            imageVector = Icons.Default.Person,

                            contentDescription = "默认头像",

                            modifier = Modifier.size(40.dp),

                            tint = Color.White

                        )


                    }
                }

                Spacer(Modifier.width(20.dp))

                Column(modifier = Modifier.weight(1f)) {
                    if (isLoggedIn) {
                        Text(

                            text = userProfile?.username ?: "加载中...",

                            modifier = Modifier.clickable {

                                tempNickName =
                                    userProfile?.username ?: ""

                                showEditDialog = true

                            },

                            fontSize = 22.sp,

                            fontWeight = FontWeight.Bold

                        )
                        Spacer(Modifier.height(4.dp))
                        Text(

                            text =
                                "手机号: ${userProfile?.phone ?: ""}",

                            color = Color.Gray,

                            fontSize = 14.sp

                        )
                    } else {
                        Text(text = "点击登录", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))
                        Text(text = "登录后享受更多服务", color = Color.Gray, fontSize = 14.sp)
                    }
                }

                if (!isLoggedIn) {
                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = "箭头",
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        // ============================ 之前的错误在这里：多了一个 } ============================

        // 功能列表区域 (第一组)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column {

                ProfileCard(title = "🏃 运动记录", onClick = { navController.navigate("sportRecord") })
                HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))

                ProfileCard(title = "📊 运动周报", onClick = { navController.navigate("weekly") })
            }
        }

        // 其他设置区域 (第二组)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column {
                ProfileCard(title = "🔄 检查更新", onClick = { showUpdateSheet = true })
                HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
                ProfileCard(title = "ℹ️ 关于", onClick = { navController.navigate("about") })

                if (isLoggedIn) {
                    HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
                    ProfileCard(
                        title = "🚪 退出登录",
                        onClick = {
                            globalPrefs.edit()
                                .putBoolean("is_logged_in", false)
                                .remove("current_account")
                                .apply()

                            isLoggedIn = false
                            currentAccount = ""
                            isLoggedIn = false
                            currentAccount = ""
                            userProfile = null
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    } // <-- 这里是 Column 的结束括号

    // ============================
    // 弹窗类组件 (必须放在 Column 外层，或者不加约束直接写在外面)
    // ============================

    // 4. 优化后的修改昵称弹窗
    if (showEditDialog) {
        Dialog(onDismissRequest = { showEditDialog = false }) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "修改昵称",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "请输入您的新昵称，最多12个字",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(Modifier.height(20.dp))

                    OutlinedTextField(
                        value = tempNickName,
                        onValueChange = { if (it.length <= 12) tempNickName = it },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        placeholder = { Text("请输入昵称") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { showEditDialog = false },
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("取消", color = MaterialTheme.colorScheme.onSurface)
                        }

                        Button(
                            onClick = {
                                if (tempNickName.isNotBlank()) {
                                    userPrefs.edit().putString("user_nickname", tempNickName).apply()

                                }
                                showEditDialog = false
                            },
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("保存", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // 底部检查更新弹窗
    if (showUpdateSheet) {
        ModalBottomSheet(
            onDismissRequest = { showUpdateSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
            ) {
                Text(text = "🔄 检查更新", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(20.dp))
                Text(text = "当前版本：v1.0.0")
                Spacer(Modifier.height(10.dp))
                Text(text = "已经是最新版本")
                Spacer(Modifier.height(30.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { showUpdateSheet = false }
                ) { Text("确定") }
                Spacer(Modifier.height(30.dp))
            }
        }
    }
} // <-- 这里才是 ProfileScreen 函数的真正结束括号

@Composable
fun ProfileCard(
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.bodyLarge, fontSize = 16.sp)
        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = "箭头",
            tint = Color.Gray,
            modifier = Modifier.size(14.dp)
        )
    }
}