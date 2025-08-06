package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp

data class Geofence(
    @DocumentId
    val geofenceId: String = "",

    // Ownership
    val userId: String = "",
    val petId: String = "",

    // Zone Definition
    val name: String = "",
    val description: String = "",
    val center: GeoPoint? = null,
    val radius: Double = 0.0, // meters (20-1000m)
    val shape: String = "circle", // "circle", "polygon" (future)
    val color: String = "#4CAF50", // UI color

    // Configuration
    val isActive: Boolean = true,
    val priority: Int = 1, // 1-5, higher = more important
    val createdBy: String = "user", // "user", "ai_recommendation"

    // Alert Settings
    val alertSettings: GeofenceAlertSettings = GeofenceAlertSettings(),

    // Smart Features
    val schedule: GeofenceSchedule = GeofenceSchedule(),

    // AI Learning Data
    val behaviorPatterns: GeofenceBehaviorPatterns = GeofenceBehaviorPatterns(),

    // Analytics
    val analytics: GeofenceAnalytics = GeofenceAnalytics(),

    // Metadata
    @ServerTimestamp
    val createdAt: Timestamp? = null,
    @ServerTimestamp
    val updatedAt: Timestamp? = null,
    @ServerTimestamp
    val lastTriggered: Timestamp? = null,

    // AI Optimization
    val aiOptimization: GeofenceAIOptimization = GeofenceAIOptimization()
)

data class GeofenceAlertSettings(
    val onEntry: Boolean = true,
    val onExit: Boolean = true,
    val autoSOS: Boolean = false,
    val autoSOSDelay: Int = 300, // seconds
    val sensitivity: String = "medium", // "low", "medium", "high"
    val quietHours: Boolean = true,
    val familyNotification: Boolean = true,
    val emergencyEscalation: Boolean = false
)

data class GeofenceSchedule(
    val enabled: Boolean = false,
    val dailySchedule: List<DailySchedule> = emptyList()
)

data class DailySchedule(
    val start: String = "", // "08:00"
    val end: String = "", // "18:00"
    val days: List<String> = emptyList(), // ["Monday", "Tuesday", ...]
    val timezone: String = "America/New_York"
)

data class GeofenceBehaviorPatterns(
    val averageStayDuration: Long = 0, // seconds
    val commonEntryTimes: List<String> = emptyList(),
    val commonExitTimes: List<String> = emptyList(),
    val weekdayPattern: Double = 0.0, // 0-1
    val weekendPattern: Double = 0.0,
    val seasonalVariation: Double = 0.0
)

data class GeofenceAnalytics(
    val totalEntries: Int = 0,
    val totalExits: Int = 0,
    val averageStayDuration: Long = 0,
    @ServerTimestamp
    val lastEntry: Timestamp? = null,
    @ServerTimestamp
    val lastExit: Timestamp? = null,
    val falseAlerts: Int = 0,
    val accuracy: Int = 0, // 0-100
    val hourlyDistribution: Map<String, Int> = emptyMap(),
    val weeklyDistribution: Map<String, Int> = emptyMap()
)

data class GeofenceAIOptimization(
    val suggestedRadius: Double? = null,
    val optimizationScore: Int = 0, // 0-100
    @ServerTimestamp
    val lastOptimized: Timestamp? = null,
    val improvementPotential: String = "low", // "low", "medium", "high"
    val recommendations: List<String> = emptyList()
)