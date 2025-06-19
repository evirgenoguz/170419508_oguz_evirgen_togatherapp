package com.evirgenoguz.presentation.groupdetail.eventdetail

import java.io.Serializable

data class UsernameList(
    val usernameList: MutableList<String> = mutableListOf<String>()
) : Serializable
