package com.evirgenoguz.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evirgenoguz.presentation.profile.databinding.ItemProfileBinding

class ProfileActionsAdapter(
    private val actions: List<ProfileActionItem>
) : RecyclerView.Adapter<ProfileActionsAdapter.ProfileActionViewHolder>() {


    inner class ProfileActionViewHolder(
        private val binding: ItemProfileBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(action: ProfileActionItem) {
            val title = binding.root.context.getString(action.titleRes)
            binding.textViewProfileAction.text = title
            binding.textViewProfileAction.setOnClickListener {
                action.onClick()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileActionViewHolder {
        return ProfileActionViewHolder(
            ItemProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ProfileActionViewHolder, position: Int) {
        val action = actions[position]
        holder.bind(action)
    }

    override fun getItemCount(): Int = actions.size

}

