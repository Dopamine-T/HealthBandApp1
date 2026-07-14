package com.example.healthbandapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.healthbandapp.navigation.AppNavigation
import com.example.healthbandapp.ui.theme.HealthBandAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            HealthBandAppTheme {

                AppNavigation()

            }

        }
    }
}