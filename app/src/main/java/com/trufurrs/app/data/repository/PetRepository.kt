package com.trufurrs.app.data.repository

import com.trufurrs.app.data.model.Pet
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    suspend fun createPet(pet: Pet): Result<String>
    suspend fun getPetById(petId: String): Result<Pet?>
    suspend fun getPetsByUserId(userId: String): Result<List<Pet>>
    suspend fun updatePet(pet: Pet): Result<Unit>
    suspend fun updatePetDevice(petId: String, deviceInfo: com.trufurrs.app.data.model.DeviceInfo): Result<Unit>
    suspend fun deletePet(petId: String): Result<Unit>
    fun observePets(userId: String): Flow<List<Pet>>
}