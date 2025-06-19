package com.evirgenoguz.presentation.home.home.state

import com.evirgenoguz.domain.model.GroupDomainModel
import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel
import com.evirgenoguz.domain.model.profile.ProfileUserModel

data class HomeUiState(
    val userHomeInfo: HomeUserModel,
    val userGroupList: List<GroupDomainModel>,
    val userEventList: List<GroupEventDomainModel>,
    val selectedType: SelectedType,
    val userModel: ProfileUserModel = ProfileUserModel(),
) {
    companion object {
        fun initial(): HomeUiState = HomeUiState(
            userHomeInfo = HomeUserModel(
                name = "Oguz Evirgen",
                nickName = "oguz3virgen",
                biography = "Friendly Ordinary Man",
                credit = 87,
                reliability = "Reliable"
            ),
            userGroupList = emptyList(),
            userEventList = emptyList(),
            selectedType = SelectedType.Group
        )
    }
}

data class HomeUserModel(
    val name: String,
    val nickName: String,
    val biography: String,
    val credit: Int,
    val reliability: String
)
