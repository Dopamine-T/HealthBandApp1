package com.example.healthbandapp.api


import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response



class TokenInterceptor(

    private val context: Context

) : Interceptor {



    override fun intercept(

        chain: Interceptor.Chain

    ): Response {



        val prefs =

            context.getSharedPreferences(

                "global_prefs",

                Context.MODE_PRIVATE

            )



        val token =

            prefs.getString(

                "token",

                ""

            )




        val request =


            chain.request()

                .newBuilder()

                .addHeader(

                    "Authorization",

                    "Bearer $token"

                )

                .build()



        return chain.proceed(request)



    }


}