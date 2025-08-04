package com.trufurrs.app.utils

enum class AppTier {
    ACTIVE, TAG
}

object TierConfig {
    var currentTier: AppTier = AppTier.ACTIVE

    // Feature flags
    val isGeofencingEnabled: Boolean get() = currentTier == AppTier.ACTIVE
    val isActivityTrackingEnabled: Boolean get() = currentTier == AppTier.ACTIVE
    val isSmartAlertsEnabled: Boolean get() = currentTier == AppTier.ACTIVE
    val isFamilySharingEnabled: Boolean get() = currentTier == AppTier.ACTIVE
    val maxPets: Int get() = if (currentTier == AppTier.ACTIVE) 3 else 1
    val gpsRefreshRate: Long get() = if (currentTier == AppTier.ACTIVE) 60000L else 300000L
    val maxGeofenceZones: Int get() = if (currentTier == AppTier.ACTIVE) 5 else 0

    // Mapbox configuration
    val mapboxStyle: String get() = when (currentTier) {
        AppTier.ACTIVE -> "mapbox://styles/mapbox/streets-v12"
        AppTier.TAG -> "mapbox://styles/mapbox/light-v11"
    }

    val isOfflineMapsEnabled: Boolean get() = currentTier == AppTier.ACTIVE
}