package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class User(
    @DocumentId
    val userId: String = "",

    // Basic Profile (Unencrypted for UX)
    val displayName: String = "",
    val email: String = "",
    val phoneNumber: String? = null,
    val photoURL: String? = null,

    // Account Management
    @ServerTimestamp
    val createdAt: Timestamp? = null,
    @ServerTimestamp
    val lastActiveAt: Timestamp? = null,
    val tier: String = "TAG", // Default to TAG, updated after device pairing
    val subscriptionStatus: String = "active",
    val subscriptionExpiry: Timestamp? = null,

    // Family & Permissions
    val isFamilyAdmin: Boolean = true,
    val familyId: String? = null,

    // Privacy & Security
    val profileIncomplete: Boolean = true, // Start as incomplete
    val privacySettings: PrivacySettings = PrivacySettings(),

    // Preferences
    val notificationPreferences: NotificationPreferences = NotificationPreferences(),

    // Analytics
    val usage: UsageAnalytics = UsageAnalytics()
)

data class PrivacySettings(
    val shareLocation: Boolean = true,
    val shareActivity: Boolean = true,
    val allowFamilyInvites: Boolean = true,
    val dataRetentionDays: Int = 365
)

data class NotificationPreferences(
    val push: Boolean = true,
    val email: Boolean = true,
    val sms: Boolean = false,
    val quietHours: QuietHours = QuietHours(),
    val alertTypes: AlertTypePreferences = AlertTypePreferences()
)

data class QuietHours(
    val enabled: Boolean = false,
    val start: String = "22:00",
    val end: String = "07:00",
    val timezone: String = "America/New_York"
)

data class AlertTypePreferences(
    val geofence: Boolean = true,
    val battery: Boolean = true,
    val activity: Boolean = true,
    val emergency: Boolean = true
)

data class UsageAnalytics(
    @ServerTimestamp
    val lastLoginAt: Timestamp? = null,
    val totalSessions: Int = 0,
    val averageSessionDuration: Long = 0, // seconds
    val featuresUsed: List<String> = emptyList()
)