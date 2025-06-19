package com.evirgenoguz.presentation.home.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evirgenoguz.domain.model.groupdetail.GroupEventDomainModel
import com.evirgenoguz.presentation.home.databinding.ItemEventCardBinding
import com.evirgenoguz.presentation.home.home.EventsAdapter.EventViewHolder

class EventsAdapter(
    private val eventClickListener: (eventDomainModel: GroupEventDomainModel) -> Unit
) : ListAdapter<GroupEventDomainModel, EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            ItemEventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private class EventDiffCallback : DiffUtil.ItemCallback<GroupEventDomainModel>() {
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

    inner class EventViewHolder(private val binding: ItemEventCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: GroupEventDomainModel) {
            binding.textViewEventCardEventName.text = event.name
            binding.textViewEventCardEventDescription.text = event.description
            binding.eventCardAvatar.text = event.name?.first().toString()
            binding.eventCardEmoji.text = event.name?.first().toString()
            binding.root.setOnClickListener {
                eventClickListener.invoke(event)
            }
        }
    }

}