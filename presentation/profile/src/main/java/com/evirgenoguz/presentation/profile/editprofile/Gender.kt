package com.evirgenoguz.presentation.profile.editprofile

enum class Gender(val value: String) {
    MALE("M"),
    FEMALE("F"),
    OTHER("O");

    companion object {
        fun getGenderText(value: String) = Gender.entries.find { it.value == value }?.name ?: "OTHER"
        fun getGender(text: String) = Gender.entries.find { it.name == text } ?: OTHER
    }
}