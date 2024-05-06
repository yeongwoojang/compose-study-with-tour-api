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
    import androidx.core.view.WindowCompat
    import androidx.hilt.navigation.compose.hiltViewModel
    import androidx.lifecycle.compose.collectAsStateWithLifecycle
    import coil.compose.ImagePainter
    import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
    import com.bumptech.glide.integration.compose.GlideImage
    import com.example.tourmanage.R
    import com.example.tourmanage.common.data.server.item.DetailItem
    import com.example.tourmanage.common.data.server.item.StayDetailItem
    import com.example.tourmanage.common.data.server.item.StayItem
    import com.example.tourmanage.common.extension.*
    import com.example.tourmanage.common.value.Config
    import com.example.tourmanage.ui.staydetail.DetailLayout
    import com.example.tourmanage.ui.ui.theme.TourManageTheme
    import com.example.tourmanage.viewmodel.MainViewModel
    import com.example.tourmanage.viewmodel.StayDetailViewModel
    import dagger.hilt.android.AndroidEntryPoint
    import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
    import timber.log.Timber

    @AndroidEntryPoint
    class StayDetailActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val receivedData = intent.intentSerializable(Config.STAY_INFO, StayItem::class.java)
            val viewModel by viewModels<StayDetailViewModel>()
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