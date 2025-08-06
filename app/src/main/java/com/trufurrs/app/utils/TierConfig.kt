package com.trufurrs.app.utils

import com.trufurrs.app.ui.theme.TruFurrsBrand

enum class AppTier {
    TAG, ACTIVE, SENSE
}

object TierConfig {
    var currentTier: AppTier = AppTier.TAG

    // Device type detection from device ID
    fun detectTierFromDevice(deviceId: String): AppTier {
        return when {
            deviceId.startsWith("TAG-", ignoreCase = true) -> AppTier.TAG
            deviceId.startsWith("ACT-", ignoreCase = true) -> AppTier.ACTIVE
            deviceId.startsWith("SNS-", ignoreCase = true) -> AppTier.SENSE
            else -> AppTier.TAG
        }
    }

    // Update tier based on device pairing
    fun updateTierFromDevice(deviceType: String) {
        currentTier = when (deviceType.uppercase()) {
            "ACTIVE" -> AppTier.ACTIVE
            "SENSE" -> AppTier.SENSE
            else -> AppTier.TAG
        }
    }

    // Feature flags based on current tier
    val isGeofencingEnabled: Boolean get() = currentTier in listOf(AppTier.ACTIVE, AppTier.SENSE)
    val isActivityTrackingEnabled: Boolean get() = currentTier in listOf(AppTier.ACTIVE, AppTier.SENSE)
    val isSmartAlertsEnabled: Boolean get() = currentTier in listOf(AppTier.ACTIVE, AppTier.SENSE)
    val isFamilySharingEnabled: Boolean get() = currentTier in listOf(AppTier.ACTIVE, AppTier.SENSE)
    val isHealthMonitoringEnabled: Boolean get() = currentTier == AppTier.SENSE

    // Tier-specific limits
    val maxPets: Int get() = when (currentTier) {
        AppTier.TAG -> 1
        AppTier.ACTIVE -> 3
        AppTier.SENSE -> 5
    }

    val maxGeofenceZones: Int get() = when (currentTier) {
        AppTier.TAG -> 0
        AppTier.ACTIVE -> 5
        AppTier.SENSE -> 10
    }

    val gpsRefreshRate: Long get() = when (currentTier) {
        AppTier.TAG -> 300000L // 5 minutes
        AppTier.ACTIVE -> 60000L // 1 minute
        AppTier.SENSE -> 30000L // 30 seconds
    }

    // Brand-consistent app titles and colors
    fun getAppTitle(): String = TruFurrsBrand.getTierDisplayName(currentTier.name)

    fun getPrimaryColor(): String = TruFurrsBrand.getTierPrimaryColor(currentTier.name)

    fun getAccentColor(): String = TruFurrsBrand.getTierAccentColor(currentTier.name)

    fun getThemeResId(): Int = when (currentTier) {
        AppTier.TAG -> com.trufurrs.app.R.style.Theme_TruFurrs_TAG
        AppTier.ACTIVE -> com.trufurrs.app.R.style.Theme_TruFurrs_ACTIVE
        AppTier.SENSE -> com.trufurrs.app.R.style.Theme_TruFurrs_SENSE
    }
}