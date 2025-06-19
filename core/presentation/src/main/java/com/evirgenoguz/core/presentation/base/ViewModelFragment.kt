package com.evirgenoguz.core.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.evirgenoguz.core.domain.util.util.IndicatorPresenter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class ViewModelFragment<VB : ViewBinding, VM : BaseViewModel>(
    bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB,
) : BaseFragment<VB>(bindingInflater) {

    protected abstract val viewModel: VM

    @Inject
    lateinit var indicatorPresenter: IndicatorPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (::indicatorPresenter.isInitialized) {
                    launch {
                        viewModel.isLoading.collectLatest { isLoading ->
                            if (isLoading) {
                                indicatorPresenter.showLoading()
                            } else {
                                indicatorPresenter.hideLoading()
                            }
                        }
                    }
                    launch {
                        viewModel.errorMessage.collectLatest { message ->
                            message?.let {
                                showErrorDialog(it)
                            }
                        }
                    }
                }
            }
        }
    }

    open fun showErrorDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
