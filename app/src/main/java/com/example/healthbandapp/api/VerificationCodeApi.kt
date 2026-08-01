package com.example.healthbandapp.api


import com.example.healthbandapp.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface VerificationCodeApi {


    @GET("user/sendCode")
    suspend fun sendCode(

        @Query("phone")
        phone:String

    ): ApiResponse<String>


}