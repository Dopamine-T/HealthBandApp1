package com.example.healthbandapp.api


import com.example.healthbandapp.model.ApiResponse
import com.example.healthbandapp.model.Warning

import retrofit2.http.GET
import retrofit2.http.Headers



interface WarningApi {



    @Headers(

        "Content-Type: application/json;charset=UTF-8",

        "ngrok-skip-browser-warning: true"

    )


    @GET("warning/list")

    suspend fun getWarningList():

            ApiResponse<List<Warning>>



}