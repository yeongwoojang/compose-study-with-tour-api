package com.example.tourmanage.common.repository

import com.example.tourmanage.common.data.server.info.AreaInfo
import com.example.tourmanage.common.data.server.info.StayInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceAPI {
    companion object {
        val BASE_URL = "https://apis.data.go.kr/B551011/KorService1/"
        val API_KEY = "y4ymoIkvUP7fuqoYEvJApczpfcOpgEdejB9BjR6/Y+Ci/ZL9mskRUZghgnt2bCpHRtP1QzJ/4O+Q7x3AVAslLg=="
        val MOBILE_OS = "AND"
        val MOBILE_APP = "TourManage"
        val TYPE = "json"
    }

    @GET("searchStay1")
    suspend fun requestSearchStay(
        @Query("MobileOS") os: String = MOBILE_OS,
        @Query("MobileApp") app: String = MOBILE_APP,
        @Query("serviceKey") key: String = API_KEY,
        @Query("arrange") arrange: String = "O",
        @Query("areaCode") areaCode: String?,
        @Query("_type") type: String = TYPE
    ): StayInfo

    @GET("areaCode1")
    suspend fun requestAreaList(
        @Query("MobileOS") os: String = MOBILE_OS,
        @Query("MobileApp") app: String = MOBILE_APP,
        @Query("serviceKey") key: String = API_KEY,
        @Query("areaCode") areaCode: String? = "",
        @Query("_type") type: String = TYPE
    ): AreaInfo
}