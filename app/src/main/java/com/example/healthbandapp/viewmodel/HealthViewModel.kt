package com.example.healthbandapp.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthbandapp.model.HealthData
import com.example.healthbandapp.network.AIRequest
import com.example.healthbandapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class HealthViewModel : ViewModel() {


    /*
     * AI分析结果
     */
    private val _aiResult =
        MutableStateFlow("")


    val aiResult: StateFlow<String>
            = _aiResult



    /*
     * AI分析状态
     *
     * true  = 分析中
     * false = 空闲
     */
    private val _isAnalyzing =
        MutableStateFlow(false)


    val isAnalyzing: StateFlow<Boolean>
            = _isAnalyzing



    /*
     * 当前健康数据
     *
     * 后续这里替换成ESP32蓝牙数据
     */
    private val _healthData =
        MutableStateFlow(

            HealthData(

                heartRate = 75,

                oxygen = 98,

                temperature = 36.5,

                hrv = 65,

                sleep = 7

            )

        )



    val healthData: StateFlow<HealthData>
            = _healthData



    /*
     * 给UI显示的健康信息
     */
    private val _healthInfo =
        MutableStateFlow("")


    val healthInfo: StateFlow<String>
            = _healthInfo



    init {

        updateHealthInfo()

    }



    /*
     * 更新健康数据显示
     */
    private fun updateHealthInfo() {


        val data =
            _healthData.value



        _healthInfo.value =

            """
            心率: ${data.heartRate} bpm
            血氧: ${data.oxygen} %
            体温: ${data.temperature} ℃
            HRV: ${data.hrv} ms
            睡眠: ${data.sleep}小时
            """.trimIndent()

    }





    /*
     * 调用AI分析
     */
    fun askAI() {


        viewModelScope.launch {


            try {


                // 开始分析
                _isAnalyzing.value = true


                // 获取当前健康数据
                val data =
                    _healthData.value



                val result =

                    RetrofitClient
                        .api
                        .chat(


                            AIRequest(


                                heart_rate =
                                    data.heartRate,


                                hrv =
                                    data.hrv,


                                sleep =
                                    data.sleep,


                                question =
                                    "分析我的今天健康状态"

                            )


                        )



                // 保存AI结果

                _aiResult.value =

                    result.answer



            }
            catch (e: Exception) {


                _aiResult.value =

                    "AI连接失败:${e.message}"


            }
            finally {


                // 无论成功失败，都结束分析状态

                _isAnalyzing.value = false


            }


        }


    }



    /*
     * 更新健康数据
     *
     * 后续ESP32蓝牙接入时使用
     */
    fun updateHealthData(
        newData: HealthData
    ) {


        _healthData.value =
            newData


        updateHealthInfo()

    }


}