package com.example.tourmanage.common.data.server.item

import com.squareup.moshi.Json

data class DetailImageItem(
    @Json(name = "contentid") val contentId: String?,
    @Json(name = "originimgurl") val originImgUrl: String?,
    @Json(name = "imgname") val imgName: String?,
    @Json(name = "smallimageurl") val smallImageUrl: String?,
    @Json(name = "cpyrhtDivCd") val cpyrhtDivCd: String?,
    @Json(name = "serialnum") val serialNum: String?,
) : CommonBodyItem()
