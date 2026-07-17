package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

// 数据类
data class UserInfo(
    var phone: String = "13800138000",
    var name: String = "健康用户",
    var age: String = "25",
    var gender: String = "男",
    var height: String = "170",
    var weight: String = "65"
)

@Composable
fun ProfileScreen() {
    var userInfo by remember { mutableStateOf(UserInfo()) }
    var page by remember { mutableStateOf("main") }

    when (page) {
        "main" -> ProfileMain(userInfo) { page = it }
        "健康报告" -> HealthReportPage(userInfo) { page = "main" }
        "健康数据" -> HealthDataPage({ page = it }, { page = "main" })
        "个人信息" -> UserInfoPage(userInfo, { page = "编辑资料" }, { page = "健康数据" })
        "编辑资料" -> EditUserPage(userInfo, { userInfo = it; page = "个人信息" })
        else -> DetailPage(title = page) { page = "main" }
    }
}

//================================
// 1. 个人主页
//================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileMain(userInfo: UserInfo, onClick: (String) -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("我的", fontWeight = FontWeight.Bold) }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // 头部渐变卡片
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .background(Brush.linearGradient(listOf(Color(0xFF1A237E), Color(0xFF3949AB))))
                        .padding(24.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "头像", tint = Color.White, modifier = Modifier.size(64.dp))
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(userInfo.name, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            Text("ID: ${userInfo.phone}", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // 功能列表
            ProfileListItem("综合健康报告", Icons.Filled.HealthAndSafety, Color(0xFF2E7D32)) { onClick("健康报告") }
            ProfileListItem("健康数据", Icons.Filled.MonitorHeart, Color(0xFF1565C0)) { onClick("健康数据") }

            Spacer(Modifier.height(16.dp))
            Text("更多功能", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp, bottom = 8.dp))

            val otherList = listOf(
                "运动记录" to Icons.AutoMirrored.Filled.DirectionsRun,
                "医疗急救卡" to Icons.Filled.WarningAmber,
                "隐私管理" to Icons.Filled.VerifiedUser,
                "设置" to Icons.Filled.Settings
            )
            otherList.forEach { (title, icon) ->
                ProfileListItem(title, icon, Color(0xFF5C6BC0)) { onClick(title) }
            }

            Spacer(Modifier.height(60.dp))
        }
    }
}

//================================
// 2. 综合健康报告 (新增核心功能)
//================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthReportPage(userInfo: UserInfo, onBack: () -> Unit) {
    // 计算 BMI
    val heightM = userInfo.height.toFloatOrNull()?.div(100) ?: 1.7f
    val weightKg = userInfo.weight.toFloatOrNull() ?: 65f
    val bmi = weightKg / (heightM * heightM)
    val bmiStr = String.format(Locale.getDefault(), "%.1f", bmi)

    val bmiStatus = when {
        bmi < 18.5 -> "偏瘦" to Color(0xFFFFA726)
        bmi in 18.5..23.9 -> "正常" to Color(0xFF2E7D32)
        bmi in 24.0..27.9 -> "超重" to Color(0xFFFFA726)
        else -> "肥胖" to Color(0xFFE53935)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("综合健康报告", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // 健康评分卡 (渐变背景)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Column(
                    modifier = Modifier
                        .background(Brush.linearGradient(listOf(Color(0xFF006064), Color(0xFF26A69A))))
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("今日健康评分", color = Color.White.copy(alpha = 0.9f))
                    Text("92", fontSize = 56.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("身体状态良好，请继续保持", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                }
            }

            Spacer(Modifier.height(20.dp))

            // BMI 卡片
            DetailCard("身体质量指数 (BMI)", Icons.Filled.Height, Color(0xFF1565C0)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("您的 BMI: $bmiStr", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("现代医学标准: 18.5 - 23.9 为正常", fontSize = 12.sp, color = Color.Gray)
                    }
                    Text(bmiStatus.first, color = bmiStatus.second, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }

            Spacer(Modifier.height(20.dp))

            // 医学健康建议
            DetailCard("AI 健康干预建议", Icons.Filled.Psychology, Color(0xFF6A1B9A)) {
                MedicalAdviceRow("心血管风险", "低风险", "静息心率 68 BPM，处于理想范围。建议保持每周3次有氧运动。")
                MedicalAdviceRow("代谢健康", "良好", "BMI $bmiStr 属于 ${bmiStatus.first}。注意控制每日碳水摄入。")
                MedicalAdviceRow("睡眠恢复", "优秀", "深睡比例达标。睡前1小时建议远离电子屏幕。")
                MedicalAdviceRow("压力管理", "正常", "HRV 指标稳定。可尝试每日5分钟正念呼吸。")
            }

            Spacer(Modifier.height(60.dp))
        }
    }
}

//================================
// 3. 健康数据列表页
//================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthDataPage(onClick: (String) -> Unit, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("健康数据", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(20.dp)
        ) {
            ProfileListItem("个人信息", Icons.Filled.AccountCircle, Color(0xFF1565C0)) { onClick("个人信息") }
            ProfileListItem("步数", Icons.AutoMirrored.Filled.DirectionsWalk, Color(0xFF2E7D32)) { onClick("步数") }
            ProfileListItem("热量", Icons.Filled.LocalFireDepartment, Color(0xFFE65100)) { onClick("热量") }
            ProfileListItem("睡眠", Icons.Filled.NightsStay, Color(0xFF5C6BC0)) { onClick("睡眠") }
            ProfileListItem("心率", Icons.Filled.Favorite, Color(0xFFE53935)) { onClick("心率") }
            ProfileListItem("压力", Icons.Filled.Psychology, Color(0xFF6A1B9A)) { onClick("压力") }
        }
    }
}

//================================
// 4. 个人信息查看页
//================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoPage(userInfo: UserInfo, onEdit: () -> Unit, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("个人信息", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp)
        ) {
            DetailCard("基础资料", Icons.Filled.AccountCircle, Color(0xFF1565C0)) {
                InfoRow("姓名", userInfo.name)
                InfoRow("手机号", userInfo.phone)
                InfoRow("年龄", "${userInfo.age} 岁")
                InfoRow("性别", userInfo.gender)
                InfoRow("身高", "${userInfo.height} cm")
                InfoRow("体重", "${userInfo.weight} kg")
            }

            Spacer(Modifier.height(20.dp))
            Button(onClick = onEdit, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp)) {
                Icon(Icons.Filled.Edit, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("修改资料")
            }
        }
    }
}

//================================
// 5. 编辑资料页
//================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserPage(userInfo: UserInfo, onSave: (UserInfo) -> Unit) {
    var name by remember { mutableStateOf(userInfo.name) }
    var age by remember { mutableStateOf(userInfo.age) }
    var height by remember { mutableStateOf(userInfo.height) }
    var weight by remember { mutableStateOf(userInfo.weight) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("编辑资料", fontWeight = FontWeight.Bold) }) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp)
        ) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("昵称") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("年龄") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = height, onValueChange = { height = it }, label = { Text("身高") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("体重") }, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { onSave(userInfo.copy(name = name, age = age, height = height, weight = weight)) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) { Text("保存") }
        }
    }
}

//================================
// 6. 通用详情页
//================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(title: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(20.dp)
        ) {
            when (title) {
                "步数" -> {
                    DetailCard("今日步数", Icons.AutoMirrored.Filled.DirectionsWalk, Color(0xFF2E7D32)) {
                        Text("8,560 步", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        Text("目标: 10,000 步", color = Color.Gray)
                        Spacer(Modifier.height(10.dp))
                        LinearProgressIndicator(
                            progress = { 0.85f },
                            modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape)
                        )
                    }
                }
                "心率" -> {
                    DetailCard("今日心率", Icons.Filled.Favorite, Color(0xFFE53935)) {
                        InfoRow("平均心率", "75 BPM")
                        InfoRow("最高心率", "132 BPM")
                        InfoRow("最低心率", "58 BPM")
                    }
                }
                "睡眠" -> {
                    DetailCard("昨晚睡眠", Icons.Filled.NightsStay, Color(0xFF5C6BC0)) {
                        InfoRow("总时长", "7小时35分钟")
                        InfoRow("深睡比例", "28%")
                        InfoRow("睡眠评分", "89 分")
                    }
                }
                "热量" -> {
                    DetailCard("热量消耗", Icons.Filled.LocalFireDepartment, Color(0xFFE65100)) {
                        Text("420 kcal", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        Text("基础代谢: 1,500 kcal", color = Color.Gray)
                    }
                }
                "压力" -> {
                    DetailCard("压力监测", Icons.Filled.Psychology, Color(0xFF6A1B9A)) {
                        Text("35", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        Text("压力水平: 低", color = Color.Gray)
                    }
                }
                "运动记录" -> {
                    DetailCard("运动记录", Icons.AutoMirrored.Filled.DirectionsRun, Color(0xFF1565C0)) {
                        InfoRow("今日跑步", "3.2 km")
                        InfoRow("运动时长", "35 分钟")
                        InfoRow("消耗热量", "260 kcal")
                    }
                }
                "医疗急救卡" -> {
                    DetailCard("医疗急救卡", Icons.Filled.WarningAmber, Color(0xFFE53935)) {
                        InfoRow("血型", "未设置")
                        InfoRow("紧急联系人", "未设置")
                        InfoRow("过敏史", "无")
                    }
                }
                else -> {
                    DetailCard(title, Icons.Filled.History, Color(0xFF5C6BC0)) {
                        Text("功能开发中...")
                    }
                }
            }
        }
    }
}

//================================
// 通用组件库
//================================
@Composable
fun ProfileListItem(title: String, icon: ImageVector, iconTint: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(36.dp).clip(CircleShape).background(iconTint.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) { Icon(icon, contentDescription = title, tint = iconTint, modifier = Modifier.size(20.dp)) }

            Spacer(Modifier.width(16.dp))
            Text(title, fontSize = 16.sp, modifier = Modifier.weight(1f))
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
        }
    }
}

@Composable
fun DetailCard(title: String, icon: ImageVector, iconTint: Color, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(32.dp).clip(CircleShape).background(iconTint.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) { Icon(icon, contentDescription = title, tint = iconTint, modifier = Modifier.size(20.dp)) }
                Spacer(Modifier.width(10.dp))
                Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(15.dp))
            content()
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontSize = 15.sp)
        Text(value, fontSize = 15.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun MedicalAdviceRow(category: String, status: String, advice: String) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(category, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(status, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
        Spacer(Modifier.height(4.dp))
        Text(advice, fontSize = 13.sp, color = Color.Gray, lineHeight = 18.sp)
    }
}