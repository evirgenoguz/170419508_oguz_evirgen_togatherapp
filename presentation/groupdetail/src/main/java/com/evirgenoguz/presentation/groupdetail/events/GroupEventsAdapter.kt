package com.evirgenoguz.presentation.groupdetail.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel
import com.evirgenoguz.domain.util.EventCategory
import com.evirgenoguz.presentation.groupdetail.databinding.ItemGroupDetailEventBinding

class GroupEventsAdapter(
    private val groupEventDetailClickListener: (groupEventDomainModel: GroupEventDomainModel) -> Unit,
) :
    ListAdapter<GroupEventDomainModel, GroupEventsAdapter.GroupEventViewHolder>(
        GroupEventDiffCallback()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupEventViewHolder {
        return GroupEventViewHolder(
            ItemGroupDetailEventBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(
        holder: GroupEventViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    private class GroupEventDiffCallback : DiffUtil.ItemCallback<GroupEventDomainModel>() {
        override fun areItemsTheSame(
            oldItem: GroupEventDomainModel,
            newItem: GroupEventDomainModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GroupEventDomainModel,
            newItem: GroupEventDomainModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class GroupEventViewHolder(private val binding: ItemGroupDetailEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(groupEvent: GroupEventDomainModel) {
            val emptySpot = getHowManyEmptySpot(groupEvent)
            binding.textViewGroupEventName.text = groupEvent.name.orEmpty()
            binding.imageViewGroupEventLayout.load(EventCategory.getImageUrl(groupEvent.eventCategory))
            binding.textViewGroupEventProvince.text = groupEvent.eventLocation?.city?.name.orEmpty()
            binding.textViewGroupEventDescription.text = groupEvent.description.orEmpty()
            binding.textViewGroupEventParticipantsCount.text = emptySpot
            binding.root.setOnClickListener { groupEventDetailClickListener(groupEvent) }
        }

        private fun getHowManyEmptySpot(groupEvent: GroupEventDomainModel): String {
            val total = groupEvent.totalParticipant ?: 0
            val participants = groupEvent.participants.size
            return "$participants/$total"
        }
    }

}