import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

data class TouristSpot(val name: String, val description: String, val imageResId: Int)

@Composable
fun TouristSpotList(touristSpots: List<TouristSpot>) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(touristSpots.size) { index ->
            val spot = touristSpots[index]
            TouristSpotItem(spot)
        }
    }
}

@Composable
fun TouristSpotItem(spot: TouristSpot) {
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = spot.name,
            style = MaterialTheme.typography.h4,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Image(
            painter = painterResource(id = spot.imageResId),
            contentDescription = spot.name,
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = spot.description,
            style = MaterialTheme.typography.body1,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

/*@Composable
fun TouristSpotScreen() {
    // TODO 이미지 입히기
    val touristSpots = remember {
        mutableStateOf(

            TouristSpot(
                "관광지 이름",
                "설명",
            )
        )
    }

    TouristSpotList(touristSpots.value)
}*/
