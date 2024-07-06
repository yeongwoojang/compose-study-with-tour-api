package com.example.tourmanage.ui.staydetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.tourmanage.R
import com.example.tourmanage.common.data.server.item.DetailCommonItem
import com.example.tourmanage.common.extension.downsizeString
import com.example.tourmanage.common.extension.isEmptyString
import timber.log.Timber

@Composable
fun StayOverview(detailData: DetailCommonItem, paddingModifier: Modifier) {
    Timber.i("OverViewLayout")
    val overviewText = detailData.overview.isEmptyString().downsizeString(80)
    val moreViewText = "더보기"
    var annotatedString: AnnotatedString? = null
    //_ LazyColumn은 뷰들이 재사용 되기 때문에 remember로 상태를 저장해도 뷰를 보이지 않을떄까지 스크롤했다가 다시 돌아오도록 스크롤하면 상태 값이 초기화되어있음.
    //_ 이 때 상태를 저장하기 위해 rememberSaveable을 사용하거나 LazyColumn 바깥에 상태 값을 선언해야한다.
    //_ rememberSaveable은 화면 회전 같은 경우에도 상태가 날아가기 때문에 사용 되기도 한다.
    var isMoreOptionYn by rememberSaveable{ mutableStateOf(overviewText.length > 80) }
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
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = colorResource(id = R.color.white_smoke)
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
}