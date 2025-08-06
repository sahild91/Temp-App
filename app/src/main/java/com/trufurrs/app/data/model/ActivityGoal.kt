package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class ActivityGoal(
    @DocumentId
    val goalId: String = "",
    val petId: String = "",
    val userId: String = "",

    // Goal Definition
    val goalType: String = "", // "daily_steps", "weekly_exercise", "weight_management", "behavioral"
    val title: String = "",
    val description: String = "",
    val category: String = "fitness", // "fitness", "health", "training", "social"

    // Target & Progress
    val target: GoalTarget = GoalTarget(),
    val currentProgress: GoalCurrentProgress = GoalCurrentProgress(),

    // Schedule & Timing
    val isActive: Boolean = true,
    @ServerTimestamp
    val startDate: Timestamp? = null,
    val endDate: Timestamp? = null,
    val pausedAt: Timestamp? = null,

    // Customization
    val difficulty: String = "moderate", // "easy", "moderate", "challenging", "expert"
    val priority: Int = 3, // 1-5
    val visibility: String = "private", // "private", "family", "public"

    // AI Optimization
    val aiRecommended: Boolean = false,
    val adaptiveTarget: Boolean = true,
    val personalizedFactors: PersonalizedFactors = PersonalizedFactors(),

    // Progress Tracking
    val history: List<GoalHistoryEntry> = emptyList(),

    // Streaks & Achievements
    val streaks: GoalStreaks = GoalStreaks(),
    val achievements: GoalAchievements = GoalAchievements(),

    // Analytics & Insights
    val analytics: GoalAnalytics = GoalAnalytics(),

    // Motivation & Rewards
    val motivation: GoalMotivation = GoalMotivation(),

    @ServerTimestamp
    val createdAt: Timestamp? = null,
    @ServerTimestamp
    val updatedAt: Timestamp? = null,
    @ServerTimestamp
    val lastAchieved: Timestamp? = null,
    val nextMilestone: NextMilestone = NextMilestone()
)

data class GoalTarget(
    val value: Int = 0,
    val unit: String = "", // "steps", "minutes", "calories", "km", "count"
    val period: String = "daily", // "daily", "weekly", "monthly", "custom"
    val customPeriodDays: Int? = null
)

data class GoalCurrentProgress(
    val value: Int = 0,
    val percentage: Double = 0.0, // 0-100
    @ServerTimestamp
    val lastUpdated: Timestamp? = null,
    val trend: String = "stable", // "improving", "stable", "declining"
    val projectedCompletion: Timestamp? = null
)

data class PersonalizedFactors(
    val breed: String = "",
    val age: Int = 0, // years
    val weight: Double = 0.0, // kg
    val activityLevel: String = "medium",
    val healthConditions: List<String> = emptyList()
)

data class GoalHistoryEntry(
    val date: String = "", // YYYY-MM-DD
    val achieved: Boolean = false,
    val value: Int = 0,
    val percentage: Double = 0.0,
    val notes: String = ""
)

data class GoalStreaks(
    val current: Int = 0,
    val longest: Int = 0,
    val thisMonth: Int = 0,
    val total: Int = 0
)

data class GoalAchievements(
    val totalAchievements: Int = 0,
    val milestones: List<GoalMilestone> = emptyList()
)

data class GoalMilestone(
    val type: String = "",
    @ServerTimestamp
    val achievedAt: Timestamp? = null,
    val value: Int = 0
)

data class GoalAnalytics(
    val averageCompletion: Double = 0.0, // 0-1
    val bestDay: String = "",
    val worstDay: String = "",
    val seasonalTrends: SeasonalTrends = SeasonalTrends(),
    val correlations: GoalCorrelations = GoalCorrelations()
)

data class SeasonalTrends(
    val spring: Double = 0.0,
    val summer: Double = 0.0,
    val fall: Double = 0.0,
    val winter: Double = 0.0
)

data class GoalCorrelations(
    val weather: Double = 0.0,
    val familyPresence: Double = 0.0,
    val daylight: Double = 0.0
)

data class GoalMotivation(
    val rewardSystem: Boolean = false,
    val rewards: List<GoalReward> = emptyList(),
    val motivationalMessages: List<String> = emptyList()
)

data class GoalReward(
    val type: String = "",
    val trigger: String = "",
    val description: String = ""
)

data class NextMilestone(
    val type: String = "",
    val requiredValue: Int = 0,
    val currentProgress: Int = 0
)