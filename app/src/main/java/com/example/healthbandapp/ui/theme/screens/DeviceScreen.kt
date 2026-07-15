package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DeviceScreen() {
    var isConnected by remember { mutableStateOf(false) }
    var isSyncing by remember { mutableStateOf(false) }
    var isFinding by remember { mutableStateOf(false) }
    val deviceName = "MyBand X1"
    val macAddress = "AA:BB:CC:DD:EE:FF"
    val firmwareVersion = "V1.2.3"
    val batteryLevel = 78
    val isCharging = false

    var wristRaiseEnabled by remember { mutableStateOf(true) }
    var sedentaryReminderEnabled by remember { mutableStateOf(false) }
    var sedentaryInterval by remember { mutableStateOf(60) }
    var doNotDisturbEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // 1. 连接状态
        val stateColor = if (isConnected) Color(0xFF4CAF50) else Color(0xFF9E9E9E)
        val stateText = if (isConnected) "已连接" else "未连接"

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(stateColor)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = "🔗 连接状态", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stateText,
                        style = MaterialTheme.typography.headlineSmall,
                        color = stateColor
                    )
                }
            }
        }

        // 2. 设备核心信息
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "📱 设备信息", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(10.dp))
                Divider()
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("设备名称", color = Color.Gray)
                    Text(deviceName, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("MAC 地址", color = Color.Gray)
                    Text(
                        macAddress,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF555555)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("固件版本", color = Color.Gray)
                    Text(
                        firmwareVersion,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF555555)
                    )
                }
            }
        }

        // 3. 电池电量
        val batteryColor = when {
            isCharging -> Color(0xFF00BCD4)
            batteryLevel >= 60 -> Color(0xFF4CAF50)
            batteryLevel >= 20 -> Color(0xFFFFC107)
            else -> Color(0xFFF44336)
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🔋 电池电量", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "$batteryLevel%" + if (isCharging) " ⚡充电中" else "",
                        style = MaterialTheme.typography.headlineSmall,
                        color = batteryColor
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = { batteryLevel / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = batteryColor,
                    trackColor = Color(0xFFE0E0E0),
                )
            }
        }

        // 4. 同步数据
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                isSyncing = true
            },
            enabled = isConnected && !isSyncing,
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = Color(0xFFBDBDBD)
            )
        ) {
            if (isSyncing) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("正在同步…")
            } else {
                Text("🔄 同步数据")
            }
        }

        // 5. 查找手环
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                isFinding = true
            },
            enabled = isConnected && !isFinding,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF9800),
                disabledContainerColor = Color(0xFFBDBDBD)
            )
        ) {
            if (isFinding) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("正在查找…")
            } else {
                Text("🔊 查找手环")
            }
        }

        // 6. 设备设置
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "⚙️ 设备设置", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(10.dp))
                Divider()
                Spacer(modifier = Modifier.height(10.dp))

                // 抬腕亮屏
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("抬腕亮屏", fontWeight = FontWeight.SemiBold)
                        Text(
                            "抬起手腕时自动点亮屏幕",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                    Switch(
                        checked = wristRaiseEnabled,
                        onCheckedChange = { wristRaiseEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF4CAF50)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = Color(0xFFF0F0F0))
                Spacer(modifier = Modifier.height(8.dp))

                // 久坐提醒
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("久坐提醒", fontWeight = FontWeight.SemiBold)
                            Text(
                                if (sedentaryReminderEnabled) "每 $sedentaryInterval 分钟提醒一次"
                                else "长时间不动时提醒你活动",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                        Switch(
                            checked = sedentaryReminderEnabled,
                            onCheckedChange = { sedentaryReminderEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF4CAF50)
                            )
                        )
                    }

                    if (sedentaryReminderEnabled) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(30, 45, 60, 90).forEach { minutes ->
                                val isSelected = sedentaryInterval == minutes
                                Button(
                                    modifier = Modifier.weight(1f).height(36.dp),
                                    onClick = { sedentaryInterval = minutes },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isSelected)
                                            Color(0xFF1976D2) else Color(0xFFE0E0E0)
                                    ),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text(
                                        "${minutes}分钟",
                                        color = if (isSelected) Color.White else Color(0xFF555555),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = Color(0xFFF0F0F0))
                Spacer(modifier = Modifier.height(8.dp))

                // 勿扰模式
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("勿扰模式", fontWeight = FontWeight.SemiBold)
                        Text(
                            "开启后手环不接收消息通知",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                    Switch(
                        checked = doNotDisturbEnabled,
                        onCheckedChange = { doNotDisturbEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF4CAF50)
                        )
                    )
                }
            }
        }

        // 7. 解绑/重置设备
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                // TODO: 解除绑定逻辑
            },
            enabled = isConnected,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF44336),
                disabledContainerColor = Color(0xFFBDBDBD)
            )
        ) {
            Text("解除绑定")
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}