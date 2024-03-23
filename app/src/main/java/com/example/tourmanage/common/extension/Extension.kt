package com.example.tourmanage.common.extension

import android.content.Intent
import androidx.compose.runtime.State
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.IntentData
import kotlinx.coroutines.flow.StateFlow

fun Intent.putExtra(data: IntentData) {
    data.map.keys.forEach {
        putExtra(it, data.map[it])
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