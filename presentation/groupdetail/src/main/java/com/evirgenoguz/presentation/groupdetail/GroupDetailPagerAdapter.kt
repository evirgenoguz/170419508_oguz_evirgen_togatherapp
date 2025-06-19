package com.evirgenoguz.presentation.groupdetail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.evirgenoguz.presentation.groupdetail.events.GroupDetailEventsFragment
import com.evirgenoguz.presentation.groupdetail.history.GroupDetailHistoryFragment
import com.evirgenoguz.presentation.groupdetail.members.GroupDetailMembersFragment

class GroupDetailPagerAdapter(
    fragment: Fragment,
    inviteCode: String,
    groupId: Int,
) : FragmentStateAdapter(fragment) {

    private val fragments = listOf(
        GroupDetailEventsFragment(inviteCode, groupId),
        GroupDetailMembersFragment(inviteCode),
        GroupDetailHistoryFragment(groupId)
    )

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}