package com.trufurrs.app.data.repository

import com.trufurrs.app.data.model.Device
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    suspend fun createDevice(device: Device): Result<String>
    suspend fun getDeviceById(deviceId: String): Result<Device?>
    suspend fun getDevicesByUserId(userId: String): Result<List<Device>>
    suspend fun updateDevice(device: Device): Result<Unit>
    suspend fun updateDeviceStatus(deviceId: String, status: String, batteryLevel: Int): Result<Unit>
    suspend fun deleteDevice(deviceId: String): Result<Unit>
    fun observeDevice(deviceId: String): Flow<Device?>
}