package com.example.healthbandapp.model


data class ApiResponse<T>(

    val code: Int,

    val message: String,

    val data: T

)