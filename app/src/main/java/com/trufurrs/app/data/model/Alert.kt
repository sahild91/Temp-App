package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp

data class Alert(
    @DocumentId
    val alertId: String = "",

    // Alert Identity
    val userId: String = "",
    val petId: String = "",
    val deviceId: String? = null,

    // Alert Classification
    val alertType: String = "", // Specific alert type from taxonomy
    val category: String = "", // "geofence", "device", "activity", "emergency", "health"
    val severity: String = "medium", // "low", "medium", "high", "critical"
    val tierRestriction: List<String> = emptyList(), // ["ACTIVE", "SENSE"]

    // Alert Content
    val title: String = "",
    val message: String = "",
    val shortMessage: String = "",

    // Timing
    @ServerTimestamp
    val timestamp: Timestamp? = null,
    val expiresAt: Timestamp? = null,

    // Rich Context Data
    val contextData: AlertContextData = AlertContextData(),

    // Status & Response
    val status: String = "active", // "active", "acknowledged", "resolved", "dismissed", "expired"
    @ServerTimestamp
    val acknowledgedAt: Timestamp? = null,
    val acknowledgedBy: String? = null,
    @ServerTimestamp
    val resolvedAt: Timestamp? = null,

    // Family Coordination
    val familyNotification: FamilyNotification = FamilyNotification(),

    // Smart Features & Learning
    val aiGenerated: Boolean = false,
    val suppressionRules: AlertSuppressionRules = AlertSuppressionRules(),

    // Machine Learning Data
    val userFeedback: AlertUserFeedback = AlertUserFeedback(),

    // Analytics & Performance
    val performance: AlertPerformance = AlertPerformance(),

    // Notification Tracking
    val notifications: List<AlertNotification> = emptyList(),

    // Related Data
    val relatedEvents: List<RelatedEvent> = emptyList(),

    // Learning & Improvement
    val learningData: AlertLearningData = AlertLearningData()
)

data class AlertContextData(
    val location: GeoPoint? = null,
    val geofenceId: String? = null,
    val geofenceName: String? = null,
    val batteryLevel: Int? = null,
    val speed: Double? = null, // km/h
    val direction: String? = null,
    val weather: String? = null,
    val timeContext: String? = null,
    val confidence: Double = 0.0 // AI confidence
)

data class FamilyNotification(
    val notifiedMembers: List<String> = emptyList(),
    val responseReceived: List<String> = emptyList(),
    val coordinatedResponse: Boolean = true,
    val primaryResponder: String? = null
)

data class AlertSuppressionRules(
    val similarAlertsWindow: Int = 300, // seconds
    val quietHoursOverride: Boolean = false,
    val familyCoordination: Boolean = true,
    val contextualSuppression: Boolean = true
)

data class AlertUserFeedback(
    val helpful: Boolean? = null,
    val falsePositive: Boolean? = null,
    val relevance: Int? = null, // 1-5
    @ServerTimestamp
    val feedbackAt: Timestamp? = null,
    val notes: String? = null
)

data class AlertPerformance(
    val responseTime: Long = 0, // seconds
    val engagementType: String = "", // "ignored", "dismissed", "acknowledged", "acted_upon"
    val followUpAction: String? = null,
    val resolution: String? = null,
    val accuracy: Boolean? = null
)

data class AlertNotification(
    val channel: String = "", // "push", "email", "sms"
    val recipient: String = "",
    @ServerTimestamp
    val sentAt: Timestamp? = null,
    @ServerTimestamp
    val deliveredAt: Timestamp? = null,
    @ServerTimestamp
    val readAt: Timestamp? = null,
    val clicked: Boolean = false
)

data class RelatedEvent(
    val type: String = "",
    val eventId: String = "",
    @ServerTimestamp
    val timestamp: Timestamp? = null
)

data class AlertLearningData(
    val patternMatch: Double = 0.0, // 0-1
    val anomalyScore: Double = 0.0, // 0-1
    val seasonalContext: String = "",
    val historicalComparison: String = "",
    val improvementSuggestions: List<String> = emptyList()
)