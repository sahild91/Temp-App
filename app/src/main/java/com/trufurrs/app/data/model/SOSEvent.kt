package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp

data class SOSEvent(
    @DocumentId
    val sosId: String = "",

    // Event Identity
    val petId: String = "",
    val userId: String = "",
    val deviceId: String = "",

    // SOS Details
    val triggerType: String = "", // "manual", "auto_geofence", "auto_movement", "auto_panic"
    val triggerSource: String = "", // "app", "device", "ai_detection", "family_member"
    val isActive: Boolean = false,
    val severity: String = "high", // "low", "medium", "high", "critical"

    // Location & Context
    val triggerLocation: SOSLocation = SOSLocation(),

    // Timeline
    @ServerTimestamp
    val triggeredAt: Timestamp? = null,
    @ServerTimestamp
    val acknowledgedAt: Timestamp? = null,
    @ServerTimestamp
    val resolvedAt: Timestamp? = null,
    val acknowledgedBy: String = "",
    val resolvedBy: String = "",

    // Escalation Chain
    val escalationLevel: Int = 1, // 1-5
    val escalationHistory: List<EscalationEvent> = emptyList(),

    // Context Data
    val contextData: SOSContextData = SOSContextData(),

    // Response & Resolution
    val response: SOSResponse = SOSResponse(),
    val resolution: SOSResolution = SOSResolution(),

    // Analytics & Learning
    val analytics: SOSAnalytics = SOSAnalytics(),

    // Follow-up Actions
    val followUp: SOSFollowUp = SOSFollowUp()
)

data class SOSLocation(
    val coordinates: GeoPoint? = null,
    val accuracy: Double = 0.0,
    val address: String? = null,
    val nearestZone: String? = null,
    val weather: WeatherInfo = WeatherInfo()
)

data class WeatherInfo(
    val condition: String = "",
    val temperature: Double = 0.0 // celsius
)

data class EscalationEvent(
    val level: Int = 0,
    @ServerTimestamp
    val triggeredAt: Timestamp? = null,
    val action: String = "", // "notify_family", "call_emergency_contact", "dispatch"
    val contacts: List<String> = emptyList(),
    val responseTime: Long = 0, // seconds
    val success: Boolean = false
)

data class SOSContextData(
    val batteryLevel: Int = 0,
    val lastKnownSpeed: Double = 0.0,
    val weatherConditions: String = "",
    val timeOfDay: String = "", // "morning", "afternoon", "evening", "night"
    val petActivityLevel: String = "", // "high", "normal", "low"
    val recentMovement: String = "", // "normal", "fast", "erratic", "slow"
    val nearbyFamily: Boolean = false,
    val historicalContext: String = ""
)

data class SOSResponse(
    val familyNotified: Int = 0,
    val firstResponse: FirstResponse = FirstResponse(),
    val searchEffort: SearchEffort = SearchEffort()
)

data class FirstResponse(
    val responderId: String = "",
    val responseTime: Long = 0, // seconds
    val action: String = ""
)

data class SearchEffort(
    val duration: Long = 0, // seconds
    val searchersInvolved: Int = 0,
    val searchArea: Double = 0.0, // km radius
    val methodsUsed: List<String> = emptyList()
)

data class SOSResolution(
    val type: String = "", // "false_alarm", "found_safe", "emergency_resolved", "ongoing"
    val notes: String = "",
    val foundLocation: GeoPoint? = null,
    val foundBy: String = "", // "owner", "family", "neighbor", "authorities", "vet"
    val petCondition: String = "safe", // "safe", "injured", "stressed", "unknown"
    val followupRequired: Boolean = false,
    val vetVisitNeeded: Boolean = false
)

data class SOSAnalytics(
    val responseTime: Long = 0, // seconds to first acknowledgment
    val resolutionTime: Long = 0, // seconds to resolution
    val falseAlarm: Boolean? = null,
    val searchEffectiveness: Double = 0.0, // 0-1
    val traumaLevel: String = "none", // "none", "low", "medium", "high"
    val costEstimate: Double = 0.0,
    val lessonsLearned: List<String> = emptyList()
)

data class SOSFollowUp(
    val behaviorChanges: List<String> = emptyList(),
    val trainingNeeded: Boolean = false,
    val geofenceAdjustments: List<String> = emptyList(),
    val alertRuleChanges: List<String> = emptyList(),
    val familyEducation: List<String> = emptyList()
)