package com.example.healthbandapp.model


data class Warning(

    val id: Int,

    val content: String,

    val level: String,

    val status: String,

    val type: String,

    val userId: Int

)