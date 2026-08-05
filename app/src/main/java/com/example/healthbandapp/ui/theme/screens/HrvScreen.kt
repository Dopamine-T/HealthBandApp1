package com.example.healthbandapp.ui.theme.screens

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
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.TipsAndUpdates
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
fun HrvScreen(navController: NavHostController) {

    var hrv by remember { mutableIntStateOf(65) }
    val hrvData = remember { List(24) { Random.nextInt(40, 90) } }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    var lastUpdateTime by remember { mutableStateOf("刚刚") }

    // 模拟手环实时数据推送
    LaunchedEffect(Unit) {
        var counter = 0
        while (true) {
            delay(3000L) // 修复 Long 重载警告
            hrv = (hrv + Random.nextInt(-3, 4)).coerceIn(50, 80)
            counter++
            lastUpdateTime = "${counter * 3}秒前"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.MonitorHeart, contentDescription = "HRV", tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(8.dp))
                        Text("HRV健康分析", fontWeight = FontWeight.Bold)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
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
            // 1. HRV实时仪表盘 (深橙渐变背景)
            // ========================
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier.background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFE65100), Color(0xFFFF9800))
                        )
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("实时心率变异率", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                        Spacer(Modifier.height(15.dp))

                        Box(
                            modifier = Modifier
                                .size(180.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("$hrv", fontSize = 60.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("ms", color = Color.White.copy(alpha = 0.8f))
                            }
                        }

                        Spacer(Modifier.height(15.dp))
                        Text("HRV正常", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(Modifier.height(5.dp))
                        Text("最近更新：$lastUpdateTime", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 2. 身体准备度 (纯白背景)
            // ========================
            HrvCard("最近数据", Icons.Filled.Bolt, Color(0xFFFF9800)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("最高 HRV", color = Color.Gray, fontSize = 14.sp)
                        Text("${hrvData.max()} ms", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("最低 HRV", color = Color.Gray, fontSize = 14.sp)
                        Text("${hrvData.min()} ms", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFEF5350))
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 3. 24小时HRV趋势 (纯白背景)
            // ========================
            HrvCard("24小时HRV趋势", Icons.AutoMirrored.Filled.ShowChart, Color(0xFF43A047)) {
                HrvChart(
                    data = hrvData,
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

                if (selectedIndex >= 0) {
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "${selectedIndex}:00 时的 HRV：${hrvData[selectedIndex]} ms",
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }




            Spacer(Modifier.height(60.dp))
        }
    }
}

//================================
// 通用带图标的Card
//================================
@Composable
fun HrvCard(
    title: String,
    icon: ImageVector,
    iconTint: Color,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
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
// HRV 信息行组件 (重命名以避免冲突)
//================================
@Composable
fun HrvInfoRow(label: String, value: String, color: Color = Color.Black) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 15.sp, color = color.copy(alpha = 0.8f))
        Text(value, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = color)
    }
}

//================================
// 建议行组件
//================================
@Composable
fun SuggestionRow(text: String, color: Color) {
    Row(
        modifier = Modifier.padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(8.dp))
        Text(text, fontSize = 14.sp, color = color)
    }
}

//================================
// HRV趋势曲线 (升级版)
//================================
@Composable
fun HrvChart(
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
        val max = 100f
        val min = 30f

        // 1. 背景网格线
        val gridColor = Color.Gray.copy(alpha = 0.3f)
        for (i in 1..3) {
            val y = size.height * (i / 4f)
            drawLine(
                gridColor,
                Offset(0f, y),
                Offset(size.width, y),
                strokeWidth = 1f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        }

        // 2. 曲线路径
        val linePath = Path()
        data.forEachIndexed { i, value ->
            val x = i * step
            val y = size.height - ((value - min) / (max - min) * size.height)
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
            fillPath,
            brush = Brush.verticalGradient(listOf(Color(0xFF43A047).copy(alpha = 0.4f), Color(0xFF43A047).copy(alpha = 0.0f)))
        )

        // 4. 折线本体
        drawPath(linePath, Color(0xFF43A047), style = Stroke(width = 5f, cap = StrokeCap.Round))

        // 5. 点击选中标记
        if (selectedIndex >= 0) {
            val x = selectedIndex * step
            val y = size.height - ((data[selectedIndex] - min) / (max - min) * size.height)
            drawLine(Color.Gray.copy(alpha = 0.5f), Offset(x, 0f), Offset(x, size.height), 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f)))
            drawCircle(Color.White, 10f, Offset(x, y))
            drawCircle(Color(0xFF43A047), 7f, Offset(x, y))
        }
    }
}