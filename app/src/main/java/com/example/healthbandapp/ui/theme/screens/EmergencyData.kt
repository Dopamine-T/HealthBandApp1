package com.example.healthbandapp.ui.theme.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

object EmergencyData {
    var name by mutableStateOf("健康用户")
    var phone by mutableStateOf("13800138000")
    var blood by mutableStateOf("未设置")
    var contact by mutableStateOf("未设置")
    var allergy by mutableStateOf("无")
}