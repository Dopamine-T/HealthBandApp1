package com.example.healthbandapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthbandapp.ui.theme.HealthBandAppTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthBandAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {

    Row {

        Text("❤️ 心率")
        Text("82 bpm")

        Text("")

        Text("🩸 血氧")
        Text("98%")

        Text("")

        Text("🚶 今日步数")
        Text("6582")

        Button(
            onClick = {

            }
        ) {
            Text("连接手环")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HealthBandAppTheme {
        Greeting()
    }
}