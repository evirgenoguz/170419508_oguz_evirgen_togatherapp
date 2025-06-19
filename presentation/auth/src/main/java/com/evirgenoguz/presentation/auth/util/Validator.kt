package com.evirgenoguz.presentation.auth.util

object Validator {
    fun isValidEmail(email: String): String? {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        if (email.isEmpty()) return "Email is required"
        if (emailRegex.matches(email).not()) return "Please enter a valid email"
        return null
    }

    fun getPasswordValidationMessage(password: String): String? {
        if (password.isEmpty()) return "Password is required"
        if (password.length < 8) return "Password must be at least 8 characters"
        if (!password.any { it.isUpperCase() }) return "Password must contain an uppercase letter"
        if (!password.any { it.isLowerCase() }) return "Password must contain a lowercase letter"
        if (!password.any { it.isDigit() }) return "Password must contain a number"
        return null
    }
}