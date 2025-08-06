package com.trufurrs.app

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.trufurrs.app.ui.theme.TruFurrsBrand
import com.trufurrs.app.utils.AppTier
import com.trufurrs.app.utils.TierConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TruFurrsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeBrandConfiguration()
        loadTierConfiguration()
        setupGlobalAppearance()
    }

    private fun initializeBrandConfiguration() {
        // Set up global brand configuration
        // This ensures brand consistency across all activities

        // Force Material Design 3 theming
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        // Set application label to brand name
        // This will be used for app switcher and notifications
        setApplicationLabel(TruFurrsBrand.APP_NAME)
    }

    private fun loadTierConfiguration() {
        // Load tier from shared preferences or remote config
        // For now, set to TAG as default - will be updated after device pairing
        val savedTier = loadSavedTierFromPreferences()

        TierConfig.currentTier = when (savedTier) {
            TruFurrsBrand.TIER_ACTIVE -> AppTier.ACTIVE
            TruFurrsBrand.TIER_SENSE -> AppTier.SENSE
            else -> AppTier.TAG // Default
        }

        // Apply tier-specific configurations
        applyTierSpecificConfiguration()
    }

    private fun setupGlobalAppearance() {
        // Set up global appearance settings that persist across activities

        // Configure status bar appearance globally
        configureStatusBarAppearance()

        // Set up notification appearance with brand colors
        configureNotificationAppearance()

        // Apply brand-specific font scaling if needed
        applyBrandTypography()
    }

    private fun loadSavedTierFromPreferences(): String {
        val sharedPrefs = getSharedPreferences("trufurrs_prefs", MODE_PRIVATE)
        return sharedPrefs.getString("user_tier", "TAG") ?: "TAG"
    }

    private fun applyTierSpecificConfiguration() {
        val currentTier = TierConfig.currentTier

        // Log tier initialization for debugging
        android.util.Log.d("TruFurrs", "Application initialized with tier: ${currentTier.name}")
        android.util.Log.d("TruFurrs", "App Title: ${TierConfig.getAppTitle()}")
        android.util.Log.d("TruFurrs", "Primary Color: ${TierConfig.getPrimaryColor()}")
        android.util.Log.d("TruFurrs", "Accent Color: ${TierConfig.getAccentColor()}")

        // Set up tier-specific analytics properties
        setupTierAnalytics(currentTier)

        // Configure tier-specific features
        configureTierFeatures(currentTier)
    }

    private fun configureStatusBarAppearance() {
        // This will be applied by individual activities via BaseActivity
        // but we can set global defaults here

        // Set up global window flags if needed
        // Most status bar theming will be handled per-activity
    }

    private fun configureNotificationAppearance() {
        // Set up notification channel with brand colors
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as android.app.NotificationManager

            // Create notification channels with brand-appropriate styling
            createBrandedNotificationChannels(notificationManager)
        }
    }

    @androidx.annotation.RequiresApi(android.os.Build.VERSION_CODES.O)
    private fun createBrandedNotificationChannels(notificationManager: android.app.NotificationManager) {
        // Emergency/SOS notifications (High priority, brand red/critical color)
        val emergencyChannel = android.app.NotificationChannel(
            "emergency_alerts",
            "Emergency Alerts",
            android.app.NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Critical pet safety alerts"
            enableLights(true)
            lightColor = android.graphics.Color.parseColor(TruFurrsBrand.ERROR_RED)
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 250, 250, 250)
        }

        // General alerts (Normal priority, brand accent color)
        val generalChannel = android.app.NotificationChannel(
            "general_alerts",
            "Pet Alerts",
            android.app.NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "General pet notifications"
            enableLights(true)
            lightColor = android.graphics.Color.parseColor(TruFurrsBrand.SECONDARY_MUSTARD_GOLD)
            enableVibration(true)
        }

        // Activity updates (Low priority, subtle)
        val activityChannel = android.app.NotificationChannel(
            "activity_updates",
            "Activity Updates",
            android.app.NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Pet activity and goal updates"
            enableLights(false)
            enableVibration(false)
        }

        notificationManager.createNotificationChannels(listOf(
            emergencyChannel,
            generalChannel,
            activityChannel
        ))
    }

    private fun applyBrandTypography() {
        // Set up global typography scaling if needed
        // Most typography will be handled via themes and styles

        // Configure font loading for Poppins if using downloadable fonts
        // or ensure local fonts are properly loaded
        try {
            // Preload brand fonts to avoid delays
            preloadBrandFonts()
        } catch (e: Exception) {
            android.util.Log.w("TruFurrs", "Failed to preload brand fonts, falling back to system fonts", e)
        }
    }

    private fun preloadBrandFonts() {
        // Preload Poppins fonts to ensure smooth UI rendering
        val fontIds = listOf(
            R.font.poppins_bold,
            R.font.poppins_medium,
            R.font.poppins_regular,
            R.font.poppins_light,
            R.font.poppins_light_italic
        )

        fontIds.forEach { fontId ->
            try {
                androidx.core.content.res.ResourcesCompat.getFont(this, fontId)
            } catch (e: Exception) {
                android.util.Log.w("TruFurrs", "Failed to preload font resource: $fontId", e)
            }
        }
    }

    private fun setupTierAnalytics(tier: AppTier) {
        // Set up analytics properties for the current tier
        // This helps track user behavior across different tier experiences

        val analyticsProperties = mapOf(
            "app_tier" to tier.name,
            "app_title" to TierConfig.getAppTitle(),
            "brand_primary_color" to TierConfig.getPrimaryColor(),
            "brand_accent_color" to TierConfig.getAccentColor(),
            "features_enabled" to getTierFeaturesList(tier)
        )

        // Log analytics setup (replace with your analytics SDK)
        android.util.Log.d("TruFurrs Analytics", "Tier analytics configured: $analyticsProperties")
    }

    private fun configureTierFeatures(tier: AppTier) {
        // Configure global feature flags based on tier
        when (tier) {
            AppTier.TAG -> {
                // Basic features only
                android.util.Log.d("TruFurrs", "TAG tier configured: Basic tracking enabled")
            }
            AppTier.ACTIVE -> {
                // Advanced features
                android.util.Log.d("TruFurrs", "ACTIVE tier configured: Full tracking + geofencing enabled")
            }
            AppTier.SENSE -> {
                // Premium features
                android.util.Log.d("TruFurrs", "SENSE tier configured: All features + health monitoring enabled")
            }
        }
    }

    private fun getTierFeaturesList(tier: AppTier): List<String> {
        return when (tier) {
            AppTier.TAG -> listOf("basic_tracking", "find_my_pet", "battery_alerts")
            AppTier.ACTIVE -> listOf("real_time_tracking", "geofencing", "activity_monitoring", "smart_alerts", "family_sharing")
            AppTier.SENSE -> listOf("real_time_tracking", "geofencing", "activity_monitoring", "smart_alerts", "family_sharing", "health_monitoring", "vet_integration")
        }
    }

    private fun setApplicationLabel(appName: String) {
        // This method would be used if we need to dynamically change app name
        // For now, it's handled in AndroidManifest.xml with @string/app_name
        android.util.Log.d("TruFurrs", "Application label set to: $appName")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Handle configuration changes (like dark/light mode)
        // while maintaining brand consistency
        handleBrandConfigurationChange(newConfig)
    }

    private fun handleBrandConfigurationChange(config: Configuration) {
        // Ensure brand colors and fonts remain consistent across configuration changes
        when (config.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                android.util.Log.d("TruFurrs", "Dark mode enabled - maintaining brand colors")
                // Brand colors stay the same in dark mode
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                android.util.Log.d("TruFurrs", "Light mode enabled - maintaining brand colors")
            }
        }
    }

    // Public methods for tier updates (called after device pairing)
    fun updateApplicationTier(newTier: AppTier) {
        TierConfig.currentTier = newTier

        // Save tier to preferences
        getSharedPreferences("trufurrs_prefs", MODE_PRIVATE)
            .edit()
            .putString("user_tier", newTier.name)
            .apply()

        // Update analytics
        setupTierAnalytics(newTier)

        // Log tier change
        android.util.Log.d("TruFurrs", "Application tier updated to: ${newTier.name}")
        android.util.Log.d("TruFurrs", "New app title: ${TierConfig.getAppTitle()}")
    }

    fun getBrandInfo(): Map<String, String> {
        return mapOf(
            "app_name" to TruFurrsBrand.APP_NAME,
            "current_tier" to TierConfig.currentTier.name,
            "app_title" to TierConfig.getAppTitle(),
            "primary_color" to TierConfig.getPrimaryColor(),
            "accent_color" to TierConfig.getAccentColor(),
            "font_primary" to TruFurrsBrand.FONT_PRIMARY,
            "font_backup" to TruFurrsBrand.FONT_BACKUP
        )
    }
}