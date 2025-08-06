package com.trufurrs.app.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.trufurrs.app.data.repository.UserRepository
import com.trufurrs.app.utils.TierConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _authState = MutableStateFlow<SplashAuthState>(SplashAuthState.Loading)
    val authState: StateFlow<SplashAuthState> = _authState

    private var splashCompleted = false
    private var authCheckCompleted = false

    fun initializeApp() {
        viewModelScope.launch {
            try {
                checkAuthenticationState()
            } catch (e: Exception) {
                _authState.value = SplashAuthState.Error("Failed to initialize app: ${e.message}")
            }
        }
    }

    fun completeSplashDuration() {
        splashCompleted = true
        checkIfReadyToNavigate()
    }

    private suspend fun checkAuthenticationState() {
        try {
            val currentUser = firebaseAuth.currentUser

            if (currentUser == null) {
                // User not logged in
                authCheckCompleted = true
                _authState.value = SplashAuthState.NotAuthenticated
                checkIfReadyToNavigate()
            } else {
                // User is logged in, check profile completion and load tier
                checkUserProfile(currentUser.uid)
            }
        } catch (e: Exception) {
            authCheckCompleted = true
            _authState.value = SplashAuthState.Error("Authentication check failed: ${e.message}")
            checkIfReadyToNavigate()
        }
    }

    private suspend fun checkUserProfile(userId: String) {
        userRepository.getUserById(userId).fold(
            onSuccess = { user ->
                if (user != null) {
                    // Load user's tier configuration
                    TierConfig.updateTierFromDevice(user.tier)

                    if (user.profileIncomplete) {
                        // Profile needs completion, but user is authenticated
                        authCheckCompleted = true
                        _authState.value = SplashAuthState.Authenticated(needsProfileCompletion = true)
                    } else {
                        // User is fully set up
                        authCheckCompleted = true
                        _authState.value = SplashAuthState.Authenticated(needsProfileCompletion = false)
                    }
                } else {
                    // User document doesn't exist, needs profile creation
                    authCheckCompleted = true
                    _authState.value = SplashAuthState.Authenticated(needsProfileCompletion = true)
                }
                checkIfReadyToNavigate()
            },
            onFailure = { error ->
                authCheckCompleted = true
                _authState.value = SplashAuthState.Error("Failed to load user profile: ${error.message}")
                checkIfReadyToNavigate()
            }
        )
    }

    private fun checkIfReadyToNavigate() {
        // Only navigate when both splash duration is complete AND auth check is done
        if (splashCompleted && authCheckCompleted) {
            // Navigation will be handled by the activity based on current state
            return
        }
    }
}