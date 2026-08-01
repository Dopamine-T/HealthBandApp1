package com.example.healthbandapp.api


import com.example.healthbandapp.model.ApiResponse
import com.example.healthbandapp.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST



interface RegisterApi {


    @Headers(

        "Content-Type: application/json;charset=UTF-8",

        "ngrok-skip-browser-warning: true"

    )


    @POST("user/register")

    suspend fun register(

        @Body request:RegisterRequest

    ):
            ApiResponse<String>


}