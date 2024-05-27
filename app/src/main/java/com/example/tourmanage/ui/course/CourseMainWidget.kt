package com.example.tourmanage.ui.course

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.viewmodel.CourseViewModel
import timber.log.Timber

@Composable
fun CourseMainWidget(viewModel: CourseViewModel = hiltViewModel(), curParentArea: AreaItem?, curChildArea: AreaItem?) {
    LaunchedEffect(key1 = Unit) {
        Timber.i("TEST_LOG | request Start")
//        if (curParentArea != null && curChildArea != null) {
//            viewModel.requestCourse(curParentArea, curChildArea)
//        }
//        viewModel.requestCourse()
//        viewModel.requestMyLocationInfo(Config.CONTENT_TYPE_ID.TOUR_COURSE)
        viewModel.requestCourseInfo(Config.CONTENT_TYPE_ID.TOUR_COURSE, curParentArea, curChildArea)
    }
    
    val curAreaTourCourse = viewModel.curAreaTourCourse.collectAsStateWithLifecycle()
    val allCourseItems = viewModel.allAreaTourCourse.collectAsStateWithLifecycle()
    val myAreaTourCourse = viewModel.myAreaTourCourse.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Header(menuName = "여행코스")
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    top = it.calculateTopPadding() + 20.dp,
                    bottom = it.calculateBottomPadding()
                ),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(25.dp),
            ) {
                if (curAreaTourCourse.isSuccess()) {
                    val curCourseData = curAreaTourCourse.value.data!!
                    item {
                        CurCourseList(curCourseData, curParentArea, curChildArea)
                    }
                }

                if (myAreaTourCourse.isSuccess()) {
                    val myCourseData = myAreaTourCourse.value.data!!
                    item {
                        MyCourseList(courseItems = myCourseData)
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
                        AllCourseItemWidget(allCourseItem = allCourseItem)
                    }
                }
            }
        }

        if (curAreaTourCourse.isLoading() || allCourseItems.isLoading()) {
            LoadingWidget()
        }
    }
}