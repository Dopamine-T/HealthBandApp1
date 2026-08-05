package com.example.healthbandapp.ui.theme.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

//=======================
// 通用图标底色组件
//=======================
@Composable
fun IconBox(icon: ImageVector, tint: Color, bgColor: Color) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
    }
}

//=======================
// 心率 (渐变主卡片)
//=======================
@Composable
fun HeartCard(
    navController: NavHostController,
    heartRate: Int = 82,
    status: String = "正常"
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = "查看心率详情") { navController.navigate("heartRate") },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier.background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFB71C1C), Color(0xFFE53935))
                )
            )
        ) {
            Column(Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconBox(Icons.Filled.Favorite, Color.White, Color.White.copy(alpha = 0.2f))
                    Spacer(Modifier.width(12.dp))
                    Text(
                        "心率",
                        modifier = Modifier.weight(1f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "查看详情", tint = Color.White.copy(alpha = 0.7f))
                }
                Spacer(Modifier.height(16.dp))
                Text("实时心率：$heartRate BPM", color = Color.White.copy(alpha = 0.9f), fontSize = 15.sp)
                Text("状态：$status", color = Color.White.copy(alpha = 0.9f), fontSize = 15.sp)
            }
        }
    }
}

//=======================
// 血氧 (浅青背景)
//=======================
@Composable
fun OxygenCard(
    navController: NavHostController,
    oxygenLevel: Int = 98,
    status: String = "优秀"
) {
    val themeColor = Color(0xFF006064)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = "查看血氧详情") { navController.navigate("oxygen") },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconBox(Icons.Filled.WaterDrop, themeColor, themeColor.copy(alpha = 0.1f))
                Spacer(Modifier.width(12.dp))
                Text("血氧", modifier = Modifier.weight(1f), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = themeColor)
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "查看详情", tint = themeColor.copy(alpha = 0.5f))
            }
            Spacer(Modifier.height(16.dp))
            Text("血氧饱和度：$oxygenLevel%", color = themeColor.copy(alpha = 0.8f), fontSize = 15.sp)
            Text("状态：$status", color = themeColor, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }
    }
}

//=======================
// 睡眠 (浅紫背景) - 重命名避免冲突
//=======================
@Composable
fun SleepSummaryCard(
    navController: NavHostController,
    sleepDuration: String = "7小时35分钟",
    sleepScore: Int = 89
) {
    val themeColor = Color(0xFF6A1B9A)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = "查看睡眠详情") { navController.navigate("sleep") },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5))
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconBox(Icons.Filled.Bedtime, themeColor, themeColor.copy(alpha = 0.1f))
                Spacer(Modifier.width(12.dp))
                Text("睡眠", modifier = Modifier.weight(1f), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = themeColor)
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "查看详情", tint = themeColor.copy(alpha = 0.5f))
            }
            Spacer(Modifier.height(16.dp))
            Text("昨晚睡眠：$sleepDuration", color = themeColor.copy(alpha = 0.8f), fontSize = 15.sp)
            Text("睡眠评分：$sleepScore", color = themeColor, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }
    }
}

//=======================
// HRV & 体温 (浅橙背景)
//=======================
@Composable
fun HrvCard(
    navController: NavHostController,
    hrvValue: Int = 65,
    temperature: Float = 36.5f,
    stressLevel: String = "低"
) {
    val themeColor = Color(0xFFE65100)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = "查看体温与HRV详情") { navController.navigate("hrv") },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconBox(Icons.Filled.Thermostat, themeColor, themeColor.copy(alpha = 0.1f))
                Spacer(Modifier.width(12.dp))
                Text(" HRV", modifier = Modifier.weight(1f), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = themeColor)
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "查看详情", tint = themeColor.copy(alpha = 0.5f))
            }
            Spacer(Modifier.height(16.dp))
            Text("HRV：$hrvValue ms", color = themeColor.copy(alpha = 0.8f), fontSize = 15.sp)

            Text("压力：$stressLevel", color = themeColor, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }
    }
}

//=======================
// 血压 (浅红背景)
//=======================
@Composable
fun BloodPressureCard(
    navController: NavHostController,
    systolic: Int = 118,
    diastolic: Int = 76,
    status: String = "正常血压"
) {
    val themeColor = Color(0xFFC62828)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = "查看血压详情") { navController.navigate("bloodPressure") },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconBox(Icons.Filled.MedicalServices, themeColor, themeColor.copy(alpha = 0.1f))
                Spacer(Modifier.width(12.dp))
                Text("血压", modifier = Modifier.weight(1f), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = themeColor)
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "查看详情", tint = themeColor.copy(alpha = 0.5f))
            }
            Spacer(Modifier.height(16.dp))
            Text("$systolic / $diastolic mmHg", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = themeColor)
            Text(status, color = themeColor.copy(alpha = 0.8f), fontSize = 15.sp)
        }
    }
}

//=======================
// 健康报告 (浅绿背景) - 重命名避免冲突
//=======================
@Composable
fun ReportSummaryCard(
    navController: NavHostController,
    heartStatus: String = "正常",
    oxygenStatus: String = "优秀",
    sleepStatus: String = "良好",
    healthScore: Int = 92
) {
    val themeColor = Color(0xFF2E7D32)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = "查看今日健康报告") { navController.navigate("report") },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconBox(Icons.Filled.Assessment, themeColor, themeColor.copy(alpha = 0.1f))
                Spacer(Modifier.width(12.dp))
                Text("今日健康报告", modifier = Modifier.weight(1f), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = themeColor)
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "查看详情", tint = themeColor.copy(alpha = 0.5f))
            }
            Spacer(Modifier.height(16.dp))
            Text("心率：$heartStatus", color = themeColor.copy(alpha = 0.8f), fontSize = 15.sp)
            Text("血氧：$oxygenStatus", color = themeColor.copy(alpha = 0.8f), fontSize = 15.sp)
            Text("睡眠：$sleepStatus", color = themeColor.copy(alpha = 0.8f), fontSize = 15.sp)
            Text("今日健康评分：$healthScore", color = themeColor, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }
    }
}