package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

// SENSE Tier Only
data class Vet(
    @DocumentId
    val vetId: String = "",
    val userId: String = "",

    // Encrypted vet information (for privacy)
    val encryptedClinicName: String = "",
    val encryptedVetName: String = "",
    val encryptedContact: EncryptedVetContact = EncryptedVetContact(),

    @ServerTimestamp
    val createdAt: Timestamp? = null
)

data class EncryptedVetContact(
    val encryptedPhone: String = "",
    val encryptedEmail: String = ""
)