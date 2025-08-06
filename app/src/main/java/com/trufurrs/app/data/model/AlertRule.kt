package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class AlertRule(
    @DocumentId
    val ruleId: String = "",
    val userId: String = "",
    val petId: String? = null, // null = applies to all pets

    // Rule Definition
    val alertType: String = "",
    val isEnabled: Boolean = true,
    val name: String = "",
    val description: String = "",

    // Sensitivity & Thresholds
    val sensitivity: String = "medium", // "low", "medium", "high", "adaptive"
    val customThresholds: AlertCustomThresholds = AlertCustomThresholds(),

    // Smart Learning Features
    val learningEnabled: Boolean = true,
    val adaptiveSensitivity: Boolean = true,
    val patternRecognition: Boolean = true,

    // Contextual Awareness
    val contextualRules: AlertContextualRules = AlertContextualRules(),

    // Suppression & Throttling
    val suppression: AlertRuleSuppression = AlertRuleSuppression(),

    // Scheduling & Timing
    val schedule: AlertRuleSchedule = AlertRuleSchedule(),

    // Performance Tracking & Analytics
    val analytics: AlertRuleAnalytics = AlertRuleAnalytics(),

    // AI Learning & Optimization
    val aiLearning: AlertRuleAILearning = AlertRuleAILearning(),

    // User Customization
    val customization: AlertRuleCustomization = AlertRuleCustomization(),

    @ServerTimestamp
    val createdAt: Timestamp? = null,
    @ServerTimestamp
    val updatedAt: Timestamp? = null,
    @ServerTimestamp
    val lastTriggered: Timestamp? = null,
    val version: Int = 1
)

data class AlertCustomThresholds(
    val timeThreshold: Int? = null, // seconds
    val distanceThreshold: Double? = null, // meters
    val speedThreshold: Double? = null, // km/h
    val batteryThreshold: Int? = null, // percentage
    val confidenceThreshold: Double? = null // minimum AI confidence
)

data class AlertContextualRules(
    val weatherAware: Boolean = true,
    val timeOfDayAware: Boolean = true,
    val familyPresenceAware: Boolean = true,
    val routineAware: Boolean = true,
    val seasonalAdjustments: Boolean = true
)

data class AlertRuleSuppression(
    val enabled: Boolean = true,
    val similarWindow: Int = 300, // minutes
    val maxPerHour: Int = 3,
    val quietHours: Boolean = true,
    val familyCoordination: Boolean = true,
    val contextualSuppression: Boolean = true,
    val locationBasedSuppression: LocationBasedSuppression = LocationBasedSuppression()
)

data class LocationBasedSuppression(
    val enabled: Boolean = true,
    val suppressInZones: List<String> = emptyList(),
    val enhanceInZones: List<String> = emptyList()
)

data class AlertRuleSchedule(
    val enabled: Boolean = false,
    val activeHours: List<ActiveHours> = emptyList(),
    val vacationMode: VacationMode = VacationMode()
)

data class ActiveHours(
    val start: String = "",
    val end: String = "",
    val days: List<String> = emptyList(),
    val timezone: String = "America/New_York"
)

data class VacationMode(
    val enabled: Boolean = false,
    val startDate: Timestamp? = null,
    val endDate: Timestamp? = null,
    val alternateRules: String? = null
)

data class AlertRuleAnalytics(
    val totalTriggers: Int = 0,
    val falsePositives: Int = 0,
    val truePositives: Int = 0,
    val helpfulAlerts: Int = 0,
    @ServerTimestamp
    val lastOptimized: Timestamp? = null,
    val accuracy: Int = 0, // 0-100
    val trends: AlertRuleTrends = AlertRuleTrends(),
    val hourlyDistribution: Map<String, Int> = emptyMap(),
    val weeklyDistribution: Map<String, Int> = emptyMap()
)

data class AlertRuleTrends(
    val triggersThisWeek: Int = 0,
    val accuracyTrend: String = "stable", // "improving", "stable", "declining"
    val userSatisfaction: Double = 0.0, // 1-5
    val averageResponseTime: Long = 0 // seconds
)

data class AlertRuleAILearning(
    val learningRate: Double = 0.1, // 0-1
    val confidenceScore: Double = 0.0, // 0-1
    @ServerTimestamp
    val lastLearningUpdate: Timestamp? = null,
    val learningHistory: List<LearningUpdate> = emptyList(),
    val patterns: AlertRulePatterns = AlertRulePatterns()
)

data class LearningUpdate(
    @ServerTimestamp
    val date: Timestamp? = null,
    val change: String = "",
    val reason: String = "",
    val impact: String = ""
)

data class AlertRulePatterns(
    val normalExitTimes: List<String> = emptyList(),
    val routineLocations: List<String> = emptyList(),
    val familyPatterns: String = "",
    val seasonalAdjustments: String = ""
)

data class AlertRuleCustomization(
    val notificationStyle: String = "immediate", // "immediate", "batched", "summary"
    val escalationEnabled: Boolean = false,
    val escalationDelay: Long = 1800, // seconds
    val personalizedMessages: Boolean = true,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true
)