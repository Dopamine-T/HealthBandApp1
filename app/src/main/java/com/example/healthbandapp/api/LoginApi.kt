package com.example.healthbandapp.api


import com.example.healthbandapp.model.ApiResponse
import com.example.healthbandapp.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST



interface LoginApi {


    @Headers(

        "Content-Type: application/json;charset=UTF-8",

        "ngrok-skip-browser-warning: true"

    )


    @POST("user/login")

    suspend fun login(

        @Body request: LoginRequest

    ): ApiResponse<String>


}