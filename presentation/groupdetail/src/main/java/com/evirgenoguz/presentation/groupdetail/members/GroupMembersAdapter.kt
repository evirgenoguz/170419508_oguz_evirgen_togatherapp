package com.evirgenoguz.presentation.groupdetail.members

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.evirgenoguz.domain.model.groupdetail.GroupMemberDomainModel
import com.evirgenoguz.presentation.groupdetail.databinding.ItemGroupDetailMemberBinding

class GroupMembersAdapter(
    private val groupMemberKickListener: (groupMemberDomainModel: GroupMemberDomainModel) -> Unit
) : ListAdapter<GroupMemberDomainModel, GroupMembersAdapter.GroupMemberViewHolder>(
    GroupMemberDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupMemberViewHolder {
        return GroupMemberViewHolder(
            ItemGroupDetailMemberBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: GroupMemberViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    private class GroupMemberDiffCallback : DiffUtil.ItemCallback<GroupMemberDomainModel>() {
        override fun areItemsTheSame(
            oldItem: GroupMemberDomainModel,
            newItem: GroupMemberDomainModel
        ): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(
            oldItem: GroupMemberDomainModel,
            newItem: GroupMemberDomainModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class GroupMemberViewHolder(private val binding: ItemGroupDetailMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(groupMember: GroupMemberDomainModel) {
            binding.textViewGroupMemberUsername.text = groupMember.username.orEmpty()
            binding.imageViewGroupMemberCardAvatar.load(groupMember.userImage)
            binding.textViewGroupMemberRole.text = groupMember.role
            binding.imageViewGroupDetailMemberKickUser.setOnClickListener {
                groupMemberKickListener(groupMember)
            }
        }
    }
}