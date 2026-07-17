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
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun OxygenScreen(navController: NavHostController) {

    // ========================
    // 状态变量
    // ========================
    var oxygen by remember { mutableStateOf(98) }
    var warning by remember { mutableStateOf(true) }
    var lowLimit by remember { mutableStateOf(90) }

    // 模拟48个数据点（每半小时一个，共24小时）
    val oxygenData = remember { List(48) { Random.nextInt(94, 100) } }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    var lastUpdateTime by remember { mutableStateOf("刚刚") }

    // ========================
    // 模拟手环实时数据推送
    // ========================
    LaunchedEffect(Unit) {
        var counter = 0
        while (true) {
            delay(3000) // 每3秒刷新一次
            // 模拟血氧在95-99之间波动
            oxygen = (oxygen + Random.nextInt(-1, 2)).coerceIn(94, 100)
            counter++
            lastUpdateTime = "${counter * 3}秒前"
        }
    }

    // 动态健康状态评估
    val isNormal = oxygen >= lowLimit
    val statusText = when {
        oxygen >= 95 -> "🟢 血氧优秀"
        oxygen in 90..94 -> "🟡 血氧正常"
        else -> "⚠ 血氧偏低"
    }
    val statusColor by animateColorAsState(
        targetValue = if (isNormal) Color(0xff2E8B57) else Color.Red,
        animationSpec = tween(durationMillis = 300), label = "oxygenStatusColor"
    )
    val healthAdvice = when {
        oxygen < lowLimit -> "血氧偏低，请深呼吸或开窗通风，若持续偏低请就医。"
        oxygen < 95 -> "血氧处于正常下限，建议适当休息，保持空气流通。"
        else -> "血氧饱和度极佳，身体供氧能力良好，请继续保持！"
    }

    // ========================
    // 页面骨架
    // ========================
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🫁 血氧监测", fontWeight = FontWeight.Bold) },
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
            // 1. 实时血氧仪表盘
            // ========================
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("实时血氧", fontSize = 18.sp, color = Color.Gray)
                    Spacer(Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(Color(0xffE8FFF0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Air, null, tint = Color(0xff2E8B57), modifier = Modifier.size(45.dp))
                            Text("$oxygen%", fontSize = 55.sp, color = Color(0xff2E8B57), fontWeight = FontWeight.Bold)
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
            // 3. 24小时血氧曲线
            // ========================
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("24小时血氧变化", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(15.dp))

                    OxygenChart(
                        data = oxygenData,
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
                val hour = selectedIndex / 2
                val minute = if (selectedIndex % 2 == 0) "00" else "30"
                Text(
                    "🕒 ${String.format("%02d", hour)}:$minute 时的血氧：${oxygenData[selectedIndex]}%",
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 4. 今日概览 & 夜间监测
            // ========================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(modifier = Modifier.weight(1f), shape = RoundedCornerShape(20.dp)) {
                    Column(Modifier.padding(20.dp)) {
                        Text("今日概览", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(10.dp))
                        Text("平均：98%", fontSize = 14.sp)
                        Text("最高：100%", fontSize = 14.sp)
                        Text("最低：94%", fontSize = 14.sp)
                    }
                }
                Card(modifier = Modifier.weight(1f), shape = RoundedCornerShape(20.dp)) {
                    Column(Modifier.padding(20.dp)) {
                        Text("夜间监测", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(10.dp))
                        Text("夜间最低：95%", fontSize = 14.sp)
                        Text("呼吸暂停：1次", fontSize = 14.sp)
                        Text("风险：低", fontSize = 14.sp, color = Color(0xff2E8B57))
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 5. 异常提醒设置
            // ========================
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text("⚠ 血氧异常提醒", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(15.dp))
                    Text("低血氧阈值：$lowLimit%")
                    Spacer(Modifier.height(15.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(15.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("开启低血氧提醒", fontSize = 16.sp)
                        Switch(checked = warning, onCheckedChange = { warning = it })
                    }
                }
            }

            Spacer(Modifier.height(60.dp))
        }
    }
}

//================================
// 血氧曲线 (升级版：带渐变填充和点击标记)
//================================
@Composable
fun OxygenChart(
    data: List<Int>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
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

        // 修复Y轴计算逻辑：血氧浓度通常在 85~100 之间
        val maxSpO2 = 100
        val minSpO2 = 85

        // 1. 绘制背景网格线
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
            // 将 85-100 的值映射到 0-size.height 的画布上
            val y = size.height - ((value - minSpO2).toFloat() / (maxSpO2 - minSpO2) * size.height)
            if (i == 0) linePath.moveTo(x, y) else linePath.lineTo(x, y)
        }

        // 3. 构建渐变填充区域路径
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
                    Color(0xff2E8B57).copy(alpha = 0.4f),
                    Color(0xff2E8B57).copy(alpha = 0.0f)
                )
            )
        )

        // 4. 绘制折线本体
        drawPath(
            path = linePath,
            color = Color(0xff2E8B57),
            style = Stroke(width = 4f, cap = StrokeCap.Round)
        )

        // 5. 绘制点击选中状态的十字准星和圆点
        if (selectedIndex >= 0) {
            val x = selectedIndex * step
            val y = size.height - ((data[selectedIndex] - minSpO2).toFloat() / (maxSpO2 - minSpO2) * size.height)

            drawLine(
                color = Color.Gray.copy(alpha = 0.5f),
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f))
            )
            drawCircle(color = Color.White, radius = 10f, center = Offset(x, y))
            drawCircle(color = Color(0xff2E8B57), radius = 7f, center = Offset(x, y))
        }
    }
}