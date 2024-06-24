package com.example.tourmanage.ui.staydetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.R
import com.example.tourmanage.common.data.server.item.StayDetailItem
import com.example.tourmanage.common.extension.getPureText
import com.example.tourmanage.common.extension.isEmptyString

@Composable
fun StayIntro(detailData: StayDetailItem) {
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