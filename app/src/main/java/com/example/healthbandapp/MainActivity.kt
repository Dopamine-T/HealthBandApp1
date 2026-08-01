package com.example.healthbandapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import com.example.healthbandapp.api.RetrofitInstance
import com.example.healthbandapp.navigation.AppNavigation
import com.example.healthbandapp.ui.theme.HealthBandAppTheme



class MainActivity : ComponentActivity() {



    override fun onCreate(
        savedInstanceState: Bundle?
    ) {


        super.onCreate(savedInstanceState)



        /*
        初始化Retrofit

        用于读取登录后的JWT

        */

        RetrofitInstance.init(
            applicationContext
        )





        setContent {



            HealthBandAppTheme {



                Surface(

                    color = MaterialTheme.colorScheme.background

                ) {



                    AppNavigation()



                }


            }


        }


    }


}