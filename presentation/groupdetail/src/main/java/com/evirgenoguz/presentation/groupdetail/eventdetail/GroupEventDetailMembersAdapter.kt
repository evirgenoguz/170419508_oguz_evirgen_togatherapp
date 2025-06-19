package com.evirgenoguz.presentation.groupdetail.eventdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.evirgenoguz.domain.model.groupdetail.ParticipantsDomainModel
import com.evirgenoguz.presentation.groupdetail.R
import com.evirgenoguz.presentation.groupdetail.databinding.ItemEventDetailParticipantBinding

class GroupEventDetailMembersAdapter() :
    ListAdapter<ParticipantsDomainModel, GroupEventDetailMembersAdapter.GroupEventDetailMemberViewHolder>(
        GroupEventDetailMemberDiffCallback()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupEventDetailMemberViewHolder {
        return GroupEventDetailMemberViewHolder(
            ItemEventDetailParticipantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: GroupEventDetailMemberViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    private class GroupEventDetailMemberDiffCallback :
        DiffUtil.ItemCallback<ParticipantsDomainModel>() {
        override fun areItemsTheSame(
            oldItem: ParticipantsDomainModel,
            newItem: ParticipantsDomainModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ParticipantsDomainModel,
            newItem: ParticipantsDomainModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class GroupEventDetailMemberViewHolder(private val binding: ItemEventDetailParticipantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(groupMember: ParticipantsDomainModel) {
            binding.textViewEventDetailStatus.text = groupMember.status.orEmpty()
            binding.textViewEventDetailParticipantUsername.text = groupMember.user?.username
            binding.imageViewEventDetailParticipantCardAvatar.load("https://fastly.picsum.photos/id/446/200/200.jpg?hmac=PkaLcCtgL4IvAz-gsxbCXz_tl0qdVUGOrxhYLrywa-c")
            binding.textViewEventDetailParticipantRole.text = if (groupMember.isOrganizer == true) {
                itemView.context.getString(R.string.admin)
            } else {
                itemView.context.getString(R.string.member)
            }
        }
    }
}