    package com.example.tourmanage.ui

    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.viewModels
    import androidx.compose.foundation.layout.*
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Surface
    import androidx.compose.ui.Modifier
    import com.example.tourmanage.common.data.server.item.StayItem
    import com.example.tourmanage.common.extension.*
    import com.example.tourmanage.common.value.Config
    import com.example.tourmanage.ui.staydetail.DetailLayout
    import com.example.tourmanage.ui.ui.theme.TourManageTheme
    import com.example.tourmanage.viewmodel.StayDetailViewModel
    import dagger.hilt.android.AndroidEntryPoint

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
                        color = MaterialTheme.colorScheme.background
                    ) {
                        DetailLayout(receivedData, viewModel)
                    }
                }
            }
        }
    }