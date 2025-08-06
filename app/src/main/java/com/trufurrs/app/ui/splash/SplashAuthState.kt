package com.trufurrs.app.ui.splash

sealed class SplashAuthState {
    object Loading : SplashAuthState()
    data class Authenticated(val needsProfileCompletion: Boolean = false) : SplashAuthState()
    object NotAuthenticated : SplashAuthState()
    data class Error(val message: String) : SplashAuthState()
}