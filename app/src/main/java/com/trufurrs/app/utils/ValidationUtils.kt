package com.trufurrs.app.utils

import android.util.Patterns
import java.util.regex.Pattern

object ValidationUtils {

    private val NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]{2,50}$")
    private val PHONE_PATTERN = Pattern.compile("^[+]?[1-9]\\d{1,14}$")
    private val PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$")

    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidName(name: String): Boolean {
        return name.isNotEmpty() && NAME_PATTERN.matcher(name).matches()
    }

    fun isValidPhone(phone: String): Boolean {
        return phone.isEmpty() || PHONE_PATTERN.matcher(phone.replace(" ", "")).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty() && PASSWORD_PATTERN.matcher(password).matches()
    }
}