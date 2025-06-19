package com.evirgenoguz.presentation.foryou

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evirgenoguz.domain.model.campaign.CampaignDomainModel
import com.evirgenoguz.presentation.foryou.databinding.ItemCampaignCardBinding

class CampaignsAdapter() :
    ListAdapter<CampaignDomainModel, CampaignsAdapter.CampaignViewHolder>(CampaignModelDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CampaignViewHolder {
        return CampaignViewHolder(
            ItemCampaignCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    private class CampaignModelDiffCallback : DiffUtil.ItemCallback<CampaignDomainModel>() {
        override fun areItemsTheSame(
            oldItem: CampaignDomainModel,
            newItem: CampaignDomainModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CampaignDomainModel,
            newItem: CampaignDomainModel
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    inner class CampaignViewHolder(private val binding: ItemCampaignCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(campaign: CampaignDomainModel) {
            binding.textViewCampaignCardCompanyName.text = campaign.campaignCompany.orEmpty()
            binding.textViewCampaignCardDescription.text = campaign.title
            binding.textViewCampaignCardEndDate.text = campaign.endDate.orEmpty()

            binding.textViewCampaignCardDirection.setOnClickListener {
                val lat = campaign.latitude
                val lng = campaign.longitude

                val uri = "geo:$lat,$lng?q=$lat,$lng".toUri()
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps")

                val context = itemView.context
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                }
            }
        }
    }
}