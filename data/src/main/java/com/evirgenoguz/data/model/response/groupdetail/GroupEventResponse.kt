package com.evirgenoguz.data.model.response.groupdetail

import com.evirgenoguz.domain.model.groupdetail.CityDomainModel
import com.evirgenoguz.domain.model.groupdetail.CreatedByGroupDomainModel
import com.evirgenoguz.domain.model.groupdetail.CreatedByPersonDomainModel
import com.evirgenoguz.domain.model.groupdetail.DistrictDomainModel
import com.evirgenoguz.domain.model.groupdetail.EventLocationDomainModel
import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel
import com.evirgenoguz.domain.model.groupdetail.ParticipantsDomainModel
import com.evirgenoguz.domain.model.groupdetail.UserDomainModel
import com.google.gson.annotations.SerializedName

data class GroupEventResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("startDate") var startDate: String? = null,
    @SerializedName("endDate") var endDate: String? = null,
    @SerializedName("eventLocation") var eventLocation: EventLocation? = EventLocation(),
    @SerializedName("createdByPerson") var createdByPerson: CreatedByPerson? = CreatedByPerson(),
    @SerializedName("createdByGroup") var createdByGroup: CreatedByGroup? = CreatedByGroup(),
    @SerializedName("eventType") var eventType: String? = null,
    @SerializedName("eventCategory") var eventCategory: String? = null,
    @SerializedName("totalParticipant") var totalParticipant: Int? = null,
    @SerializedName("participants") var participants: ArrayList<Participants> = arrayListOf(),
    @SerializedName("eventStatus") var eventStatus: String? = null,
    @SerializedName("inviteCode") var inviteCode: String? = null,
    @SerializedName("isDeleted") var isDeleted: Boolean? = null
)

data class EventLocation(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("city") var city: City? = City(),
    @SerializedName("district") var district: District? = District()
)

data class City(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("countryId") var countryId: Int? = null
)

data class District(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("cityId") var cityId: Int? = null
)

data class CreatedByPerson(
    @SerializedName("email") var email: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("emailVerified") var emailVerified: Boolean? = null
)

data class CreatedByGroup(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("inviteCode") var inviteCode: String? = null,
    @SerializedName("pictureKey") var pictureKey: String? = null,
    @SerializedName("createdByUser") var createdByUser: String? = null,
    @SerializedName("createDate") var createDate: String? = null
)

data class User(
    @SerializedName("email") var email: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("emailVerified") var emailVerified: Boolean? = null
)

data class Participants(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("user") var user: User? = User(),
    @SerializedName("status") var status: String? = null,
    @SerializedName("isOrganizer") var isOrganizer: Boolean? = null,
    @SerializedName("invitedByUserId") var invitedByUserId: Int? = null
)

fun List<GroupEventResponse>.toDomainModel(): List<GroupEventDomainModel> {
    return this.map {
        GroupEventDomainModel(
            id = it.id,
            name = it.name,
            description = it.description,
            startDate = it.startDate,
            endDate = it.endDate,
            eventLocation = it.eventLocation?.toDomainModel(),
            createdByPerson = it.createdByPerson?.toDomainModel(),
            createdByGroup = it.createdByGroup?.toDomainModel(),
            eventType = it.eventType,
            eventCategory = it.eventCategory,
            totalParticipant = it.totalParticipant,
            participants = it.participants.toDomainModel() as ArrayList<ParticipantsDomainModel>,
            eventStatus = it.eventStatus,
            inviteCode = it.inviteCode,
            isDeleted = it.isDeleted
        )
    }
}

fun GroupEventResponse.toDomainModel(): GroupEventDomainModel {
    return GroupEventDomainModel(
        id = this.id,
        name = this.name,
        description = this.description,
        startDate = this.startDate,
        endDate = this.endDate,
        eventLocation = this.eventLocation?.toDomainModel(),
        createdByPerson = this.createdByPerson?.toDomainModel(),
        createdByGroup = this.createdByGroup?.toDomainModel(),
        eventType = this.eventType,
        eventCategory = this.eventCategory,
        totalParticipant = this.totalParticipant,
        participants = this.participants.toDomainModel() as ArrayList<ParticipantsDomainModel>,
        eventStatus = this.eventStatus,
        inviteCode = this.inviteCode,
        isDeleted = this.isDeleted
    )
}

fun EventLocation.toDomainModel(): EventLocationDomainModel {
    return EventLocationDomainModel(
        id = this.id,
        name = this.name,
        address = this.address,
        latitude = this.latitude,
        longitude = this.longitude,
        city = this.city?.toDomainModel(),
        district = this.district?.toDomainModel()
    )
}

fun City.toDomainModel(): CityDomainModel {
    return CityDomainModel(
        id = this.id,
        name = this.name,
        code = this.code,
        countryId = this.countryId
    )
}

fun District.toDomainModel(): DistrictDomainModel {
    return DistrictDomainModel(
        id = this.id,
        name = this.name,
        code = this.code,
        cityId = this.cityId
    )
}

fun CreatedByPerson.toDomainModel(): CreatedByPersonDomainModel {
    return CreatedByPersonDomainModel(
        email = this.email,
        username = this.username,
        emailVerified = this.emailVerified
    )
}

fun CreatedByGroup.toDomainModel(): CreatedByGroupDomainModel {
    return CreatedByGroupDomainModel(
        id = this.id,
        name = this.name,
        description = this.description,
        inviteCode = this.inviteCode,
        pictureKey = this.pictureKey,
        createdByUser = this.createdByUser,
        createDate = this.createDate
    )
}

fun ArrayList<Participants>.toDomainModel(): List<ParticipantsDomainModel> {
    return this.map {
        ParticipantsDomainModel(
            id = it.id,
            user = it.user?.toDomainModel(),
            status = it.status,
            isOrganizer = it.isOrganizer,
            invitedByUserId = it.invitedByUserId
        )
    }
}

fun User.toDomainModel(): UserDomainModel {
    return UserDomainModel(
        email = this.email,
        username = this.username,
        emailVerified = this.emailVerified
    )
}

