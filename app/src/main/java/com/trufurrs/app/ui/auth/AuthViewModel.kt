package com.trufurrs.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.trufurrs.app.data.model.User
import com.trufurrs.app.data.repository.UserRepository
import com.trufurrs.app.utils.TierConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            try {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener { result ->
                        val user = result.user
                        if (user != null) {
                            loadUserProfile(user.uid)
                        } else {
                            _authState.value = AuthState.Error("Authentication failed")
                        }
                    }
                    .addOnFailureListener { exception ->
                        _authState.value = AuthState.Error(exception.message ?: "Sign in failed")
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Sign in failed")
            }
        }
    }

    fun signUpWithEmail(fullName: String, email: String, phoneNumber: String?, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { result ->
                        val firebaseUser = result.user
                        if (firebaseUser != null) {
                            createUserProfile(
                                uid = firebaseUser.uid,
                                displayName = fullName,
                                email = email,
                                phoneNumber = phoneNumber,
                                photoURL = null
                            )
                        } else {
                            _authState.value = AuthState.Error("Account creation failed")
                        }
                    }
                    .addOnFailureListener { exception ->
                        _authState.value = AuthState.Error(exception.message ?: "Account creation failed")
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Account creation failed")
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener { result ->
                        val user = result.user
                        if (user != null) {
                            // Check if this is a new user or existing user
                            loadUserProfile(user.uid)
                        } else {
                            _authState.value = AuthState.Error("Google Sign-In failed")
                        }
                    }
                    .addOnFailureListener { exception ->
                        _authState.value = AuthState.Error(exception.message ?: "Google Sign-In failed")
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Google Sign-In failed")
            }
        }
    }

    fun signUpWithGoogle(idToken: String, displayName: String, email: String, photoUrl: String?) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener { result ->
                        val firebaseUser = result.user
                        if (firebaseUser != null) {
                            createUserProfile(
                                uid = firebaseUser.uid,
                                displayName = displayName,
                                email = email,
                                phoneNumber = firebaseUser.phoneNumber,
                                photoURL = photoUrl
                            )
                        } else {
                            _authState.value = AuthState.Error("Google Sign-Up failed")
                        }
                    }
                    .addOnFailureListener { exception ->
                        _authState.value = AuthState.Error(exception.message ?: "Google Sign-Up failed")
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Google Sign-Up failed")
            }
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            try {
                firebaseAuth.sendPasswordResetEmail(email)
                // Success is handled in the activity with a toast message
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Password reset failed")
            }
        }
    }

    private fun loadUserProfile(userId: String) {
        viewModelScope.launch {
            userRepository.getUserById(userId).fold(
                onSuccess = { user ->
                    if (user != null) {
                        // Load tier configuration
                        TierConfig.updateTierFromDevice(user.tier)
                        _authState.value = AuthState.Success(user)
                    } else {
                        // User document doesn't exist, this shouldn't happen after auth
                        _authState.value = AuthState.Error("User profile not found")
                    }
                },
                onFailure = { error ->
                    _authState.value = AuthState.Error(error.message ?: "Failed to load profile")
                }
            )
        }
    }

    private fun createUserProfile(uid: String, displayName: String, email: String, phoneNumber: String?, photoURL: String?) {
        viewModelScope.launch {
            val newUser = User(
                userId = uid,
                displayName = displayName,
                email = email,
                phoneNumber = phoneNumber,
                photoURL = photoURL,
                tier = "TAG", // Always start with TAG
                profileIncomplete = true // Profile completion will happen later
            )

            userRepository.createUser(newUser).fold(
                onSuccess = {
                    _authState.value = AuthState.Success(newUser)
                },
                onFailure = { error ->
                    _authState.value = AuthState.Error(error.message ?: "Failed to create profile")
                }
            )
        }
    }

    fun completeProfile(userId: String, additionalData: Map<String, Any>) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            val updates = additionalData + mapOf("profileIncomplete" to false)

            userRepository.completeUserProfile(userId, updates).fold(
                onSuccess = {
                    _authState.value = AuthState.ProfileCompleted
                },
                onFailure = { error ->
                    _authState.value = AuthState.Error(error.message ?: "Profile completion failed")
                }
            )
        }
    }

    fun updateUserTierAfterPairing(userId: String, deviceType: String) {
        viewModelScope.launch {
            // Update local tier config
            TierConfig.updateTierFromDevice(deviceType)

            // Update user's tier in database
            userRepository.updateUserTier(userId, deviceType).fold(
                onSuccess = {
                    _authState.value = AuthState.TierUpdated(deviceType)
                },
                onFailure = { error ->
                    _authState.value = AuthState.Error(error.message ?: "Tier update failed")
                }
            )
        }
    }
}