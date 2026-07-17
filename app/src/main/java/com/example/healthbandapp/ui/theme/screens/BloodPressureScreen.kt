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
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MonitorHeart
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
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodPressureScreen(navController: NavHostController) {

    var systolic by remember { mutableIntStateOf(118) }
    var diastolic by remember { mutableIntStateOf(76) }
    var tab by remember { mutableIntStateOf(0) }

    val systolicData = remember { List(24) { Random.nextInt(110, 135) } }
    val diastolicData = remember { List(24) { Random.nextInt(65, 90) } }
    var selectedIndex by remember { mutableIntStateOf(-1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Bloodtype, contentDescription = "血压", tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(8.dp))
                        Text("血压监测", fontWeight = FontWeight.Bold)
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
            // 1. PrimaryTabRow 日期切换
            // ========================
            PrimaryTabRow(
                selectedTabIndex = tab,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                listOf("日", "周", "月").forEachIndexed { index, text ->
                    Tab(
                        selected = tab == index,
                        onClick = { tab = index },
                        text = { Text(text, fontWeight = if (tab == index) FontWeight.Bold else FontWeight.Normal) }
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 2. 血压仪表盘 (深红渐变背景)
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
                            colors = listOf(Color(0xFFB71C1C), Color(0xFFE53935))
                        )
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(240.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                // 背景弧
                                drawArc(
                                    color = Color.White.copy(alpha = 0.2f),
                                    startAngle = 135f,
                                    sweepAngle = 270f,
                                    useCenter = false,
                                    style = Stroke(width = 25f, cap = StrokeCap.Round)
                                )
                                // 进度弧
                                drawArc(
                                    color = Color.White,
                                    startAngle = 135f,
                                    sweepAngle = 200f,
                                    useCenter = false,
                                    style = Stroke(width = 25f, cap = StrokeCap.Round)
                                )
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Filled.MonitorHeart,
                                    contentDescription = "",
                                    tint = Color.White,
                                    modifier = Modifier.size(40.dp)
                                )
                                Text("$systolic/$diastolic", fontSize = 42.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("mmHg", color = Color.White.copy(alpha = 0.8f))
                                Text("正常", color = Color.White.copy(alpha = 0.9f), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 3. 24小时血压趋势 (纯白背景)
            // ========================
            BloodPressureCard("24小时血压趋势", Icons.AutoMirrored.Filled.ShowChart, Color(0xFFEF5350), Color.White) {
                BloodPressureChart(
                    systolicData,
                    diastolicData,
                    selectedIndex
                ) { selectedIndex = it }

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

            // 点击显示详情
            if (selectedIndex >= 0) {
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "${selectedIndex}:00  收缩压：${systolicData[selectedIndex]} mmHg | 舒张压：${diastolicData[selectedIndex]} mmHg",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 4. WHO血压等级 (浅红背景)
            // ========================
            BloodPressureCard("WHO血压等级", Icons.Filled.Info, Color(0xFFC62828), Color(0xFFFFEBEE)) {
                WhoLevelRow(Color(0xFF4CAF50), "正常", "<120/80 mmHg")
                WhoLevelRow(Color(0xFFFFC107), "偏高", "120~129 mmHg")
                WhoLevelRow(Color(0xFFFF9800), "一级高血压", "130~139 mmHg")
                WhoLevelRow(Color(0xFFE53935), "二级高血压", "≥140 mmHg")
            }

            Spacer(Modifier.height(20.dp))

            // ========================
            // 5. AI血压分析 (浅绿背景)
            // ========================
            BloodPressureCard("AI血压分析", Icons.Filled.AutoAwesome, Color(0xFF2E7D32), Color(0xFFE8F5E9)) {
                AnalysisRow("今日血压波动稳定")
                AnalysisRow("夜间血压正常")
                AnalysisRow("未发现明显异常")

                Spacer(Modifier.height(15.dp))
                HorizontalDivider(color = Color(0xFF2E7D32).copy(alpha = 0.2f))
                Spacer(Modifier.height(15.dp))

                Text("💡 改善建议：", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                Spacer(Modifier.height(5.dp))
                Text("• 控制每日盐分摄入", color = Color.Gray, fontSize = 14.sp)
                Text("• 保持适量有氧运动", color = Color.Gray, fontSize = 14.sp)
                Text("• 保证充足睡眠质量", color = Color.Gray, fontSize = 14.sp)
            }

            Spacer(Modifier.height(60.dp))
        }
    }
}

//================================
// 通用带图标的Card
//================================
@Composable
fun BloodPressureCard(
    title: String,
    icon: ImageVector,
    iconTint: Color,
    containerColor: Color,
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
                Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(15.dp))
            content()
        }
    }
}

//================================
// WHO等级行组件
//================================
@Composable
fun WhoLevelRow(color: Color, level: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(10.dp).clip(CircleShape).background(color))
        Spacer(Modifier.width(10.dp))
        Text(level, fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
        Text(value, fontSize = 14.sp, color = Color.Gray)
    }
}

//================================
// AI分析行组件
//================================
@Composable
fun AnalysisRow(text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(8.dp))
        Text(text, fontSize = 14.sp, color = Color(0xFF2E7D32))
    }
}

//================================
// 血压趋势曲线 (升级版)
//================================
@Composable
fun BloodPressureChart(
    systolic: List<Int>,
    diastolic: List<Int>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val index = (offset.x / size.width * (systolic.size - 1))
                        .toInt()
                        .coerceIn(0, systolic.size - 1)
                    onSelect(index)
                }
            }
    ) {
        val maxValue = 150f
        val minValue = 50f
        val chartHeight = size.height
        val step = size.width / (systolic.size - 1)

        fun y(value: Int): Float {
            val ratio = (value - minValue) / (maxValue - minValue)
            return chartHeight - (ratio * chartHeight)
        }

        // 1. 背景网格线
        val gridColor = Color.Gray.copy(alpha = 0.3f)
        listOf(150, 130, 110, 90, 70, 50).forEach { value ->
            drawLine(
                gridColor,
                Offset(0f, y(value)),
                Offset(size.width, y(value)),
                strokeWidth = 1f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        }

        // 2. 构建收缩压(高压)路径
        val systolicPath = Path()
        systolic.forEachIndexed { i, value ->
            val x = i * step
            val y = y(value)
            if (i == 0) systolicPath.moveTo(x, y) else systolicPath.lineTo(x, y)
        }

        // 高压渐变填充
        val systolicFillPath = Path().apply {
            addPath(systolicPath)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        drawPath(
            systolicFillPath,
            brush = Brush.verticalGradient(listOf(Color.Red.copy(alpha = 0.3f), Color.Red.copy(alpha = 0.0f)))
        )

        // 绘制高压线
        drawPath(systolicPath, Color.Red, style = Stroke(width = 5f, cap = StrokeCap.Round))

        // 3. 构建舒张压(低压)路径
        val diastolicPath = Path()
        diastolic.forEachIndexed { i, value ->
            val x = i * step
            val y = y(value)
            if (i == 0) diastolicPath.moveTo(x, y) else diastolicPath.lineTo(x, y)
        }

        // 低压渐变填充
        val diastolicFillPath = Path().apply {
            addPath(diastolicPath)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        drawPath(
            diastolicFillPath,
            brush = Brush.verticalGradient(listOf(Color.Blue.copy(alpha = 0.3f), Color.Blue.copy(alpha = 0.0f)))
        )

        // 绘制低压线
        drawPath(diastolicPath, Color.Blue, style = Stroke(width = 5f, cap = StrokeCap.Round))

        // 4. 点击选中标记
        if (selectedIndex >= 0) {
            val x = selectedIndex * step
            // 高压点
            drawCircle(Color.White, 10f, Offset(x, y(systolic[selectedIndex])))
            drawCircle(Color.Red, 7f, Offset(x, y(systolic[selectedIndex])))
            // 低压点
            drawCircle(Color.White, 10f, Offset(x, y(diastolic[selectedIndex])))
            drawCircle(Color.Blue, 7f, Offset(x, y(diastolic[selectedIndex])))

            // 竖线
            drawLine(
                Color.Gray.copy(alpha = 0.5f),
                Offset(x, 0f),
                Offset(x, size.height),
                strokeWidth = 2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f))
            )
        }
    }
}