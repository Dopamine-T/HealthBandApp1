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
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Bedtime, contentDescription = "睡眠", tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(8.dp))
                        Text("睡眠监测", fontWeight = FontWeight.Bold)
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
            // 1. 睡眠总览卡片 (深蓝渐变背景)
            // ========================
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFF1A237E), Color(0xFF3949AB), Color(0xFF5C6BC0))
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Filled.Bedtime, contentDescription = "睡眠", tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(40.dp))
                        Spacer(Modifier.height(10.dp))
                        Text("昨晚睡眠", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
                        Spacer(Modifier.height(6.dp))
                        Text("7小时35分钟", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(Modifier.height(8.dp))
                        Text("睡眠质量：良好 ★★★★☆", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 2. 睡眠时间轴
            // ========================
            SleepCard("睡眠阶段时间轴", Icons.Filled.NightsStay, Color(0xFF5C6BC0)) {
                SleepTimeline()
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 3. 睡眠结构 (带图标的进度条)
            // ========================
            SleepCard("睡眠结构分布", Icons.Filled.Cloud, Color(0xFF64B5F6)) {
                SleepStructureBar(Icons.Filled.Bedtime, "深度睡眠", "2小时10分钟", 0.28f, Color(0xff1565C0))
                Spacer(Modifier.height(12.dp))
                SleepStructureBar(Icons.Filled.Cloud, "核心睡眠", "4小时20分钟", 0.57f, Color(0xff64B5F6))
                Spacer(Modifier.height(12.dp))
                SleepStructureBar(Icons.Filled.AutoAwesome, "快速眼动", "1小时05分钟", 0.14f, Color(0xffFFB74D))
                Spacer(Modifier.height(12.dp))
                SleepStructureBar(Icons.Filled.RemoveRedEye, "清醒", "10分钟", 0.01f, Color(0xffB0BEC5))
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 4. 夜间心率
            // ========================
            SleepCard("夜间心率变化", Icons.Filled.Favorite, Color(0xFFEF5350)) {
                NightHeartChart()
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 5. 最近7天睡眠
            // ========================
            SleepCard("最近7天睡眠时长", Icons.Filled.Assessment, Color(0xFF26A69A)) {
                WeekSleepChart()
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 6. 鼾声监测 (浅橙背景)
            // ========================
            SleepCard("鼾声监测", Icons.Filled.GraphicEq, Color(0xFFE65100), Color(0xFFFFF3E0)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SleepMetricItem("时长", "12", "分钟")
                    SleepMetricItem("分贝", "45", "dB")
                    SleepMetricItem("风险", "低", "", Color(0xFF2E7D32))
                }
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 7. 智能闹钟 (浅青背景)
            // ========================
            SleepCard("智能闹钟", Icons.Filled.Alarm, Color(0xFF006064), Color(0xFFE0F7FA)) {
                Text("闹钟时间：07:00", fontSize = 16.sp, color = Color(0xFF006064))
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = Color(0xFF00838F), modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("已在 06:45 (浅睡阶段) 智能唤醒您", color = Color(0xFF00838F), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
                Spacer(Modifier.height(4.dp))
                Text("在浅睡期唤醒，有效减少起床后的疲惫感。", color = Color.Gray, fontSize = 12.sp)
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 8. AI睡眠分析 (浅紫背景)
            // ========================
            SleepCard("AI睡眠分析", Icons.Filled.AutoAwesome, Color(0xFF6A1B9A), Color(0xFFF3E5F5)) {
                AnalysisRow("深睡比例正常，身体恢复良好", Color(0xFF6A1B9A))
                AnalysisRow("夜间心率波动稳定", Color(0xFF6A1B9A))
                AnalysisRow("未发现明显呼吸暂停", Color(0xFF6A1B9A))

                Spacer(Modifier.height(15.dp))
                HorizontalDivider(color = Color(0xFF6A1B9A).copy(alpha = 0.2f))
                Spacer(Modifier.height(15.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.TipsAndUpdates, contentDescription = null, tint = Color(0xFF6A1B9A), modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("改善建议：", fontWeight = FontWeight.Bold, color = Color(0xFF6A1B9A))
                }
                Spacer(Modifier.height(5.dp))
                Text("• 建议保持 23:00 前入睡", color = Color.Gray, fontSize = 14.sp)
                Text("• 睡前1小时减少手机使用", color = Color.Gray, fontSize = 14.sp)
                Text("• 保持固定作息，巩固生物钟", color = Color.Gray, fontSize = 14.sp)
            }

            Spacer(Modifier.height(60.dp))
        }
    }
}

//================================
// 通用带图标的Card
//================================
@Composable
fun SleepCard(
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
// 数据小指标组件 (用于鼾声等)
//================================
@Composable
fun SleepMetricItem(label: String, value: String, unit: String, valueColor: Color = Color.Black) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = Color.Gray, fontSize = 12.sp)
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = valueColor)
            if(unit.isNotEmpty()) {
                Text(" $unit", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 2.dp))
            }
        }
    }
}

//================================
// AI分析行组件
//================================
@Composable
fun AnalysisRow(text: String, color: Color) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(8.dp))
        Text(text, fontSize = 14.sp, color = color)
    }
}

//================================
// 睡眠结构进度条 (带图标)
//================================
@Composable
fun SleepStructureBar(icon: ImageVector, label: String, time: String, percentage: Float, color: Color) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
            Text(time, fontSize = 14.sp, color = Color.Gray)
        }
        Spacer(Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(Color.LightGray.copy(alpha = 0.3f), CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(percentage)
                    .height(10.dp)
                    .background(color, CircleShape)
            )
        }
    }
}

//================================
// 睡眠时间轴 (多色阶梯条带图)
//================================
@Composable
fun SleepTimeline() {
    val stages = remember {
        listOf(
            0, 2, 2, 3, 3, 3, 2, 2, 1, 0, 2, 2,
            3, 3, 3, 2, 2, 1, 1, 0, 2, 2, 3, 3,
            2, 2, 1, 1, 0, 2, 2, 3, 2, 2, 1, 1,
            2, 1, 0, 2, 2, 1, 0
        )
    }

    val stageColors = listOf(
        Color(0xffB0BEC5), Color(0xffFFB74D), Color(0xff64B5F6), Color(0xff1565C0)
    )

    Column {
        Canvas(modifier = Modifier.fillMaxWidth().height(120.dp)) {
            val stepX = size.width / stages.size
            val sectionHeight = size.height / 4f

            stages.forEachIndexed { i, stage ->
                val color = stageColors[stage]
                val yTop = stage * sectionHeight
                drawRect(
                    color = color,
                    topLeft = Offset(i * stepX + 1f, yTop + 2f),
                    size = Size(stepX - 2f, sectionHeight - 2f)
                )
            }

            val linePath = Path()
            stages.forEachIndexed { i, stage ->
                val x = i * stepX
                val yCenter = stage * sectionHeight + (sectionHeight / 2f)
                if (i == 0) linePath.moveTo(x, yCenter) else linePath.lineTo(x, yCenter)
                linePath.lineTo(x + stepX, yCenter)
            }

            drawPath(
                path = linePath,
                color = Color.White,
                style = Stroke(width = 3f, cap = StrokeCap.Round, join = androidx.compose.ui.graphics.StrokeJoin.Round)
            )
        }

        Spacer(Modifier.height(10.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("23:00", color = Color.Gray, fontSize = 12.sp)
            Text("01:00", color = Color.Gray, fontSize = 12.sp)
            Text("03:00", color = Color.Gray, fontSize = 12.sp)
            Text("05:00", color = Color.Gray, fontSize = 12.sp)
            Text("07:00", color = Color.Gray, fontSize = 12.sp)
        }

        Spacer(Modifier.height(15.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Legend(Color(0xffB0BEC5), "清醒")
            Legend(Color(0xffFFB74D), "快速眼动")
            Legend(Color(0xff64B5F6), "核心睡眠")
            Legend(Color(0xff1565C0), "深度睡眠")
        }
    }
}

@Composable
fun Legend(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(12.dp).background(color, CircleShape))
        Spacer(Modifier.width(5.dp))
        Text(text, fontSize = 12.sp)
    }
}

//================================
// 夜间心率曲线
//================================
@Composable
fun NightHeartChart() {
    val data = remember { listOf(68, 66, 65, 63, 61, 60, 59, 58, 60, 62, 63, 65) }
    var selectedIndex by remember { mutableIntStateOf(-1) }

    Row {
        Column(
            modifier = Modifier.height(220.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text("80", color = Color.Gray, fontSize = 10.sp)
            Text("70", color = Color.Gray, fontSize = 10.sp)
            Text("60", color = Color.Gray, fontSize = 10.sp)
            Text("50", color = Color.Gray, fontSize = 10.sp)
        }

        Spacer(Modifier.width(10.dp))

        Column {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            val index = (offset.x / size.width * (data.size - 1))
                                .toInt()
                                .coerceIn(0, data.size - 1)
                            selectedIndex = index
                        }
                    }
            ) {
                val step = size.width / (data.size - 1)
                val max = 80
                val min = 50

                val gridColor = Color.Gray.copy(alpha = 0.3f)
                for (i in 1..3) {
                    drawLine(
                        gridColor,
                        Offset(0f, size.height * (i / 4f)),
                        Offset(size.width, size.height * (i / 4f)),
                        1f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                    )
                }

                val linePath = Path()
                data.forEachIndexed { i, v ->
                    val x = i * step
                    val y = size.height - ((v - min).toFloat() / (max - min) * size.height)
                    if (i == 0) linePath.moveTo(x, y) else linePath.lineTo(x, y)
                }

                val fillPath = Path().apply {
                    addPath(linePath)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(
                    fillPath,
                    brush = Brush.verticalGradient(listOf(Color.Red.copy(alpha = 0.3f), Color.Red.copy(alpha = 0.0f)))
                )

                drawPath(linePath, Color.Red, style = Stroke(width = 5f, cap = StrokeCap.Round))

                if (selectedIndex >= 0) {
                    val x = selectedIndex * step
                    val y = size.height - ((data[selectedIndex] - min).toFloat() / (max - min) * size.height)
                    drawLine(Color.Gray.copy(alpha = 0.5f), Offset(x, 0f), Offset(x, size.height), 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f)))
                    drawCircle(Color.White, 10f, Offset(x, y))
                    drawCircle(Color.Red, 7f, Offset(x, y))
                }
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("23", color = Color.Gray, fontSize = 10.sp)
                Text("01", color = Color.Gray, fontSize = 10.sp)
                Text("03", color = Color.Gray, fontSize = 10.sp)
                Text("05", color = Color.Gray, fontSize = 10.sp)
                Text("07", color = Color.Gray, fontSize = 10.sp)
            }

            if (selectedIndex >= 0) {
                Spacer(Modifier.height(5.dp))
                Text("心率：${data[selectedIndex]} BPM", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

//================================
// 最近7天睡眠
//================================
@Composable
fun WeekSleepChart() {
    val hours = listOf(7f, 6.5f, 8f, 7.2f, 6.8f, 8.3f, 7.5f)
    val days = listOf("一", "二", "三", "四", "五", "六", "日")
    val avg = hours.average()

    Column {
        Row {
            Column(
                modifier = Modifier.height(180.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text("10", color = Color.Gray, fontSize = 10.sp)
                Text("8", color = Color.Gray, fontSize = 10.sp)
                Text("6", color = Color.Gray, fontSize = 10.sp)
                Text("4", color = Color.Gray, fontSize = 10.sp)
                Text("0", color = Color.Gray, fontSize = 10.sp)
            }

            Spacer(Modifier.width(10.dp))

            Row(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                hours.forEachIndexed { index, value ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val barColor = if (value >= 7f) Color(0xff3F51B5) else Color(0xffFFA726)
                        Box(
                            modifier = Modifier
                                .width(22.dp)
                                .height((value * 18).dp)
                                .background(barColor, RoundedCornerShape(4.dp))
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(days[index], fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        Text(
            "周平均：${"%.1f".format(avg)} 小时",
            modifier = Modifier.fillMaxWidth(),
            color = Color.Gray,
            fontSize = 12.sp,
            textAlign = TextAlign.End
        )
    }
}