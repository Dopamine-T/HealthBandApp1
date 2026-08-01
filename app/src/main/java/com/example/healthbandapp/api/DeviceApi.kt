package com.example.healthbandapp.api


import com.example.healthbandapp.model.ApiResponse
import com.example.healthbandapp.model.DeviceInfo

import retrofit2.http.GET
import retrofit2.http.Headers



interface DeviceApi {



    @Headers(

        "Content-Type: application/json;charset=UTF-8",

        "ngrok-skip-browser-warning: true"

    )


    @GET("device/info")

    suspend fun getDeviceInfo():

            ApiResponse<DeviceInfo>



}