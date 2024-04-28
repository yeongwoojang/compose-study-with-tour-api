package com.example.tourmanage

sealed class UiState<T>(
    val data: T? = null,
    val message: String? = null,
    val requestKey: String? = "",
) {
    class Ready<T> : UiState<T>()
    class Loading<T> : UiState<T>()
    class Finish<T> : UiState<T>()
    class Success<T>(data: T, requestKey: String? = "") : UiState<T>(data, requestKey = requestKey)
    class Error<T>(message: String, data: T? = null) : UiState<T>(data, message = message)
}