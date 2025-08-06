package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

// SENSE Tier Only
data class HealthData(
    @DocumentId
    val healthId: String = "",
    val petId: String = "",

    @ServerTimestamp
    val timestamp: Timestamp? = null,

    // Encrypted health metrics (AES-256-GCM)
    val heartRate: String? = null, // Encrypted
    val temperature: String? = null, // Encrypted
    val respiratoryRate: String? = null, // Encrypted
    val stressLevel: String? = null, // Encrypted
    val sleep: String? = null, // Encrypted sleep data
    val seizure: String? = null // Encrypted seizure detection
)