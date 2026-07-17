package com.example.healthbandapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyCardScreen(navController: NavController) {

    val name = EmergencyData.name
    val phone = EmergencyData.phone
    val blood = EmergencyData.blood
    val contact = EmergencyData.contact
    val allergy = EmergencyData.allergy

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("医疗急救卡", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    titleContentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "⚠️ 紧急情况下，请向急救人员出示此页面",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(16.dp))

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InfoRow(Icons.Default.Person, "姓名", name)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    InfoRow(Icons.Default.Phone, "手机号", phone)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    InfoRow(Icons.Default.Bloodtype, "血型", blood)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    InfoRow(Icons.Default.ContactPhone, "紧急联系人", contact)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    InfoRow(Icons.Default.Warning, "过敏史", allergy)
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("emergencyEdit") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("编辑急救信息", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(28.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}