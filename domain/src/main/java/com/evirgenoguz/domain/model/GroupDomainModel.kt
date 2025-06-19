package com.evirgenoguz.domain.model

data class GroupDomainModel(
    val id: Int,
    val name: String,
    val description: String,
    val inviteCode: String,
    val pictureKey: String,
    val createdByUser: String,
    val createDate: String
)