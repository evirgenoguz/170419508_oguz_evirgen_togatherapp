package com.evirgenoguz.presentation.sample

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.util.ButtonConfig
import com.evirgenoguz.presentation.sample.databinding.FragmentSampleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleFragment :
    ViewModelFragment<FragmentSampleBinding, SampleViewModel>(FragmentSampleBinding::inflate) {

    override val viewModel: SampleViewModel by viewModels()

    override fun getToolbarButtons(): List<ButtonConfig> = listOf(
        ButtonConfig(
            "title",
            com.evirgenoguz.core.presentation.R.drawable.ic_mail,
            {
                Toast.makeText(requireContext(), "asdad", Toast.LENGTH_LONG).show()
            }
        )
    )

    override fun setupUI() {
        showAppBar()
        viewModel.getSampleData()
        collectEvents()
    }

    private fun collectEvents() {
        viewLifecycleOwner.launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                when (event) {
                    SampleEvent.DataSuccess -> {
                        binding.textView.text = viewModel.state.value.data
                    }

                    is SampleEvent.Error -> {
                        Toast.makeText(requireContext(), event.errorMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}