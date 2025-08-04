package com.trufurrs.app.ui

import androidx.appcompat.app.AppCompatActivity
import com.trufurrs.app.utils.AppTier
import com.trufurrs.app.utils.TierConfig

abstract class BaseActivity : AppCompatActivity() {
    protected fun isActiveMode() = TierConfig.currentTier == AppTier.ACTIVE
    protected fun isTagMode() = TierConfig.currentTier == AppTier.TAG

    protected fun getAppTitle(): String {
        return if (isActiveMode()) "TruFurrs ACTIVE" else "TruFurrs TAG"
    }
}