package com.example.healthbandapp.ui.theme.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeartRateScreen(navController: NavHostController) {

    // ========================
    // 状态变量
    // ========================
    var heartRate by remember { mutableIntStateOf(75) }
    var warning by remember { mutableStateOf(true) }
    var highLimit by remember { mutableIntStateOf(120) }
    var lowLimit by remember { mutableIntStateOf(50) }

    val heartData = remember { List(24) { Random.nextInt(55, 130) } }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    var lastUpdateTime by remember { mutableStateOf("刚刚") }

    // ========================
    // 模拟手环实时数据推送
    // ========================
    LaunchedEffect(Unit) {
        var counter = 0
        while (true) {
            delay(2000)
            heartRate = (heartRate + Random.nextInt(-3, 4)).coerceIn(60, 100)
            counter++
            lastUpdateTime = "${counter * 2}秒前"
        }
    }

    val isNormal = heartRate in lowLimit..highLimit
    val statusText = if (isNormal) "状态正常" else "心率异常"
    val statusColor by animateColorAsState(
        targetValue = if (isNormal) Color(0xFF4CAF50) else Color(0xFFFFCDD2),
        animationSpec = tween(durationMillis = 300), label = "statusColor"
    )
    val healthAdvice = when {
        heartRate < lowLimit -> "当前心率偏低，若伴有头晕等不适，请留意休息。"
        heartRate > highLimit -> "当前心率偏高，请尝试深呼吸放松，避免剧烈运动。"
        else -> "心率处于健康区间，请继续保持良好的作息习惯。"
    }

    // ========================
    // 页面骨架
    // ========================
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.MonitorHeart, contentDescription = "心率", tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(8.dp))
                        Text("心率监测", fontWeight = FontWeight.Bold)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = "同步",
                        modifier = Modifier.padding(end = 16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            // ========================
            // 1. 实时仪表盘卡片 (深红渐变背景)
            // ========================
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFB71C1C), Color(0xFFE53935))
                            )
                        )
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("实时心率", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
                        Spacer(Modifier.height(15.dp))

                        Box(
                            modifier = Modifier
                                .size(220.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.15f)), // 半透明白色圆环
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Filled.MonitorHeart,
                                    contentDescription = "",
                                    tint = Color.White,
                                    modifier = Modifier.size(45.dp)
                                )
                                Text("$heartRate", fontSize = 60.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("BPM", fontSize = 16.sp, color = Color.White.copy(alpha = 0.8f))
                            }
                        }

                        Spacer(Modifier.height(15.dp))
                        Text(statusText, color = statusColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(Modifier.height(5.dp))
                        Text("最近更新：$lastUpdateTime", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 2. 健康建议卡片 (浅青背景)
            // ========================
            HeartCard("健康建议", Icons.Filled.Lightbulb, Color(0xFF006064), containerColor = Color(0xFFE0F7FA)) {
                Text(healthAdvice, fontSize = 14.sp, color = Color(0xFF006064))
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 3. 24小时心率曲线 (纯白背景)
            // ========================
            HeartCard("24小时连续心率", Icons.Filled.ShowChart, Color(0xFFEF5350)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("全天趋势", color = Color.Gray, fontSize = 14.sp)
                    Text("平均: ${heartData.average().toInt()} BPM", color = Color(0xFFEF5350), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(15.dp))

                HeartChart(
                    data = heartData,
                    selectedIndex = selectedIndex,
                    onSelect = { selectedIndex = it }
                )

                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("0:00", color = Color.Gray, fontSize = 12.sp)
                    Text("6:00", color = Color.Gray, fontSize = 12.sp)
                    Text("12:00", color = Color.Gray, fontSize = 12.sp)
                    Text("18:00", color = Color.Gray, fontSize = 12.sp)
                    Text("24:00", color = Color.Gray, fontSize = 12.sp)
                }
            }

            if (selectedIndex >= 0) {
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.AccessTime, contentDescription = "时间", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "${selectedIndex}:00 时的心率：${heartData[selectedIndex]} BPM",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 4. 今日概览 (浅蓝背景)
            // ========================
            HeartCard("今日概览", Icons.Filled.Info, Color(0xFF1565C0), containerColor = Color(0xFFE3F2FD)) {
                InfoRow("静息心率", "59 BPM")
                InfoRow("运动峰值心率", "140 BPM")
                InfoRow("平均心率", "80 BPM")
                InfoRow("夜间波动", "55~70 BPM")
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 5. 异常提醒设置 (浅红背景)
            // ========================
            HeartCard("心率异常提醒", Icons.Filled.NotificationsActive, Color(0xFFC62828), containerColor = Color(0xFFFFEBEE)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("高心率阈值：$highLimit BPM", color = Color(0xFFC62828))
                    Text("低心率阈值：$lowLimit BPM", color = Color(0xFFC62828))
                }

                Spacer(Modifier.height(15.dp))
                HorizontalDivider(color = Color(0xFFC62828).copy(alpha = 0.2f))
                Spacer(Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("开启心率异常提醒", fontSize = 16.sp, color = Color(0xFFC62828))
                    Switch(checked = warning, onCheckedChange = { warning = it })
                }
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 6. 活动状态分布 (浅绿背景)
            // ========================
            HeartCard("活动状态分布", Icons.Filled.Insights, Color(0xFF2E7D32), containerColor = Color(0xFFE8F5E9)) {
                InfoRow("步行", "85 BPM", Color(0xFF2E7D32))
                InfoRow("运动", "135 BPM", Color(0xFF2E7D32))
                InfoRow("睡眠", "60 BPM", Color(0xFF2E7D32))
                InfoRow("压力状态", "95 BPM", Color(0xFF2E7D32))
            }

            Spacer(Modifier.height(60.dp))
        }
    }
}

//================================
// 通用带图标的Card
//================================
@Composable
fun HeartCard(
    title: String,
    icon: ImageVector,
    iconTint: Color,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(iconTint.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = title, tint = iconTint, modifier = Modifier.size(20.dp))
                }
                Spacer(Modifier.width(10.dp))
                Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            }
            Spacer(Modifier.height(15.dp))
            content()
        }
    }
}

//================================
// 信息行组件 (替代纯Text)
//================================
@Composable
fun InfoRow(label: String, value: String, color: Color = Color.Black) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 15.sp, color = color.copy(alpha = 0.8f))
        Text(value, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = color)
    }
}

//================================
// 心率曲线 (带渐变填充和点击标记)
//================================
@Composable
fun HeartChart(
    data: List<Int>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val index = (offset.x / size.width * (data.size - 1))
                        .toInt()
                        .coerceIn(0, data.size - 1)
                    onSelect(index)
                }
            }
    ) {
        val step = size.width / (data.size - 1)
        val max = 140
        val min = 40

        // 1. 背景网格线
        val gridColor = Color.Gray.copy(alpha = 0.3f)
        for (i in 1..3) {
            val y = size.height * (i / 4f)
            drawLine(
                color = gridColor,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        }

        // 2. 曲线路径
        val linePath = Path()
        data.forEachIndexed { i, value ->
            val x = i * step
            val y = size.height - ((value - min).toFloat() / (max - min) * size.height)
            if (i == 0) linePath.moveTo(x, y) else linePath.lineTo(x, y)
        }

        // 3. 渐变填充
        val fillPath = Path().apply {
            addPath(linePath)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFE53935).copy(alpha = 0.4f),
                    Color(0xFFE53935).copy(alpha = 0.0f)
                )
            )
        )

        // 4. 折线本体
        drawPath(
            path = linePath,
            color = Color(0xFFE53935),
            style = Stroke(width = 5f, cap = StrokeCap.Round)
        )

        // 5. 十字准星和圆点
        if (selectedIndex >= 0) {
            val x = selectedIndex * step
            val y = size.height - ((data[selectedIndex] - min).toFloat() / (max - min) * size.height)

            drawLine(
                color = Color.Gray.copy(alpha = 0.5f),
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f))
            )
            drawCircle(color = Color.White, radius = 10f, center = Offset(x, y))
            drawCircle(color = Color(0xFFE53935), radius = 7f, center = Offset(x, y))
        }
    }
}