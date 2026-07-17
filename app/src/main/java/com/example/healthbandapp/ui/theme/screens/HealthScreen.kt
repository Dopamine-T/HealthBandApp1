package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.healthbandapp.ui.theme.card.BloodPressureCard
import com.example.healthbandapp.ui.theme.card.HeartCard
import com.example.healthbandapp.ui.theme.card.HrvCard
import com.example.healthbandapp.ui.theme.card.OxygenCard
import com.example.healthbandapp.ui.theme.card.ReportCard
import com.example.healthbandapp.ui.theme.card.SleepCard

@Composable
fun HealthScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .navigationBarsPadding() // 替代原有硬编码的 90.dp，自动适配底部手势条
    ) {
        HeartCard(navController)
        Spacer(modifier = Modifier.height(16.dp))

        OxygenCard(navController)
        Spacer(modifier = Modifier.height(16.dp))

        SleepCard(navController)
        Spacer(modifier = Modifier.height(16.dp))

        HrvCard(navController)
        Spacer(modifier = Modifier.height(16.dp))

        BloodPressureCard(navController)
        Spacer(modifier = Modifier.height(16.dp))

        ReportCard(navController)
        Spacer(modifier = Modifier.height(20.dp))
    }
}