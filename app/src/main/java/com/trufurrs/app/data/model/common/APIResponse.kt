package com.trufurrs.app.data.model.common

data class ApiResponse<T>(
    val success: Boolean = false,
    val data: T? = null,
    val message: String? = null,
    val errorCode: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)