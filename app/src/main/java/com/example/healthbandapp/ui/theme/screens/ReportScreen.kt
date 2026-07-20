package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lightbulb
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

@Composable
fun ReportScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(Modifier.height(20.dp))

        // 页面标题
        Text(
            "今日健康报告",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "基于现代医学健康标准的综合评估",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(Modifier.height(4.dp))

        // ============================
        // 1. 健康评分 (浅绿背景)
        // ============================
        ReportItemCard(
            title = "健康评分",
            value = "92 分",
            icon = Icons.Filled.Assessment,
            isGradient = false,
            cardBgColor = Color(0xFFE8F5E9),
            iconBgColor = Color(0xFF2E7D32).copy(alpha = 0.1f),
            iconTint = Color(0xFF2E7D32)
        )

        // ============================
        // 2. 平均心率 (高级渐变色主卡片)
        // ============================
        ReportItemCard(
            title = "平均心率",
            value = "78 BPM",
            icon = Icons.Filled.Favorite,
            isGradient = true,
            cardBgColor = Color.Transparent,
            iconBgColor = Color.White.copy(alpha = 0.2f),
            iconTint = Color.White
        )

        // ============================
        // 3. 血氧 (浅青背景)
        // ============================
        ReportItemCard(
            title = "血氧饱和度",
            value = "98%",
            icon = Icons.Filled.WaterDrop,
            isGradient = false,
            cardBgColor = Color(0xFFE0F7FA),
            iconBgColor = Color(0xFF006064).copy(alpha = 0.1f),
            iconTint = Color(0xFF006064)
        )

        // ============================
        // 4. 步数 (浅橙背景)
        // ============================
        ReportItemCard(
            title = "今日步数",
            value = "6582 步",
            icon = Icons.Filled.DirectionsWalk,
            isGradient = false,
            cardBgColor = Color(0xFFFFF3E0),
            iconBgColor = Color(0xFFE65100).copy(alpha = 0.1f),
            iconTint = Color(0xFFE65100)
        )

        // ============================
        // 5. 睡眠 (浅紫背景)
        // ============================
        ReportItemCard(
            title = "睡眠时长",
            value = "7小时30分钟",
            icon = Icons.Filled.Bedtime,
            isGradient = false,
            cardBgColor = Color(0xFFF3E5F5),
            iconBgColor = Color(0xFF6A1B9A).copy(alpha = 0.1f),
            iconTint = Color(0xFF6A1B9A)
        )

        Spacer(Modifier.height(8.dp))

        // ============================
        // 6. AI 综合健康评估与建议 (新增模块)
        // ============================
        HealthAdviceCard()

        Spacer(Modifier.height(20.dp))
    }
}

// ============================
// 数据卡片组件
// ============================
@Composable
fun ReportItemCard(
    title: String,
    value: String,
    icon: ImageVector,
    isGradient: Boolean,
    cardBgColor: Color,
    iconBgColor: Color,
    iconTint: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardBgColor)
    ) {
        Box(
            modifier = Modifier.background(
                brush = if (isGradient) {
                    Brush.linearGradient(colors = listOf(Color(0xFFB71C1C), Color(0xFFE53935)))
                } else {
                    Brush.linearGradient(listOf(cardBgColor, cardBgColor))
                }
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(iconBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = title, tint = iconTint, modifier = Modifier.size(24.dp))
                }

                Spacer(Modifier.width(16.dp))

                Column {
                    Text(
                        text = title,
                        color = if (isGradient) Color.White.copy(alpha = 0.9f) else Color.Black.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = value,
                        color = if (isGradient) Color.White else Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// ============================
// 健康评估与建议卡片组件
// ============================
@Composable
fun HealthAdviceCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDE7)) // 浅黄色提示背景
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFF57F17).copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Lightbulb,
                        contentDescription = "建议",
                        tint = Color(0xFFF57F17),
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(Modifier.width(16.dp))
                Text("AI 综合健康评估", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF57F17))
            }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = Color(0xFFF57F17).copy(alpha = 0.2f))
            Spacer(Modifier.height(16.dp))

            Text("状态判断：优秀 (各项指标均在医学健康范围内)", fontWeight = FontWeight.Bold, color = Color.Black.copy(alpha = 0.8f))
            Spacer(Modifier.height(10.dp))

            Text("1. 心率与血氧：78BPM的静息心率和98%的血氧饱和度非常理想，说明您的心肺功能运转良好。", fontSize = 14.sp, color = Color.Black.copy(alpha = 0.7f), lineHeight = 20.sp)
            Spacer(Modifier.height(8.dp))

            Text("2. 睡眠质量：7.5小时睡眠完美符合成年人7-9小时的医学标准，请继续保持规律的作息。", fontSize = 14.sp, color = Color.Black.copy(alpha = 0.7f), lineHeight = 20.sp)
            Spacer(Modifier.height(8.dp))

            Text("3. 运动建议：今日步数已达标，但距离最优的8000-10000步还有提升空间。建议晚餐后进行30分钟的快走或慢跑，进一步强化心肺耐力。", fontSize = 14.sp, color = Color.Black.copy(alpha = 0.7f), lineHeight = 20.sp)
        }
    }
}