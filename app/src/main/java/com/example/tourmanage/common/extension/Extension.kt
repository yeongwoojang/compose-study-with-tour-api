package com.example.tourmanage.common.extension

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import androidx.compose.runtime.State
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.IntentData
import kotlinx.coroutines.flow.StateFlow

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