package com.example.tourmanage.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ModalBottomSheetValue.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.tourmanage.R
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    closeSheet: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        onDismissRequest = closeSheet,
        sheetState = sheetState,
        containerColor = Color.Transparent,
        scrimColor = Color.Transparent,
        dragHandle = null,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.lightpink),
                    contentColor = Color.White
                ),
                onClick = {
                    Timber.i("Sheet Click")
                }
            ) {
                Text(text = "객실 선택", color = Color.White)
            }
        }
    }


}