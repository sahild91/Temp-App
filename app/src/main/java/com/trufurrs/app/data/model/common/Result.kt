package com.trufurrs.app.data.model.common

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

inline fun <T> Result<T>.fold(
    onSuccess: (value: T) -> Unit,
    onError: (exception: Exception) -> Unit
) {
    when (this) {
        is Result.Success -> onSuccess(data)
        is Result.Error -> onError(exception)
        is Result.Loading -> { /* Handle loading if needed */ }
    }
}