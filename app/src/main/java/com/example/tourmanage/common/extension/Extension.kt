package com.example.tourmanage.common.extension

import android.content.Intent
import android.location.Address
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.IntentData
import kotlinx.coroutines.flow.*
import java.text.NumberFormat
import java.util.Locale

fun Intent.putExtra(data: IntentData) {
    data.map.keys.forEach {
        if (data.map[it] is String) {
            putExtra(it, data.map[it] as String)
        } else {
            putExtra(it, data.map[it] as java.io.Serializable)
        }
    }
}

fun <T: java.io.Serializable> Intent.intentSerializable(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializableExtra(key, clazz)
    } else {
        this.getSerializableExtra(key) as T?
    }
}

fun String?.isNotNullOrEmpty(): Boolean {
    return this != null && this.isNotEmpty()
}

fun String?.getPureText(): String {
    val result = this ?: ""
    val regex = Regex("""\((.*?)\)|\[(.*?)\]""")
    val newText = result.replace(regex, "").trim()
    return newText
}

fun State<UiState<*>>.isLoading(): Boolean {
    return value is UiState.Loading
}

fun State<UiState<*>>.isSuccess(): Boolean {
    return value is UiState.Success
}

fun State<UiState<*>>.isCompleteSuccess(requestKey: String?): Boolean {
    return value is UiState.Success && requestKey == value.requestKey
}

fun State<UiState<*>>.isError(): Boolean {
    return value is UiState.Error
}

fun State<UiState<*>>.isComplete(): Boolean {
    return value is UiState.Error || value is UiState.Success
}
fun String?.isEmptyString(value: String = ""): String {
    if (this.isNullOrEmpty()) {
        return value
    } else {
        return this
    }
}

fun String.downsizeString(endLength: Int): String {
    var result = this
    if (this.length > endLength) {
       result = this.subSequence(0, endLength).toString() + "..."
    }
    return result
}

fun String?.isBooleanYn() = "Y" == this

fun String.convertKRW(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.KOREA)
    return formatter.format(this.toLong())
}

suspend fun <T>Flow<UiState<T>>.setDefaultCollect(state: MutableStateFlow<UiState<T>>, needLoading: Boolean = true) {
    onStart {
        if (needLoading) state.value = UiState.Loading()
    }
        .catch { state.value = UiState.Error(it.message ?: "") }
        .collect { state.value = it }
}

/**
 * Clickable에서 click 이벤트 실행 시 effect 제거
 */
fun Modifier.noRippleClickable(onClick: ()->Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun Address.toAreaText() = "${adminArea.isEmptyString()} ${locality.isEmptyString()} ${thoroughfare.isEmptyString()}"

fun Address.toMyAreaText() = "${adminArea.isEmptyString()} ${subLocality}"

fun String?.formatCoordinate(): String {
    this?: return ""

    // 2. 소수점을 적절한 위치에 추가
    val formattedValue = StringBuilder(this)
    formattedValue.insert(this.length - 7, '.')

    return formattedValue.toString()
}