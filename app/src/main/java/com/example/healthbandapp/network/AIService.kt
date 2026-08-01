package com.example.healthbandapp.network


import retrofit2.http.Body
import retrofit2.http.POST


data class AIRequest(

    val heart_rate:Int,

    val hrv:Int,

    val sleep:Int,

    val question:String

)



data class AIResponse(

    val answer:String

)



interface AIService {


    @POST("chat")
    suspend fun chat(

        @Body request:AIRequest

    ):AIResponse


}