package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FireExtinguisher // 实际上用作卡路里
import androidx.compose.material.icons.filled.LocalFireDepartment
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
import androidx.navigation.NavController

// 模拟的每日运动数据
data class DailySportData(
    val day: String,
    val steps: Int,
    val calories: Int,
    val distance: Double
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportWeeklyReportScreen(navController: NavController) {

    // 模拟一周的运动数据
    val weekData = listOf(
        DailySportData("周一", 8520, 320, 5.2),
        DailySportData("周二", 10200, 410, 6.8),
        DailySportData("周三", 6500, 250, 4.1),
        DailySportData("周四", 12000, 480, 7.5),
        DailySportData("周五", 9800, 390, 6.0),
        DailySportData("周六", 15000, 600, 9.2),
        DailySportData("周日", 7200, 280, 4.5)
    )

    val totalSteps = weekData.sumOf { it.steps }
    val totalCalories = weekData.sumOf { it.calories }
    val totalDistance = weekData.sumOf { it.distance }
    val maxSteps = weekData.maxOf { it.steps } // 用于计算柱状图高度比例

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("运动周报", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // 1. 顶部本周汇总卡片
            SummaryCard(totalSteps, totalCalories, totalDistance)

            Spacer(Modifier.height(24.dp))

            // 2. 本周步数柱状图
            Text("每日步数趋势", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            BarChart(weekData, maxSteps)

            Spacer(Modifier.height(24.dp))

            // 3. 每日明细列表
            Text("每日明细", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            weekData.forEach { dailyData ->
                DailyDetailItem(dailyData)
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

// 汇总卡片组件
@Composable
fun SummaryCard(totalSteps: Int, totalCalories: Int, totalDistance: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("本周汇总", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SummaryItem(Icons.Default.DirectionsRun, "步数", "$totalSteps")
                SummaryItem(Icons.Default.LocalFireDepartment, "千卡", "$totalSteps")
                SummaryItem(Icons.Default.DirectionsRun, "公里", String.format("%.1f", totalDistance))
            }
        }
    }
}

// 汇总卡片内的单个数据项
@Composable
fun SummaryItem(icon: ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        Text(text = label, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// 简单的柱状图组件
@Composable
fun BarChart(weekData: List<DailySportData>, maxSteps: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(150.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            weekData.forEach { data ->
                // 计算柱状图高度比例
                val heightRatio = if (maxSteps > 0) data.steps.toFloat() / maxSteps else 0f

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.weight(1f)
                ) {
                    // 数值
                    Text(
                        text = "${data.steps / 1000}k",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(4.dp))

                    // 柱子
                    Box(
                        modifier = Modifier
                            .width(16.dp)
                            .fillMaxHeight(heightRatio) // 按比例填充高度
                            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                                    )
                                )
                            )
                    )
                    Spacer(Modifier.height(8.dp))
                    // 日期
                    Text(
                        text = data.day,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

// 每日明细列表项
@Composable
fun DailyDetailItem(data: DailySportData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 日期
            Text(
                text = data.day,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(50.dp)
            )

            // 数据详情
            Column(modifier = Modifier.weight(1f)) {
                Text("${data.steps} 步", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                Text("${data.distance} 公里 | ${data.calories} 千卡", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            // 步数完成度小圆点（假设目标10000步）
            val progress = (data.steps / 10000f).coerceIn(0f, 1f)
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.size(32.dp),
                strokeWidth = 4.dp,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}