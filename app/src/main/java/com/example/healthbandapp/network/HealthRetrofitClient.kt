package com.example.healthbandapp.network


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object HealthRetrofitClient {


    private const val BASE_URL =
        "http://garnish-chevron-backup.ngrok-free.dev/"


    val api: ApiService by lazy {


        Retrofit.Builder()

            .baseUrl(BASE_URL)

            .addConverterFactory(
                GsonConverterFactory.create()
            )

            .build()

            .create(ApiService::class.java)

    }

}