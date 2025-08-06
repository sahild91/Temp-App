package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class PetManagementRequest(
    @DocumentId
    val requestId: String = "",

    val familyUserId: String = "",
    val familyId: String = "",
    val petId: String? = null, // null for create requests
    val action: String = "", // "create", "update", "delete"

    // Encrypted request details
    val encryptedDetails: Map<String, Any> = emptyMap(),

    val status: String = "pending", // "pending", "approved", "rejected"
    @ServerTimestamp
    val submittedAt: Timestamp? = null,
    val reviewedBy: String? = null,
    @ServerTimestamp
    val reviewedAt: Timestamp? = null
)