package com.example.tourmanage.ui.staydetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.R
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.data.server.item.StayDetailItem
import com.example.tourmanage.common.extension.downsizeString
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.components.BottomSheet
import com.example.tourmanage.viewmodel.StayDetailViewModel
import timber.log.Timber

@Composable
fun StayOverview(detailData: StayDetailItem, paddingModifier: Modifier) {
    Timber.i("OverViewLayout")
    val overviewText = detailData.overview.isEmptyString().downsizeString()
    val moreViewText = "더보기"
    var annotatedString: AnnotatedString? = null
    var isMoreOptionYn by remember { mutableStateOf(overviewText.length > 80) }
    if (isMoreOptionYn) {
        annotatedString = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontSize = 11.sp)
            ) {
                append(overviewText)
            }
            pushStringAnnotation(tag = "Clickable", annotation = moreViewText)
            withStyle(style = SpanStyle(color = colorResource(id = R.color.cornflower_blue), fontSize = 11.sp, textDecoration = TextDecoration.None)) {
                append(moreViewText)
            }
            pop()
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(id = R.color.white_smoke),
        thickness = 1.dp,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Box(modifier = paddingModifier) {
        if (isMoreOptionYn) {
            ClickableText(
                text = annotatedString!!,
                onClick = {
                    isMoreOptionYn = false
                }
            )
        } else {
            Text(
                text = detailData.overview.isEmptyString(),
                fontSize = 11.sp
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(id = R.color.white_smoke),
        thickness = 8.dp,
    )
}

@Composable
fun OptionLayout(optionInfos: ArrayList<DetailItem>, paddingModifier: Modifier) {
    Timber.i("optionInfos() | size: ${optionInfos.size}")
    val size = optionInfos.size
    Column() {
        optionInfos.forEachIndexed { index, optionItem ->
            StayRooms(optionItem, index, size, paddingModifier)
        }
    }
}