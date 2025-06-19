package com.evirgenoguz.presentation.foryou

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.evirgenoguz.core.presentation.R as CoreRes
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.util.BottomMarginItemDecorator
import com.evirgenoguz.domain.util.SessionManager
import com.evirgenoguz.presentation.foryou.databinding.FragmentForYouBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForYouFragment : ViewModelFragment<FragmentForYouBinding, ForYouViewModel>(
    FragmentForYouBinding::inflate
) {

    override val viewModel: ForYouViewModel by viewModels()

    private lateinit var campaignsAdapter: CampaignsAdapter

    override fun setupUI() {
        setTitle(getString(com.evirgenoguz.presentation.foryou.R.string.campaigns))
        setAdapter()
        initialRequest()
        collectEvents()
    }

    private fun initialRequest() {
        viewModel.onAction(ForYouAction.GetCityIdAndGetCampaigns(SessionManager.getCurrentUser()?.city))
    }

    private fun collectEvents() {
        launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                when (event) {
                    ForYouEvent.CampaignsDataSuccess -> setDataToAdapter()
                    is ForYouEvent.Error -> showErrorDialog(event.message)
                }
            }
        }
    }

    private fun setAdapter() {
        campaignsAdapter = CampaignsAdapter()
        val bottomSpacing = resources.getDimensionPixelSize(CoreRes.dimen.spacing_medium)
        val topSpacing = resources.getDimensionPixelSize(CoreRes.dimen.spacing_medium)
        binding.recyclerViewForYouCampaigns.addItemDecoration(
            BottomMarginItemDecorator(
                topSpacing,
                bottomSpacing
            )
        )
        binding.recyclerViewForYouCampaigns.adapter = campaignsAdapter
    }

    private fun setDataToAdapter() {
        campaignsAdapter.submitList(viewModel.state.value.campaignList)
    }

}