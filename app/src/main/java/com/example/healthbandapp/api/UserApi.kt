package com.example.healthbandapp.api


import com.example.healthbandapp.model.ApiResponse
import com.example.healthbandapp.model.UserProfile

import retrofit2.http.GET
import retrofit2.http.Headers



interface UserApi {



    @Headers(

        "Content-Type: application/json;charset=UTF-8",

        "ngrok-skip-browser-warning: true"

    )


    @GET("user/me")

    suspend fun getUserProfile():

            ApiResponse<UserProfile>



}