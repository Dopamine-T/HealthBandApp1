package com.example.healthbandapp.api


import android.content.Context
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Inet4Address
import java.net.InetAddress



object RetrofitInstance {



    private const val BASE_URL =
        "https://garnish-chevron-backup.ngrok-free.dev/"



    private lateinit var appContext: Context



    fun init(context: Context){

        appContext =
            context.applicationContext

    }





    /*
    网络日志
     */

    private val logger =
        HttpLoggingInterceptor().apply {

            level =
                HttpLoggingInterceptor.Level.BODY

        }







    /*
    强制IPv4
     */

    private val ipv4Dns = object : Dns {


        override fun lookup(
            hostname:String
        ):List<InetAddress>{


            return InetAddress
                .getAllByName(hostname)
                .filterIsInstance<Inet4Address>()


        }


    }








    /*
    OkHttp客户端
     */

    private val client =

        OkHttpClient.Builder()



            // IPv4

            .dns(ipv4Dns)




            /*
            自动添加JWT
             */

            .addInterceptor {


                    chain ->



                val token =


                    if(::appContext.isInitialized){


                        appContext

                            .getSharedPreferences(

                                "global_prefs",

                                Context.MODE_PRIVATE

                            )

                            .getString(

                                "token",

                                ""

                            )


                    }else{

                        ""

                    }







                val request =


                    chain.request()

                        .newBuilder()

                        .addHeader(

                            "Authorization",

                            "Bearer $token"

                        )

                        .build()






                chain.proceed(request)



            }





            // 请求日志

            .addInterceptor(logger)




            // 防止重复连接

            .retryOnConnectionFailure(false)



            .build()









    /*
    Retrofit核心
     */


    private val retrofit =


        Retrofit.Builder()


            .baseUrl(BASE_URL)


            .client(client)


            .addConverterFactory(

                GsonConverterFactory.create()

            )


            .build()










    /*
    Warning接口
     */


    val warningApi:WarningApi by lazy {


        retrofit.create(

            WarningApi::class.java

        )


    }



    //兼容旧代码

    val api:WarningApi

        get() = warningApi











    /*
    Device接口
     */


    val deviceApi:DeviceApi by lazy {


        retrofit.create(

            DeviceApi::class.java

        )


    }









    /*
    验证码接口
     */


    val verificationCodeApi:VerificationCodeApi by lazy {


        retrofit.create(

            VerificationCodeApi::class.java

        )


    }











    /*
    注册接口
     */


    val registerApi:RegisterApi by lazy {


        retrofit.create(

            RegisterApi::class.java

        )


    }









    /*
    登录接口
     */


    val loginApi:LoginApi by lazy {


        retrofit.create(

            LoginApi::class.java

        )


    }

    /*
用户信息接口
*/


    val userApi:UserApi by lazy {


        retrofit.create(

            UserApi::class.java

        )


    }



}