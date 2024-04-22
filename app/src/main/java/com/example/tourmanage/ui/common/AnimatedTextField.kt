package com.example.tourmanage.ui.common

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.R
import com.example.tourmanage.common.extension.noRippleClickable
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont

@Composable
fun AnimatedTextField(placeholder: String? = "", onClick: () -> Unit) {
    var isSearchMode by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    AnimatedVisibility(
        visible = isSearchMode,
        modifier = Modifier
            .fillMaxWidth(),
        enter = scaleIn() + expandHorizontally(),
        exit = scaleOut() + shrinkHorizontally()
    ) {
        BasicTextField(
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.white_smoke),
                    RoundedCornerShape(20.dp)
                )
                .height(50.dp)
                .padding(start = 20.dp, end = 5.dp),
            value = searchText,
            onValueChange = {
                searchText = it
            },
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colors.primary),
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = MaterialTheme.typography.body2.fontSize
            ),
            decorationBox = { innerTextField ->
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box {
                        if (searchText.isEmpty()) {
                            Text(
                                text = "검색어를 입력하세요.",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontFamily = spoqaHanSansNeoFont,
                                    fontWeight = FontWeight.Light,
                                    color = colorResource(id = R.color.dark_gray)
                                )
                            )
                        }
                        innerTextField()
                    }
                    IconButton(
                        onClick = {
                            keyboardController?.hide()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = LocalContentColor.current.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        )
    }
    if (!isSearchMode) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    color = colorResource(id = R.color.gainsboro),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(start = 20.dp)
                .noRippleClickable { onClick() },
            contentAlignment = Alignment.TopStart
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "$placeholder",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = spoqaHanSansNeoFont,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.dark_gray)
                    )
                )
            }

        }
    }
}