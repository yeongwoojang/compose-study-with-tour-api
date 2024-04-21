package com.example.tourmanage.ui

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.StayDetailItem
import com.example.tourmanage.common.extension.intentSerializable
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.festival.FestivalBanner
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FestivalMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receivedData = intent.intentSerializable(Config.PASS_DATA, java.util.ArrayList::class.java)
        var festivalItems: ArrayList<FestivalItem>? = null
        if (receivedData is ArrayList<*>) {
            festivalItems = receivedData as? ArrayList<FestivalItem>
            Timber.d("festivalItems: $festivalItems")
        }

        setContent {
            TourManageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if (festivalItems != null) {
                        FestivalBanner(festivalItems = festivalItems)
                    } else {
                        //TODO 데이터 없는 경우
                    }
                }
            }
        }
    }
}
