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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
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

    // 模拟24小时历史数据
    val heartData = remember {
        List(24) { Random.nextInt(55, 130) }
    }

    var selectedIndex by remember { mutableIntStateOf(-1) }

    // 模拟最近更新时间
    var lastUpdateTime by remember { mutableStateOf("刚刚") }

    // ========================
    // 模拟手环实时数据推送
    // ========================
    LaunchedEffect(Unit) {
        var counter = 0
        while (true) {
            delay(2000) // 每2秒刷新一次
            // 模拟心率在正常范围内小幅度波动
            heartRate = (heartRate + Random.nextInt(-3, 4)).coerceIn(60, 100)
            counter++
            lastUpdateTime = "${counter * 2}秒前"
        }
    }

    // 动态计算当前状态与建议
    val isNormal = heartRate in lowLimit..highLimit
    val statusText = if (isNormal) "🟢 状态正常" else "⚠ 心率异常"
    val statusColor by animateColorAsState(
        targetValue = if (isNormal) Color(0xff2E7D32) else Color.Red,
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
                title = { Text("❤️ 心率监测", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    Icon(
                        Icons.Default.Refresh,
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
            // 1. 实时仪表盘卡片
            // ========================
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("实时心率", fontSize = 18.sp, color = Color.Gray)
                    Spacer(Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .clip(CircleShape)
                            .background(Color(0xffffdddd)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "",
                                tint = Color.Red,
                                modifier = Modifier.size(45.dp)
                            )
                            Text("$heartRate", fontSize = 60.sp, fontWeight = FontWeight.Bold, color = Color.Red)
                            Text("BPM", fontSize = 16.sp, color = Color.Gray)
                        }
                    }

                    Spacer(Modifier.height(15.dp))
                    Text(statusText, color = statusColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.height(5.dp))
                    Text("最近更新：$lastUpdateTime", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 2. 健康建议卡片
            // ========================
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("💡", fontSize = 28.sp)
                    Spacer(Modifier.width(15.dp))
                    Text(healthAdvice, fontSize = 14.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 3. 24小时心率曲线
            // ========================
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("24小时连续心率", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("平均: ${heartData.average().toInt()} BPM", color = Color.Gray, fontSize = 14.sp)
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
            }

            if (selectedIndex >= 0) {
                Spacer(Modifier.height(10.dp))
                Text(
                    "🕒 ${selectedIndex}:00 时的心率：${heartData[selectedIndex]} BPM",
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 4. 详情卡片信息
            // ========================
            HeartInfoCard("今日概览", listOf(
                "静息心率：59 BPM",
                "运动峰值心率：140 BPM",
                "平均心率：80 BPM",
                "夜间波动：55~70 BPM"
            ))

            Spacer(Modifier.height(20.dp))

            // ========================
            // 5. 异常提醒设置 (将开关整合进卡片)
            // ========================
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text("⚠ 心率异常提醒", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(15.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("高心率阈值：$highLimit BPM")
                        Text("低心率阈值：$lowLimit BPM")
                    }

                    Spacer(Modifier.height(15.dp))
                    HorizontalDivider(modifier = Modifier.padding(vertical = 5.dp))
                    Spacer(Modifier.height(15.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("开启心率异常提醒", fontSize = 16.sp)
                        Switch(checked = warning, onCheckedChange = { warning = it })
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            HeartInfoCard("活动状态分布", listOf(
                "🚶 步行：85 BPM",
                "🏃 运动：135 BPM",
                "😴 睡眠：60 BPM",
                "😰 压力状态：95 BPM"
            ))

            Spacer(Modifier.height(60.dp))
        }
    }
}

//================================
// 心率信息卡
//================================
@Composable
fun HeartInfoCard(title: String, items: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(15.dp))
            items.forEach {
                Text(it, fontSize = 15.sp, modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

//================================
// 心率曲线 (升级版：带渐变填充和点击标记)
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

        // 1. 绘制背景网格线 (3条横线)
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

        // 2. 构建曲线路径
        val linePath = Path()
        data.forEachIndexed { i, value ->
            val x = i * step
            val y = size.height - ((value - min).toFloat() / (max - min) * size.height)
            if (i == 0) linePath.moveTo(x, y) else linePath.lineTo(x, y)
        }

        // 3. 构建渐变填充区域路径
        val fillPath = Path().apply {
            addPath(linePath)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }

        // 绘制渐变填充
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Red.copy(alpha = 0.4f),
                    Color.Red.copy(alpha = 0.0f)
                )
            )
        )

        // 4. 绘制折线本体
        drawPath(
            path = linePath,
            color = Color.Red,
            style = Stroke(width = 5f, cap = StrokeCap.Round)
        )

        // 5. 绘制点击选中状态的十字准星和圆点
        if (selectedIndex >= 0) {
            val x = selectedIndex * step
            val y = size.height - ((data[selectedIndex] - min).toFloat() / (max - min) * size.height)

            // 竖线
            drawLine(
                color = Color.Gray.copy(alpha = 0.5f),
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f))
            )
            // 圆点外圈
            drawCircle(
                color = Color.White,
                radius = 10f,
                center = Offset(x, y)
            )
            // 圆点内圈
            drawCircle(
                color = Color.Red,
                radius = 7f,
                center = Offset(x, y)
            )
        }
    }
}