package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("关于我们", fontWeight = FontWeight.Bold) },
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(40.dp))

            // 1. 应用 Logo 占位符 (使用心跳图标代替)
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "App Logo",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(80.dp)
            )

            Spacer(Modifier.height(16.dp))

            // 2. 应用名称和版本号
            Text(
                text = "HealthBand",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Version 1.0.0",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(24.dp))

            // 3. 应用简介
            Text(
                text = "HealthBand 致力于为您提供全面的健康数据管理。包括日常步数追踪、心率监测、睡眠分析以及紧急医疗信息卡，做您手腕上的健康卫士。",
                modifier = Modifier.padding(horizontal = 32.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 22.sp
            )

            Spacer(Modifier.height(40.dp))

            // 4. 功能列表项
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column {
                    AboutListItem(
                        icon = Icons.Default.Share,
                        title = "分享应用",
                        onClick = { /* TODO: 调起系统分享 */ }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 72.dp))

                    AboutListItem(
                        icon = Icons.Default.Description,
                        title = "用户协议",
                        onClick = { /* TODO: 跳转用户协议页面 */ }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 72.dp))

                    AboutListItem(
                        icon = Icons.Default.PrivacyTip,
                        title = "隐私政策",
                        onClick = { /* TODO: 跳转隐私政策页面 */ }
                    )
                }
            }

            Spacer(Modifier.weight(1f)) // 将底部版权信息推到页面最下方

            // 5. 底部版权信息
            Text(
                text = "© 2023-2024 HealthBand Team\nAll Rights Reserved",
                modifier = Modifier.padding(bottom = 24.dp, top = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// 抽取的列表项组件
@Composable
fun AboutListItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(28.dp)
        )
        Spacer(Modifier.width(24.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.weight(1f))
        // 右侧的小箭头
        Text(
            text = ">",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}