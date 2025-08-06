package com.trufurrs.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.trufurrs.app.utils.TierConfig
import com.trufurrs.app.utils.AppTier

@HiltAndroidApp
class TruFurrsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        loadTierConfiguration()
    }

    private fun loadTierConfiguration() {
        TierConfig.currentTier = AppTier.ACTIVE
    }
}