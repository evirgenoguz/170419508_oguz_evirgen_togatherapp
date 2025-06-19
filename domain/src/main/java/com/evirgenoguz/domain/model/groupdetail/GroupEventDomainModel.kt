package com.evirgenoguz.domain.model.groupdetail

import java.io.Serializable

data class GroupEventDomainModel(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var eventLocation: EventLocationDomainModel? = EventLocationDomainModel(),
    var createdByPerson: CreatedByPersonDomainModel? = CreatedByPersonDomainModel(),
    var createdByGroup: CreatedByGroupDomainModel? = CreatedByGroupDomainModel(),
    var eventType: String? = null,
    var eventCategory: String? = null,
    var totalParticipant: Int? = null,
    var participants: ArrayList<ParticipantsDomainModel> = arrayListOf(),
    var eventStatus: String? = null,
    var inviteCode: String? = null,
    var isDeleted: Boolean? = null
) : Serializable

data class EventLocationDomainModel(
    var id: Int? = null,
    var name: String? = null,
    var address: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var city: CityDomainModel? = CityDomainModel(),
    var district: DistrictDomainModel? = DistrictDomainModel()
)

data class CityDomainModel(
    var id: Int? = null,
    var name: String? = null,
    var code: String? = null,
    var countryId: Int? = null
)

data class DistrictDomainModel(
    var id: Int? = null,
    var name: String? = null,
    var code: String? = null,
    var cityId: Int? = null
)

data class CreatedByPersonDomainModel(
    var email: String? = null,
    var username: String? = null,
    var emailVerified: Boolean? = null
)

data class CreatedByGroupDomainModel(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var inviteCode: String? = null,
    var pictureKey: String? = null,
    var createdByUser: String? = null,
    var createDate: String? = null
)

data class UserDomainModel(
    var email: String? = null,
    var username: String? = null,
    var emailVerified: Boolean? = null
)

data class ParticipantsDomainModel(
    var id: Int? = null,
    var user: UserDomainModel? = UserDomainModel(),
    var status: String? = null,
    var isOrganizer: Boolean? = null,
    var invitedByUserId: Int? = null
)