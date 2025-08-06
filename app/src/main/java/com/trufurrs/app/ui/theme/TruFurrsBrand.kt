package com.trufurrs.app.ui.theme

object TruFurrsBrand {
    // Brand Colors
    const val PRIMARY_DARK_GREEN = "#00291B"
    const val PRIMARY_CHARCOAL_GRAY = "#333333"
    const val SECONDARY_MUSTARD_GOLD = "#D3AF37"
    const val ACCENT_GOLD = "#D4AF37"
    const val ACCENT_SILVER = "#A8A8A8"

    // Common Colors
    const val WHITE = "#FFFFFF"
    const val BLACK = "#000000"
    const val ERROR_RED = "#F44336"
    const val SUCCESS_GREEN = "#4CAF50"
    const val WARNING_ORANGE = "#FF9800"

    // Typography
    const val FONT_PRIMARY = "Poppins"
    const val FONT_BACKUP = "Inter"
    const val FONT_SYSTEM = "Roboto"

    // Brand Name
    const val APP_NAME = "TruFurrs"

    // Tier Constants
    const val TIER_TAG = "TAG"
    const val TIER_ACTIVE = "ACTIVE"
    const val TIER_SENSE = "SENSE"

    // Tier-specific variations (using same brand colors but different emphasis)
    fun getTierPrimaryColor(tier: String): String {
        return PRIMARY_DARK_GREEN // Consistent across all tiers
    }

    fun getTierAccentColor(tier: String): String {
        return when (tier.uppercase()) {
            TIER_TAG -> ACCENT_SILVER
            TIER_ACTIVE -> SECONDARY_MUSTARD_GOLD
            TIER_SENSE -> ACCENT_GOLD
            else -> ACCENT_SILVER
        }
    }

    fun getTierDisplayName(tier: String): String {
        return when (tier.uppercase()) {
            TIER_TAG -> "$APP_NAME TAG"
            TIER_ACTIVE -> "$APP_NAME ACTIVE"
            TIER_SENSE -> "$APP_NAME SENSE"
            else -> APP_NAME
        }
    }

    // Brand taglines for different tiers
    fun getTierTagline(tier: String): String {
        return when (tier.uppercase()) {
            TIER_TAG -> "Find Your Pet"
            TIER_ACTIVE -> "Smart Pet Tracking"
            TIER_SENSE -> "Complete Pet Wellness"
            else -> "Pet Safety & Wellness"
        }
    }
}