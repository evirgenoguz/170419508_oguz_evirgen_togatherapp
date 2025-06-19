package com.evirgenoguz.presentation.home.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evirgenoguz.domain.model.GroupDomainModel
import com.evirgenoguz.presentation.home.databinding.ItemGroupCardBinding
import com.evirgenoguz.presentation.home.home.GroupsAdapter.GroupViewHolder

class GroupsAdapter(
    private val groupClickListener: (groupDomainModel: GroupDomainModel) -> Unit
) : ListAdapter<GroupDomainModel, GroupViewHolder>(GroupModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(
            ItemGroupCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private class GroupModelDiffCallback : DiffUtil.ItemCallback<GroupDomainModel>() {
        override fun areItemsTheSame(
            oldItem: GroupDomainModel,
            newItem: GroupDomainModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GroupDomainModel,
            newItem: GroupDomainModel
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    inner class GroupViewHolder(private val binding: ItemGroupCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(group: GroupDomainModel) {
            binding.root.setOnClickListener {
                groupClickListener.invoke(group)
            }
            binding.groupCardAvatar.text = group.name.first().toString()
            binding.textViewGroupCardGroupName.text = group.name
            binding.textViewGroupCardGroupDescription.text = group.description
        }
    }
}


