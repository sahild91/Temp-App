package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp

data class Activity(
    @DocumentId
    val activityId: String = "",
    val petId: String = "",
    val deviceId: String = "",

    // Activity Data
    @ServerTimestamp
    val timestamp: Timestamp? = null,
    val activityType: String = "", // "walk", "run", "play", "rest", "sleep", "training"
    val subType: String = "", // more specific classification
    val duration: Long = 0, // seconds
    val steps: Int? = null,
    val distance: Double? = null, // kilometers
    val calories: Int? = null,
    val intensity: String = "moderate", // "low", "moderate", "high", "intense"

    // Movement Analysis
    val movementData: MovementData = MovementData(),

    // Environmental Context
    val location: ActivityLocation = ActivityLocation(),

    // Quality & Confidence Metrics
    val qualityScore: Int = 0, // 0-100
    val confidence: Int = 0, // 0-100
    val dataCompleteness: Double = 0.0, // 0-1
    val anomalies: List<String> = emptyList(),

    // Social Context
    val socialActivity: SocialActivity = SocialActivity(),

    // Health & Wellness
    val healthMetrics: ActivityHealthMetrics = ActivityHealthMetrics(),

    // Goals & Progress
    val contributesToGoals: List<String> = emptyList(),
    val achievements: List<String> = emptyList(),
    val goalProgress: GoalProgress = GoalProgress(),

    // AI Analysis
    val analysis: ActivityAnalysis = ActivityAnalysis()
)

data class MovementData(
    val averageSpeed: Double? = null, // km/h
    val maxSpeed: Double? = null,
    val minSpeed: Double? = null,
    val elevationGain: Double? = null, // meters
    val elevationLoss: Double? = null,
    val routeCoordinates: List<GeoPoint> = emptyList(),
    val movementPattern: String = "regular", // "regular", "erratic", "excited", "lethargic"
    val pauseCount: Int = 0,
    val averagePauseDuration: Long = 0 // seconds
)

data class ActivityLocation(
    val startCoordinates: GeoPoint? = null,
    val endCoordinates: GeoPoint? = null,
    val primaryZone: String? = null,
    val zonesVisited: List<String> = emptyList(),
    val terrainType: String = "mixed", // "urban", "park", "beach", "trail", "mixed"
    val weather: ActivityWeather = ActivityWeather()
)

data class ActivityWeather(
    val condition: String = "",
    val temperature: Double = 0.0, // celsius
    val humidity: Int = 0, // percentage
    val windSpeed: Double = 0.0 // km/h
)

data class SocialActivity(
    val withFamily: Boolean = false,
    val familyMembers: List<String> = emptyList(),
    val withOtherPets: Boolean = false,
    val otherPets: List<String> = emptyList(),
    val withOtherDogs: Boolean = false,
    val interactions: Int = 0
)

data class ActivityHealthMetrics(
    val restingHeartRate: Int? = null, // for SENSE tier
    val averageHeartRate: Int? = null, // for SENSE tier
    val stressLevel: String = "low", // "low", "medium", "high"
    val fatigueLevel: String = "none", // "none", "low", "medium", "high"
    val recoveryTime: Long = 0, // seconds
    val hydrationNeeded: Boolean = false
)

data class GoalProgress(
    val stepsContribution: Int = 0,
    val durationContribution: Long = 0, // seconds
    val streakMaintained: Boolean = false
)

data class ActivityAnalysis(
    val behaviorClassification: String = "normal", // "normal", "unusual", "concerning"
    val energyLevel: String = "medium", // compared to baseline
    val enjoymentLevel: Double = 0.0, // 0-1
    val optimalActivity: Boolean = false,
    val recommendations: List<String> = emptyList(),
    val nextActivitySuggestion: String = ""
)