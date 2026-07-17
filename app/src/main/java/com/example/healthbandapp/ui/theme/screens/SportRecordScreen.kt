package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
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

// 模拟的历史记录数据
data class SportHistory(
    val type: String,
    val icon: ImageVector,
    val time: String,
    val duration: String,
    val calories: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportRecordScreen(navController: NavController) {

    // 模拟历史记录数据
    val historyList = listOf(
        SportHistory("户外跑步", Icons.Default.DirectionsRun, "今天 18:30", "35分钟", 320),
        SportHistory("健走", Icons.Default.DirectionsWalk, "今天 12:15", "15分钟", 80),
        SportHistory("骑行", Icons.Default.Star, "昨天 07:00", "60分钟", 550),
        SportHistory("游泳", Icons.Default.Favorite, "前天 19:00", "45分钟", 400)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("运动记录", fontWeight = FontWeight.Bold) },
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

            // 1. 顶部今日数据卡片 (带渐变色)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                )
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column {
                        Text("今日运动", color = Color.White, fontSize = 14.sp)
                        Spacer(Modifier.height(8.dp))
                        Text("8,520 步", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)

                        Spacer(Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            MiniStat(Icons.Default.Favorite, "320", "千卡")
                            MiniStat(Icons.Default.DirectionsWalk, "5.2", "公里")
                            MiniStat(Icons.Default.Star, "45", "分钟")
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // 2. 运动类型快捷入口
            Text("选择运动", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SportTypeButton(Icons.Default.DirectionsRun, "跑步", Color(0xFFE8F5E9))
                SportTypeButton(Icons.Default.DirectionsWalk, "健走", Color(0xFFFFF3E0))
                SportTypeButton(Icons.Default.Star, "骑行", Color(0xFFE3F2FD))
                SportTypeButton(Icons.Default.Favorite, "游泳", Color(0xFFE0F7FA))
            }

            Spacer(Modifier.height(24.dp))

            // 3. 历史运动记录
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("历史记录", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "查看全部 >",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { /* TODO */ }
                )
            }
            Spacer(Modifier.height(12.dp))

            // 历史记录列表
            historyList.forEach { record ->
                HistoryCard(record)
                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

// 顶部卡片的小统计项
@Composable
fun MiniStat(icon: ImageVector, value: String, unit: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(4.dp))
        Text("$value $unit", color = Color.White, fontWeight = FontWeight.Medium)
    }
}

// 运动类型圆形按钮
@Composable
fun SportTypeButton(icon: ImageVector, name: String, bgColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { /* TODO: 开始运动 */ }
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = name,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(name, fontSize = 12.sp)
    }
}

// 历史记录卡片
@Composable
fun HistoryCard(record: SportHistory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = record.icon,
                    contentDescription = record.type,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(record.type, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("${record.time} | 时长 ${record.duration}", fontSize = 12.sp, color = Color.Gray)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text("${record.calories}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text("千卡", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}