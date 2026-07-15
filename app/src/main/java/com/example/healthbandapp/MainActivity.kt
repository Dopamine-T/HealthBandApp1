package com.example.healthbandapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.OnBackPressedCallback
import com.example.healthbandapp.navigation.AppNavigation
import com.example.healthbandapp.ui.theme.HealthBandAppTheme


class MainActivity : ComponentActivity() {


    private var lastBackTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()


        // 双击退出

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {


                override fun handleOnBackPressed() {


                    val currentTime = System.currentTimeMillis()


                    if (currentTime - lastBackTime < 2000) {


                        finish()


                    } else {


                        lastBackTime = currentTime


                        Toast.makeText(
                            this@MainActivity,
                            "再按一次退出应用",
                            Toast.LENGTH_SHORT
                        ).show()


                    }

                }

            }
        )



        setContent {


            HealthBandAppTheme {


                AppNavigation()


            }


        }


    }


}