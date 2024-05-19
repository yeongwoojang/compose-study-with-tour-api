package com.example.tourmanage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.extension.intentSerializable
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.DetailDavItem
import com.example.tourmanage.ui.festival.FestivalDetail
import com.example.tourmanage.ui.festival.FestivalMainWidget
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FestivalMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receivedData = intent.intentSerializable(Config.PASS_DATA.DATA.value, java.util.ArrayList::class.java)

        var mainFestival: ArrayList<FestivalItem>? = null
        if (receivedData is ArrayList<*>) {
            mainFestival = receivedData as? ArrayList<FestivalItem>
            Timber.d("mainFestival: $mainFestival")
        }

        enableEdgeToEdge()
        setContent {
            TourManageTheme {
                if (mainFestival != null) {
                    val navController = rememberNavController()
                    ChildNavHost(navController, mainFestival)
                } else {
                    //TODO 데이터 없는 경우
                }
            }
        }
    }
}

@Composable
fun ChildNavHost(navController: NavHostController, mainFestival: ArrayList<FestivalItem>) {
    NavHost(
        navController = navController,
        startDestination = DetailDavItem.Main.route
    ) {
        composable(DetailDavItem.Main.route) {
            FestivalMainWidget(navController = navController, mainFestival = mainFestival)
        }
        composable(DetailDavItem.Test.route) { backstackentry->
            FestivalDetail(navController)
        }
    }
}
