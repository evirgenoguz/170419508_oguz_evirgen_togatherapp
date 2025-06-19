package com.evirgenoguz.core.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.evirgenoguz.core.domain.util.util.IndicatorPresenter
import com.evirgenoguz.core.presentation.util.SystemBarInsets
import com.evirgenoguz.domain.util.EventLogger
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseFragmentViewPager<VB : ViewBinding, VM : BaseViewModel>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB,
): Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    protected abstract val viewModel: VM

    @Inject
    lateinit var indicatorPresenter: IndicatorPresenter

    @Inject
    lateinit var analytics: EventLogger

    protected open fun showAppBar(): Boolean = true

    protected open fun showBelowStatusBar(): Boolean = true

    protected open fun showAboveNavigationBar(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSystemBars()
        setupUI()
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

    override fun onResume() {
        super.onResume()
        logScreen()
    }

    private fun logScreen() {
        if (::analytics.isInitialized) {
            analytics.logScreen(
                screenName = this::class.simpleName ?: "UnknownScreen",
                screenClass = requireActivity()::class.simpleName
            )
        }
    }

    private fun setSystemBars() {

        val systemBarInsetsController = (activity as? SystemBarInsets) ?: return
        if (showBelowStatusBar()) {
            systemBarInsetsController.statusBarHeightLiveData.observe(viewLifecycleOwner) { statusBarHeight ->
                if (requireView().paddingTop < statusBarHeight) {
                    if (showAppBar()) {
                        systemBarInsetsController.setTopPaddingToAppBar(statusBarHeight)
                    } else {
                        requireView().updatePadding(top = statusBarHeight)
                    }
                }
            }
        }
        if (showAboveNavigationBar()) {
            systemBarInsetsController.navigationBarHeightLiveData.observe(viewLifecycleOwner) { navigationBarHeight ->
                if (requireView().paddingBottom < navigationBarHeight) {
                    if (systemBarInsetsController.isBottomNavigationVisible) {
                        systemBarInsetsController.setBottomPaddingToBottomNavigation(
                            navigationBarHeight
                        )
                    } else {
                        requireView().updatePadding(bottom = navigationBarHeight)
                    }
                }
            }
        }
    }

    abstract fun setupUI()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}