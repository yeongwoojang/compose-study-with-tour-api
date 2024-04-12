    package com.example.tourmanage.ui

    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.viewModels
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.shape.CornerSize
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.foundation.text.ClickableText
    import androidx.compose.material.*
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Close
    import androidx.compose.material.icons.filled.Menu
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.res.colorResource
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.AnnotatedString
    import androidx.compose.ui.text.SpanStyle
    import androidx.compose.ui.text.buildAnnotatedString
    import androidx.compose.ui.text.font.FontStyle
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.text.style.TextDecoration
    import androidx.compose.ui.text.withStyle
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.hilt.navigation.compose.hiltViewModel
    import androidx.lifecycle.compose.collectAsStateWithLifecycle
    import coil.compose.ImagePainter
    import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
    import com.bumptech.glide.integration.compose.GlideImage
    import com.example.tourmanage.R
    import com.example.tourmanage.common.data.server.item.DetailItem
    import com.example.tourmanage.common.data.server.item.StayDetailItem
    import com.example.tourmanage.common.extension.*
    import com.example.tourmanage.common.value.Config
    import com.example.tourmanage.ui.ui.theme.TourManageTheme
    import com.example.tourmanage.viewmodel.MainViewModel
    import com.example.tourmanage.viewmodel.StayDetailViewModel
    import com.google.accompanist.pager.ExperimentalPagerApi
    import dagger.hilt.android.AndroidEntryPoint
    import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
    import timber.log.Timber

    @AndroidEntryPoint
    class StayDetailActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val receivedData = intent.intentSerializable(Config.STAY_INFO, StayDetailItem::class.java)
            val viewModel by viewModels<StayDetailViewModel>()
            Timber.i("OnCreate")
            setContent {
                TourManageTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        DetailLayout(receivedData, viewModel)
                    }
                }
            }
        }
    }

    @Composable
    fun DetailLayout(detailData: StayDetailItem?, viewModel: StayDetailViewModel = hiltViewModel()) {
        Timber.i("MainLayout() | detailData: $detailData")

        if (detailData == null) {
            return
        }
        LaunchedEffect(Unit) {
            viewModel.requestOptionInfo(detailData.contentId, detailData.contentTypeId)
        }

        val context = LocalContext.current
        val activity = context as StayDetailActivity
        LazyColumn() {
            item {
                TopImageLayout(detailData, activity)
            }
            item {
                StayIntroLayout(detailData)
                OverViewLayout(detailData, Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp))
            }
            item {
                OptionLayout(viewModel, Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp))
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class, ExperimentalPagerApi::class)
    @Composable
    fun TopImageLayout(detailData: StayDetailItem, activity: StayDetailActivity) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(300.dp)) {
            GlideImage(
                contentScale = ContentScale.Crop,
                model = detailData.mainImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
            Row(
                Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { activity.finish()}) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }

    @Composable
    fun StayIntroLayout(detailData: StayDetailItem) {
        Column(modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp)) {
            Text(
                text = detailData.title.isEmptyString("숙소 명").getPureText(),
                fontSize = 20.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Row() {
                Image(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(id = R.drawable.baseline_location_on_black_18),
                    contentDescription = "")
                Text(
                    text = detailData.addr1.isEmptyString("주소"),
                    fontSize = 15.sp)
            }
        }
    }
    @Composable
    fun OverViewLayout(detailData: StayDetailItem, paddingModifier: Modifier) {
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
                    fontSize = 11.sp)) {
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

        //_ TEST Commit
    }

    @Composable
    fun OptionLayout(viewModel: StayDetailViewModel = hiltViewModel(), paddingModifier: Modifier) {
        Timber.i("OptionLayout()")
        val optionItem = viewModel.optionInfo.collectAsStateWithLifecycle()
        if (optionItem.isSuccess()) {
            val optionInfos = optionItem.value.data!!
            Timber.i("optionInfos size: ${optionInfos.size}")
            Column() {
                optionInfos.forEach {
                    RoomsInfo(it, paddingModifier)
                }
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun RoomsInfo(optionItem: DetailItem, paddingModifier: Modifier) {
        Timber.i("RoomsInfo()")
        var options = ""
        if (optionItem.airConditionYn.isBooleanYn()) {
            options += "에어컨 | "
        }
        if (optionItem.bathFacilityYn.isBooleanYn()) {
            options += "욕조 | "
        }
        if (optionItem.roomPcYn.isBooleanYn()) {
            options += "PC | "
        }
        if (optionItem.roomTvYn.isBooleanYn()) {
            options += "TV | "
        }
        if (optionItem.roomInternetYn.isBooleanYn()) {
            options += "인터넷 | "
        }
        if (optionItem.roomCookYn.isBooleanYn()) {
            options += "취사 가능 | "
        }
        if (optionItem.roomSofaYn.isBooleanYn()) {
            options += "소파 | "
        }
        if (optionItem.roomRefrigeratorYn.isBooleanYn()) {
            options += "냉장고"
        }

        Text(
            modifier = paddingModifier,
            text = options,
            fontSize = 11.sp,
            color = colorResource(id = R.color.black))
        
        Row(
            modifier = paddingModifier
                .fillMaxWidth()
        ) {
            GlideImage(
               modifier = Modifier
                   .size(150.dp)
                   .clip(RoundedCornerShape(CornerSize(16.dp))),
               contentScale = ContentScale.Crop,
               model = optionItem.roomImg1,
               contentDescription = null,
           )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier
                .height(150.dp),
                verticalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier
                    .fillMaxWidth()) {
                    Text(
                        text = optionItem.roomTitle.isEmptyString(),
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.black)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "기준 인원: ${optionItem.roomBaseCount}명",
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "최대 인원: ${optionItem.roomMaxCount}명",
                        fontSize = 11.sp
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "${optionItem.offWeekDayFee.isEmptyString("0").convertKRW()}원",
                    textAlign = TextAlign.End,
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(id = R.color.white_smoke),
            thickness = 8.dp,)
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview4() {
        TourManageTheme {
            Greeting("Android")
        }
    }