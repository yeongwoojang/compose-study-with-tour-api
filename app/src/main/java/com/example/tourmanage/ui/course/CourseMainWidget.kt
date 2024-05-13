package com.example.tourmanage.ui.course

import android.annotation.SuppressLint
import android.location.Address
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.R
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaBasedItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.CourseViewModel

@Composable
fun CourseMainWidget(viewModel: CourseViewModel = hiltViewModel()) {
    LaunchedEffect(key1 = Unit) {
        val parentArea = ServerGlobal.getCurrentParentArea()
        val childArea = ServerGlobal.getCurrentChildArea()
        if (parentArea != null && childArea != null) {
            viewModel.requestCourse(parentArea, childArea)
        }
        viewModel.requestCourse()
    }
    
    val myCourseItems = viewModel.myAreaTourCourse.collectAsStateWithLifecycle()
    val allCourseItems = viewModel.allAreaTourCourse.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Header(menuName = "여행코스")
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = it.calculateTopPadding() + 20.dp, bottom = it.calculateBottomPadding()),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(25.dp),
            ) {
                if (myCourseItems.isSuccess()) {
                    val courseData = myCourseItems.value.data!!
                    item {
                        MyAreaCourseList(courseData)
                    }
                }

                if (allCourseItems.isSuccess()) {
                    val allCourse = allCourseItems.value.data!!
                    item {
                        AllCourseList()
                    }
                    itemsIndexed(
                        items = allCourse,
                        key = {index, allCourseItem ->
                            allCourseItem.contentId.isEmptyString()
                        }
                    ) {index, allCourseItem ->
                        AllCourseItemWidget(allCourseItem)
                    }
                }
            }
        }

        if (myCourseItems.isLoading() || allCourseItems.isLoading()) {
            LoadingWidget()
        }
    }
        }


@Composable
fun AllCourseList() {
    Text(
        text = "한국 전국을 아우르는 다양한 여행 코스를 소개합니다.",
        style = TextStyle(
            fontSize= 14.sp,
            fontFamily = spoqaHanSansNeoFont,
            fontWeight = FontWeight.Normal
        )
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "한국 곳곳의 신비로운 여행 코스를 탐험해보세요.",
        style = TextStyle(
            color = colorResource(id = R.color.dark_gray),
            fontSize= 11.sp,
            fontFamily = spoqaHanSansNeoFont,
            fontWeight = FontWeight.Thin
        )
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AllCourseItemWidget(allCourseItem: AreaBasedItem) {
    val context = LocalContext.current
    var address by remember { mutableStateOf<Address?>(null) }

    LaunchedEffect(key1 = Unit) {
        address = ServerGlobal.getAddress(context, allCourseItem.mapY.isEmptyString("0").toDouble(), allCourseItem.mapX.isEmptyString("0").toDouble())
    }

    Column{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(end = 16.dp)
                .shadow(4.dp, shape = RoundedCornerShape(12.dp))
        ) {
            GlideImage(
                model = allCourseItem.fullImageUrl,
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "${allCourseItem.title}",
            style = TextStyle(
                fontSize= 13.sp,
                fontFamily = spoqaHanSansNeoFont,
                fontWeight = FontWeight.Normal
            )
        )
        if (address != null) {
            val addressText = "${address!!.adminArea.isEmptyString()} ${address!!.locality.isEmptyString()} ${address!!.thoroughfare.isEmptyString()}"
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = addressText,
                style = TextStyle(
                    color = colorResource(id = R.color.dark_gray),
                    fontSize = 11.sp,
                    fontFamily = spoqaHanSansNeoFont,
                    fontWeight = FontWeight.Thin
                )
            )
        }
    }
}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MyAreaCourseList(courseItems: ArrayList<AreaBasedItem>) {
    val parentArea = ServerGlobal.getCurrentParentArea()
    val childArea = ServerGlobal.getCurrentChildArea()

    Text(
        text = "${parentArea?.name} ${childArea?.name}의 여행 코스",
        style = TextStyle(
            fontSize= 14.sp,
            fontFamily = spoqaHanSansNeoFont,
            fontWeight = FontWeight.Normal
        )
    )
    Spacer(modifier = Modifier.height(20.dp))
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(
            items = courseItems,
            key = { index, courseItem ->
                courseItem.contentId.isEmptyString()
            }
        ) {index, courseItem ->
            Column(modifier = Modifier
                .width(150.dp)
            ) {
                GlideImage(
                    model = courseItem.fullImageUrl,
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(12.dp)),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = courseItem.title.isEmptyString(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily =  spoqaHanSansNeoFont,
                        fontWeight = FontWeight.Medium,
                    )
                )
            }
        }
    }
}