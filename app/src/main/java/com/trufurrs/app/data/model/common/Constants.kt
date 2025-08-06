package com.trufurrs.app.data.model.common

object Constants {
    // Tier Types
    const val TIER_TAG = "TAG"
    const val TIER_ACTIVE = "ACTIVE"
    const val TIER_SENSE = "SENSE"

    // Device Types
    const val DEVICE_TAG = "TAG"
    const val DEVICE_ACTIVE = "ACTIVE"
    const val DEVICE_SENSE = "SENSE"

    // Pet Types
    const val PET_TYPE_DOG = "Dog"
    const val PET_TYPE_CAT = "Cat"
    const val PET_TYPE_OTHER = "Other"

    // Activity Types
    const val ACTIVITY_WALK = "walk"
    const val ACTIVITY_RUN = "run"
    const val ACTIVITY_PLAY = "play"
    const val ACTIVITY_REST = "rest"
    const val ACTIVITY_SLEEP = "sleep"
    const val ACTIVITY_TRAINING = "training"

    // Alert Categories
    const val ALERT_CATEGORY_GEOFENCE = "geofence"
    const val ALERT_CATEGORY_DEVICE = "device"
    const val ALERT_CATEGORY_ACTIVITY = "activity"
    const val ALERT_CATEGORY_EMERGENCY = "emergency"
    const val ALERT_CATEGORY_HEALTH = "health"

    // Alert Severities
    const val ALERT_SEVERITY_LOW = "low"
    const val ALERT_SEVERITY_MEDIUM = "medium"
    const val ALERT_SEVERITY_HIGH = "high"
    const val ALERT_SEVERITY_CRITICAL = "critical"

    // Family Roles
    const val FAMILY_ROLE_OWNER = "owner"
    const val FAMILY_ROLE_FAMILY = "family"
    const val FAMILY_ROLE_EMERGENCY = "emergency"
    const val FAMILY_ROLE_VET = "vet"
    const val FAMILY_ROLE_TRAINER = "trainer"

    // Permissions
    const val PERMISSION_VIEW = "view"
    const val PERMISSION_ALERTS = "alerts"
    const val PERMISSION_EMERGENCY = "emergency"
    const val PERMISSION_SETTINGS = "settings"
    const val PERMISSION_MANAGE_FAMILY = "manage_family"

    // Location Sources
    const val LOCATION_SOURCE_GPS = "gps"
    const val LOCATION_SOURCE_WIFI = "wifi"
    const val LOCATION_SOURCE_CELLULAR = "cellular"
    const val LOCATION_SOURCE_BLE = "ble"

    // Confidence Levels
    const val CONFIDENCE_HIGH = "high"
    const val CONFIDENCE_MEDIUM = "medium"
    const val CONFIDENCE_LOW = "low"
}