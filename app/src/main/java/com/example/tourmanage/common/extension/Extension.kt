package com.example.tourmanage.common.extension

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.State
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.IntentData
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
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

fun State<UiState<*>>.isReady(): Boolean {
    return value is UiState.Ready
}

fun State<UiState<*>>.isSuccess(): Boolean {
    return value is UiState.Success
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

fun String.downsizeString(): String {
    var result = this
    if (this.length > 80) {
       result = this.subSequence(0, 80).toString() + "..."
    }
    return result
}

fun String?.isBooleanYn() = "Y" == this

fun String.convertKRW(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.KOREA)
    return formatter.format(this.toLong())
}

