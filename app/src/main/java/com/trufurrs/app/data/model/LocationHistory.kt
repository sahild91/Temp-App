package com.trufurrs.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp

data class LocationHistory(
    @DocumentId
    val historyId: String = "",

    @ServerTimestamp
    val timestamp: Timestamp? = null,
    val coordinates: GeoPoint? = null,
    val accuracy: Double = 0.0,
    val zone: String = "",
    val source: String = "", // "gps", "wifi", "cellular", "ble"
    val speed: Double? = null, // km/h
    val heading: Double? = null, // degrees
    val confidence: String = "low", // "high", "medium", "low"
    val batteryLevel: Int? = null,
    val significance: String = "routine", // "routine", "unusual", "emergency"

    // Environmental Context
    val weather: LocationWeather = LocationWeather(),
    val duration: Long = 0, // seconds at this location
    val notes: String? = null
)

data class LocationWeather(
    val condition: String = "",
    val temperature: Double = 0.0, // celsius
    val humidity: Int = 0 // percentage
)