package com.example.tourmanage.common.repository

import com.example.tourmanage.common.data.server.info.*
import com.example.tourmanage.common.value.Config
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
        @Query("arrange") arrange: String = Config.ARRANGE_TYPE.O.value,
        @Query("areaCode") areaCode: String? = "",
        @Query("sigunguCode") sigunguCode: String? = "",
        @Query("_type") type: String = TYPE,
        ): StayInfo

    @GET("areaCode1")
    suspend fun requestAreaList(
        @Query("MobileOS") os: String = MOBILE_OS,
        @Query("MobileApp") app: String = MOBILE_APP,
        @Query("serviceKey") key: String = API_KEY,
        @Query("areaCode") areaCode: String? = "",
        @Query("numOfRows") numOfRows: String = "30",
        @Query("_type") type: String = TYPE
    ): AreaInfo

    @GET("detailCommon1")
    suspend fun requestStayDetailInfo(
        @Query("MobileOS") os: String = MOBILE_OS,
        @Query("MobileApp") app: String = MOBILE_APP,
        @Query("serviceKey") key: String = API_KEY,
        @Query("contentId") contentId: String = "",
        @Query("contentTypeId") contentTypeId: String = "",
        @Query("defaultYN") defaultYn: String? = "Y",
        @Query("firstImageYN") mainImageYn: String? = "Y",
        @Query("areacodeYN") areaCodeYn: String? = "Y",
        @Query("catcodeYN") catCodeYn: String? = "Y",
        @Query("addrinfoYN") addrInfoYn: String? = "Y",
        @Query("mapinfoYN") mapInfoYn: String? = "Y",
        @Query("overviewYN") overviewYn: String? = "Y",
        @Query("_type") type: String = TYPE
    ): DetailCommonInfo

    @GET("detailInfo1")
    suspend fun requestOptionInfo(
        @Query("MobileOS") os: String = MOBILE_OS,
        @Query("MobileApp") app: String = MOBILE_APP,
        @Query("serviceKey") key: String = API_KEY,
        @Query("contentId") contentId: String = "",
        @Query("contentTypeId") contentTypeId: String? = "",
        @Query("_type") type: String = TYPE
    ): DetailInfo

    @GET("searchFestival1")
    suspend fun requestFestivalInfo(
        @Query("MobileOS") os: String = MOBILE_OS,
        @Query("MobileApp") app: String = MOBILE_APP,
        @Query("serviceKey") key: String = API_KEY,
        @Query("areaCode") areaCode: String? = "",
        @Query("_type") type: String = TYPE,
        @Query("eventStartDate") eventStartDate: String? = "",
        @Query("listYN") listYn: String = "Y",
        @Query("arrange") arrange: String = Config.ARRANGE_TYPE.O.value,
    ): FestivalInfo

    @GET("areaBasedList1")
    suspend fun requestAreaBasedList(
        @Query("MobileOS") os: String = MOBILE_OS,
        @Query("MobileApp") app: String = MOBILE_APP,
        @Query("serviceKey") key: String = API_KEY,
        @Query("areaCode") areaCode: String?,
        @Query("sigunguCode") sigunguCode: String? = "",
        @Query("_type") type: String = TYPE,
        @Query("contentTypeId") contentType: String? = "12",
        @Query("arrange") arrange: String? = Config.ARRANGE_TYPE.O.value
    ): AreaBasedInfo

    @GET("locationBasedList1")
    suspend fun requestLocationBasedList(
        @Query("MobileOS") os: String = MOBILE_OS,
        @Query("MobileApp") app: String = MOBILE_APP,
        @Query("_type") type: String = TYPE,
        @Query("listYN") listYn: String = "Y",
        @Query("arrange") arrange: String? = Config.ARRANGE_TYPE.O.value,
        @Query("mapX") mapX: String,
        @Query("mapY") mapY: String,
        @Query("radius") radius: String? = "5000",
        @Query("contentTypeId") contentTypeId: String,
        @Query("serviceKey") key: String = API_KEY,
    ): LocationBasedInfo

    @GET("detailInfo1")
    suspend fun requestDetailInfo(
        @Query("MobileOS") os: String = MOBILE_OS,
        @Query("MobileApp") app: String = MOBILE_APP,
        @Query("serviceKey") key: String = API_KEY,
        @Query("contentId") contentId: String = "",
        @Query("contentTypeId") contentTypeId: String? = "",
        @Query("_type") type: String = TYPE
    ): DetailInfo

    @GET("detailCommon1")
    suspend fun requestDetailCommonInfo(
        @Query("MobileOS") os: String = MOBILE_OS,
        @Query("MobileApp") app: String = MOBILE_APP,
        @Query("serviceKey") key: String = API_KEY,
        @Query("contentId") contentId: String = "",
        @Query("contentTypeId") contentTypeId: String = "",
        @Query("defaultYN") defaultYn: String? = "Y",
        @Query("firstImageYN") mainImageYn: String? = "Y",
        @Query("areacodeYN") areaCodeYn: String? = "Y",
        @Query("catcodeYN") catCodeYn: String? = "Y",
        @Query("addrinfoYN") addrInfoYn: String? = "Y",
        @Query("mapinfoYN") mapInfoYn: String? = "Y",
        @Query("overviewYN") overviewYn: String? = "Y",
        @Query("_type") type: String = TYPE
    ): DetailCommonInfo

    @GET("detailImage1")
    suspend fun requestDetailImage(
        @Query("MobileOS") os: String = MOBILE_OS,
        @Query("MobileApp") app: String = MOBILE_APP,
        @Query("serviceKey") key: String = API_KEY,
        @Query("contentId") contentId: String = "",
        @Query("imageYN") imageYN: String? = "Y",
        @Query("subImageYN") subImageYN: String? = "Y",
        @Query("_type") type: String = TYPE
    ): DetailImageInfo

    @GET("areaBasedSyncList1")
    suspend fun requestTourInfo(
        @Query("MobileOS") os: String = MOBILE_OS,
        @Query("MobileApp") app: String = MOBILE_APP,
        @Query("serviceKey") key: String = API_KEY,
        @Query("_type") type: String = TYPE,
        @Query("contentTypeId") contentTypeId: String = "",
        @Query("numOfRows") numOfRows: String,
        @Query("pageNo") pageNo: String
    ): TourInfo
}