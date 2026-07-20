package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthbandapp.ui.theme.card.*

@Composable
fun HealthScreen(
    navController: NavHostController,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()) // 支持上下滚动
    ) {
        Spacer(Modifier.height(20.dp))

        Text(
            text = "💚 健康关怀",
            fontSize = 26.sp
        )

        Spacer(Modifier.height(20.dp))

        // ===== 直接放入 6 个可点击的卡片组件 =====

        // 1. 心率
        HeartCard(navController)
        Spacer(Modifier.height(16.dp))

        // 2. 血氧
        OxygenCard(navController)
        Spacer(Modifier.height(16.dp))

        // 3. 睡眠
        SleepSummaryCard(navController)
        Spacer(Modifier.height(16.dp))

        // 4. 体温 & HRV
        HrvCard(navController)
        Spacer(Modifier.height(16.dp))

        // 5. 血压
        BloodPressureCard(navController)
        Spacer(Modifier.height(16.dp))

        // 6. 健康报告
        ReportSummaryCard(navController)

        Spacer(Modifier.height(20.dp))
    }
}