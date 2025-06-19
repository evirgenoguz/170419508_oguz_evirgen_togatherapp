package com.evirgenoguz.domain.model

data class Event(
    val name: String,
    val params: Map<String, Any> = emptyMap()
)
