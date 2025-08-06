package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class FamilyInvitation(
    @DocumentId
    val inviteId: String = "",

    // Invitation Details
    val fromUserId: String = "",
    val fromUserName: String = "",
    val fromUserEmail: String = "",
    val familyId: String = "",

    // Recipient Information
    val toEmail: String = "",
    val toName: String = "",
    val toPhone: String? = null,

    // Invitation Configuration
    val proposedRole: String = "family", // "family", "emergency", "vet", "trainer"
    val proposedPermissions: List<String> = emptyList(),

    // Access Scope
    val petAccess: PetAccess = PetAccess(),

    // Invitation Lifecycle
    val status: String = "sent", // "sent", "accepted", "declined", "expired", "revoked"
    val inviteCode: String = "",
    val inviteUrl: String = "",

    // Timing
    @ServerTimestamp
    val sentAt: Timestamp? = null,
    val expiresAt: Timestamp? = null,
    @ServerTimestamp
    val respondedAt: Timestamp? = null,
    @ServerTimestamp
    val acceptedAt: Timestamp? = null,

    // Communication
    val personalMessage: String? = null,
    val language: String = "en",
    val emailTemplate: String = "family_member_invite",

    // Tracking & Analytics
    val tracking: InvitationTracking = InvitationTracking(),

    // Security & Validation
    val security: InvitationSecurity = InvitationSecurity(),

    // Metadata
    val invitationType: String = "standard", // "standard", "emergency", "temporary"
    val priority: String = "normal", // "low", "normal", "high", "urgent"
    val source: String = "manual", // "manual", "bulk", "suggested", "auto"

    // Follow-up Actions
    val followUp: InvitationFollowUp = InvitationFollowUp(),

    // Integration Data
    val integrations: InvitationIntegrations = InvitationIntegrations()
)

data class PetAccess(
    val allPets: Boolean = true,
    val specificPets: List<String> = emptyList(),
    val futureAccessPermitted: Boolean = true
)

data class InvitationTracking(
    val emailSent: Boolean = false,
    val emailDelivered: Boolean = false,
    val emailOpened: Boolean = false,
    val remindersSent: Int = 0,
    val linkClicked: Boolean = false,
    val appDownloaded: Boolean = false,
    @ServerTimestamp
    val viewedAt: Timestamp? = null,
    val emailStats: EmailStats = EmailStats()
)

data class EmailStats(
    val sentCount: Int = 0,
    val openRate: Double = 0.0,
    val clickRate: Double = 0.0,
    val bounced: Boolean = false
)

data class InvitationSecurity(
    val ipAddress: String? = null,
    val userAgent: String? = null,
    val verificationRequired: Boolean = false,
    val twoFactorRequired: Boolean = false,
    val responseIP: String? = null,
    val responseUserAgent: String? = null,
    val securityFlags: List<String> = emptyList()
)

data class InvitationFollowUp(
    val reminderScheduled: Boolean = false,
    @ServerTimestamp
    val reminderSentAt: Timestamp? = null,
    val escalateToPhone: Boolean = false,
    val escalateToAdmin: Boolean = false,
    val autoExpire: Boolean = true,
    val onboardingRequired: Boolean = true,
    val welcomeMessageSent: Boolean = false,
    val tutorialAssigned: String = ""
)

data class InvitationIntegrations(
    val calendarInvite: Boolean = false,
    val slackNotification: Boolean = false,
    val emailListSubscription: String? = null
)