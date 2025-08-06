package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class Family(
    @DocumentId
    val familyId: String = "",
    val adminUserId: String = "",
    val familyName: String = "",
    @ServerTimestamp
    val createdAt: Timestamp? = null,
    @ServerTimestamp
    val updatedAt: Timestamp? = null,
    val members: List<FamilyMember> = emptyList(),
    val sharedSettings: SharedFamilySettings = SharedFamilySettings(),
    val analytics: FamilyAnalytics = FamilyAnalytics()
)

data class FamilyMember(
    val userId: String = "",
    val displayName: String = "",
    val email: String = "",
    val role: String = "family", // "owner", "family", "emergency", "vet", "trainer"
    val permissions: List<String> = emptyList(), // ["view", "alerts", "emergency", "settings", "manage_family"]
    @ServerTimestamp
    val addedAt: Timestamp? = null,
    val addedBy: String = "",
    val status: String = "active", // "active", "invited", "disabled"
    @ServerTimestamp
    val lastActive: Timestamp? = null
)

data class SharedFamilySettings(
    val emergencyContactOrder: List<String> = emptyList(), // Ordered list of userIds
    val allowChildAccounts: Boolean = false,
    val requireApprovalForNewPets: Boolean = true,
    val sharedNotifications: Boolean = true,
    val alertCoordination: Boolean = true
)

data class FamilyAnalytics(
    val totalPets: Int = 0,
    val totalDevices: Int = 0,
    val activeMembers: Int = 0,
    val sharedActivities: Int = 0
)