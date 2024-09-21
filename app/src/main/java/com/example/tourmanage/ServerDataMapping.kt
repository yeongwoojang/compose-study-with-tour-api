package com.example.tourmanage

import com.example.tourmanage.common.data.server.info.*
import com.example.tourmanage.common.data.server.item.*
import com.example.tourmanage.common.extension.formatCoordinate
import com.example.tourmanage.common.extension.isBooleanYn
import com.example.tourmanage.data.home.PosterItem
import java.util.regex.Pattern

fun StayInfo.toStayInfoList(): ArrayList<StayItem> {
    return response?.body?.items?.item?.let {
        it as ArrayList<StayItem>
    } ?: ArrayList(emptyList())
}

fun AreaInfo.toAreaInfoList(): ArrayList<AreaItem> {
    return response?.body?.items?.item?.let {
        it as ArrayList<AreaItem>
    } ?: ArrayList(emptyList())
}

fun DetailCommonInfo.toDetailCommonItem(): DetailCommonItem? {
    return response?.body?.items?.item?.let {

        it[0].apply {
            val regex = """href="([^"]+)"""".toRegex()
            if (!hompageUrl.isNullOrEmpty()) {
                val matchResult = regex.find(hompageUrl!!)
                hompageUrl = matchResult?.groupValues?.get(1)
            }
        }
        it[0]
    }
}

fun DetailInfo.toDetailItems(): ArrayList<DetailItem> {
    return response?.body?.items?.item?.let {
        it as ArrayList<DetailItem>
        it.forEach { item ->
            val fullText = item.infoText
            if (!fullText.isNullOrEmpty()) {
                val htmlPattern = Pattern.compile("<.*?>")
                // 정규 표현식을 이용하여 태그 제거
                val noHtml = htmlPattern.matcher(fullText).replaceAll("")
                item.infoText = noHtml
            }
        }
        it
    } ?: ArrayList(emptyList())
}

fun FestivalInfo.toFestivalItems(): ArrayList<FestivalItem> {
    return response?.body?.items?.item?.let {
        it as ArrayList<FestivalItem>
    } ?: ArrayList(emptyList())
}

fun StayInfo.toPosterItem(): List<PosterItem> {
    return response?.body?.items?.item?.let {
        it.map { stayItem ->
            PosterItem(
                contentId = stayItem.contentId.orEmpty(),
                contentTypeId = stayItem.contentTypeId.orEmpty(),
                imgUrl = stayItem.fullImageUrl.orEmpty(),
                title = stayItem.title.orEmpty()
            )
        }
    } ?: ArrayList(emptyList())
}

fun FestivalInfo.toPosterItem(): List<PosterItem> {
    return response?.body?.items?.item?.let {
        it.map { festivalItem ->
            PosterItem(
                contentId = festivalItem.contentId.orEmpty(),
                contentTypeId = festivalItem.contentTypeId.orEmpty(),
                imgUrl = festivalItem.mainImage.orEmpty(),
                title = festivalItem.title.orEmpty()
            )
        }
    } ?: ArrayList(emptyList())
}

fun AreaBasedInfo.toAreaBasedInfoItems(): ArrayList<AreaBasedItem> {
    return response?.body?.items?.item?.let {
        it as ArrayList<AreaBasedItem>
    } ?: ArrayList(emptyList())
}

fun LocationBasedInfo.toLocationBasedList(): ArrayList<LocationBasedItem> {
    return response?.body?.items?.item?.let {
        it as ArrayList<LocationBasedItem>
    } ?: ArrayList(emptyList())
}

fun DetailImageInfo.toDetailImageLise(): ArrayList<DetailImageItem> {
    return response?.body?.items?.item?.let {
        it as ArrayList<DetailImageItem>
    } ?: ArrayList(emptyList())
}

fun TourInfo.toPosterItem(): List<PosterItem> {
    return response?.body?.items?.item?.let {
        it.map { tourItem ->
            PosterItem(
                contentId = tourItem.contentid.orEmpty(),
                contentTypeId = tourItem.contenttypeid.orEmpty(),
                imgUrl = tourItem.firstimage.orEmpty(),
                title = tourItem.title.orEmpty()
            )
        }
    } ?: ArrayList(emptyList())
}

fun SearchItem.convertXY(): SearchItem {
    return SearchItem(
        mapx = this.mapx.formatCoordinate(),
        mapy = this.mapy.formatCoordinate()
    )
}

fun DetailItem.getOptionString(): String {
    var options = ""
    if (airConditionYn.isBooleanYn()) {
        options += "에어컨 | "
    }
    if (bathFacilityYn.isBooleanYn()) {
        options += "욕조 | "
    }
    if (roomPcYn.isBooleanYn()) {
        options += "PC | "
    }
    if (roomTvYn.isBooleanYn()) {
        options += "TV | "
    }
    if (roomInternetYn.isBooleanYn()) {
        options += "인터넷 | "
    }
    if (roomCookYn.isBooleanYn()) {
        options += "취사 가능 | "
    }
    if (roomSofaYn.isBooleanYn()) {
        options += "소파 | "
    }
    if (roomRefrigeratorYn.isBooleanYn()) {
        options += "냉장고"
    }

    return options
}