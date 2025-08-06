package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class Device(
    @DocumentId
    val deviceId: String = "",

    // Device Identity
    val userId: String = "",
    val petId: String = "",
    val physicalDeviceId: String = "", // Physical device identifier
    val deviceType: String = "TAG", // Determined during pairing
    val serialNumber: String = "",
    val firmwareVersion: String = "",
    val hardwareVersion: String = "",
    val manufacturingDate: String = "",

    // Status
    val status: String = "active", // "active", "inactive", "lost", "maintenance"
    @ServerTimestamp
    val lastSync: Timestamp? = null,
    val batteryLevel: Int = 0,
    val batteryHealth: String = "good", // "excellent", "good", "fair", "poor"
    val estimatedBatteryLife: Int = 0, // days remaining

    // Connectivity
    val connectivity: ConnectivityStatus = ConnectivityStatus(),

    // Configuration
    val settings: DeviceSettings = DeviceSettings(),

    // Diagnostics
    val diagnostics: DeviceDiagnostics = DeviceDiagnostics(),

    // Security
    val authToken: String = "",
    @ServerTimestamp
    val lastTokenRefresh: Timestamp? = null,
    val securityVersion: String = "v1.0",
    val encryptionEnabled: Boolean = true
)

data class ConnectivityStatus(
    val gps: GPSStatus = GPSStatus(),
    val cellular: CellularStatus = CellularStatus(),
    val wifi: WiFiStatus = WiFiStatus(),
    val ble: BLEStatus = BLEStatus()
)

data class GPSStatus(
    val enabled: Boolean = true,
    val lastFix: Timestamp? = null,
    val accuracy: Double = 0.0, // meters
    val satelliteCount: Int = 0
)

data class CellularStatus(
    val enabled: Boolean = true,
    val signalStrength: Int = 0, // 0-100
    val carrier: String = "",
    val dataUsage: Int = 0, // MB this month
    val lastConnection: Timestamp? = null
)

data class WiFiStatus(
    val enabled: Boolean = false,
    val connected: Boolean = false,
    val ssid: String? = null,
    val signalStrength: Int? = null
)

data class BLEStatus(
    val enabled: Boolean = true,
    val range: Int = 25, // effective range in meters
    val lastConnection: Timestamp? = null
)

data class DeviceSettings(
    val gpsRefreshRate: Int = 300, // seconds (5 minutes for TAG, 60 for ACTIVE)
    val sosEnabled: Boolean = true,
    val ledEnabled: Boolean = true,
    val soundEnabled: Boolean = true,
    val powerSaveMode: Boolean = false,
    val geofenceMonitoring: Boolean = false, // ACTIVE only
    val activityTracking: Boolean = false // ACTIVE only
)

data class DeviceDiagnostics(
    val totalUptime: Int = 0, // hours since activation
    val crashCount: Int = 0,
    val lastMaintenance: Timestamp? = null,
    val temperature: Double = 0.0, // celsius
    val issues: List<String> = emptyList(),
    val performanceScore: Int = 100, // 0-100
    val dataIntegrity: Double = 1.0 // 0-1
)