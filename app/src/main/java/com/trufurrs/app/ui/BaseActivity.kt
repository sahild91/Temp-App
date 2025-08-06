package com.trufurrs.app.ui

import androidx.appcompat.app.AppCompatActivity
import com.trufurrs.app.utils.AppTier
import com.trufurrs.app.utils.TierConfig

abstract class BaseActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        // Apply tier-specific theme before setting content view
        setTheme(TierConfig.getThemeResId())
        super.setContentView(layoutResID)
    }

    protected fun isActiveMode() = TierConfig.currentTier == AppTier.ACTIVE
    protected fun isTagMode() = TierConfig.currentTier == AppTier.TAG
    protected fun isSenseMode() = TierConfig.currentTier == AppTier.SENSE

    protected fun getAppTitle(): String = TierConfig.getAppTitle()

    protected fun getBrandPrimaryColor(): String = TierConfig.getPrimaryColor()
    protected fun getBrandAccentColor(): String = TierConfig.getAccentColor()
}