package com.evirgenoguz.domain.model.home

data class CreateGroupDomainModel(
    val name: String,
    val description: String,
    val image: String? = null
)
