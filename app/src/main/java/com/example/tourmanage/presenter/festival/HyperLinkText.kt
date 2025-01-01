package com.example.tourmanage.presenter.festival

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun HyperLinkText(text: String, icon: ImageVector) {
    val context = LocalContext.current
    val mUriHandler = LocalUriHandler.current

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val annotatedText = buildAnnotatedString {
            pushStringAnnotation(tag = text, annotation = text)
            append(text)
            pop()
        }

        Icon(
            imageVector = icon,
            tint = Color.Black,
            contentDescription = ""
        )

        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(tag = text, start = offset, end = offset)
                    .firstOrNull()?.let { annotation ->
                        mUriHandler.openUri(Uri.parse(annotation.item).toString())
                    }
            }
        )
    }
}