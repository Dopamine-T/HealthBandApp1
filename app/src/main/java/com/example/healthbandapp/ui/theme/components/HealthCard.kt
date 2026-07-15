package com.example.healthbandapp.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun HealthCard(
    title: String,
    value: String,
    unit: String,
    color: Color
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(title)

            Row {

                Text(
                    value,
                    style = MaterialTheme.typography.displayMedium,
                    color = color
                )

                Spacer(
                    Modifier.width(8.dp)
                )

                Text(unit)

            }

        }

    }
}