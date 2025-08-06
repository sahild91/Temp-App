package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp

data class Pet(
    @DocumentId
    val petId: String = "",

    // Basic Info (Unencrypted for UX)
    val userId: String = "",
    val name: String = "",
    val type: String = "", // "Dog", "Cat", "Other"
    val breed: String = "",
    val gender: String = "", // "Male", "Female", "Unknown"
    val birthday: String = "", // YYYY-MM-DD format
    val weight: Double? = null, // kg
    val photoURL: String? = null,
    val microchipId: String? = null,
    val registrationNumber: String? = null,

    // Timestamps
    @ServerTimestamp
    val createdAt: Timestamp? = null,
    @ServerTimestamp
    val updatedAt: Timestamp? = null,

    // Device Association - Updated after pairing
    val currentDevice: DeviceInfo? = null,

    // Medical Info (Basic - unencrypted)
    val medicalHistory: List<MedicalRecord> = emptyList(),

    // Emergency Contacts
    val emergencyContacts: List<EmergencyContact> = emptyList(),

    // Current Status (Cached)
    val currentStatus: PetStatus? = null,

    // Activity Summary (ACTIVE Tier - Cached Daily)
    val activitySummary: ActivitySummary? = null,

    // Behavior Patterns (ML Generated)
    val behaviorProfile: BehaviorProfile? = null
)

data class DeviceInfo(
    val deviceId: String = "",
    val deviceType: String = "TAG", // "TAG", "ACTIVE", "SENSE"
    val pairedAt: Timestamp? = null,
    val batteryLevel: Int = 0,
    val lastSeen: Timestamp? = null
)

data class MedicalRecord(
    val date: String = "",
    val condition: String = "",
    val notes: String = "",
    val vetId: String? = null,
    val severity: String = "routine" // "routine", "minor", "major", "critical"
)

data class EmergencyContact(
    val name: String = "",
    val phone: String = "",
    val relationship: String = "", // "Owner", "Vet", "Family", "Friend"
    val isPrimary: Boolean = false,
    val available24h: Boolean = false,
    val notes: String = ""
)

data class PetStatus(
    val location: LocationStatus? = null,
    val activity: ActivityStatus? = null,
    val health: HealthStatus? = null
)

data class LocationStatus(
    val coordinates: GeoPoint? = null,
    val accuracy: Double = 0.0,
    val zone: String = "",
    val timestamp: Timestamp? = null,
    val confidence: String = "low" // "high", "medium", "low"
)

data class ActivityStatus(
    val level: String = "low", // "low", "moderate", "high"
    val lastMovement: Timestamp? = null,
    val todaySteps: Int = 0,
    val streak: Int = 0
)

data class HealthStatus(
    val status: String = "normal", // "normal", "concern", "alert"
    val lastCheckup: Timestamp? = null,
    val nextCheckup: Timestamp? = null
)

data class ActivitySummary(
    val lastUpdated: Timestamp? = null,
    val today: DailyActivity = DailyActivity(),
    val thisWeek: WeeklyActivity = WeeklyActivity(),
    val streaks: ActivityStreaks = ActivityStreaks()
)

data class DailyActivity(
    val steps: Int = 0,
    val activeMinutes: Int = 0,
    val distance: Double = 0.0, // km
    val calories: Int = 0,
    val activities: List<String> = emptyList()
)

data class WeeklyActivity(
    val averageSteps: Int = 0,
    val totalDistance: Double = 0.0,
    val activeDays: Int = 0,
    val goalAchieved: Int = 0
)

data class ActivityStreaks(
    val current: Int = 0,
    val longest: Int = 0,
    val thisMonth: Int = 0
)

data class BehaviorProfile(
    val activityPeaks: List<String> = emptyList(), // ["08:00-09:00", "18:00-19:00"]
    val favoriteLocations: List<String> = emptyList(),
    val socialBehavior: String = "friendly", // "friendly", "cautious", "aggressive"
    val energyLevel: String = "moderate", // "low", "moderate", "high"
    val routineStrength: Double = 0.0, // 0-1
    val lastAnalyzed: Timestamp? = null
)