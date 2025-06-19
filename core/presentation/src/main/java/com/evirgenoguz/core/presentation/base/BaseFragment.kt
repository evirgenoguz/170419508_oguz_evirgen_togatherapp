package com.evirgenoguz.core.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.evirgenoguz.core.presentation.R
import com.evirgenoguz.core.presentation.util.BottomNavHandler
import com.evirgenoguz.core.presentation.util.ButtonConfig
import com.evirgenoguz.core.presentation.util.SystemBarInsets
import com.evirgenoguz.core.presentation.util.ToolbarHandler
import com.evirgenoguz.domain.util.EventLogger
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    @Inject
    lateinit var analytics: EventLogger

    private val toolbarHandler: ToolbarHandler?
        get() = activity as? ToolbarHandler

    private val bottomNavHandler: BottomNavHandler?
        get() = activity as? BottomNavHandler

    protected open fun showBackButton(): Boolean = false

    protected open fun getToolbarButtons(): List<ButtonConfig> = emptyList()

    protected open fun showAppBar(): Boolean = true

    protected open fun showBelowStatusBar(): Boolean = true

    protected open fun showAboveNavigationBar(): Boolean = true

    protected open fun showBottomNavigation(): Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAppBar()
        setBottomNav()
        toolbarHandler?.setBackButton(showBackButton())
        toolbarHandler?.setToolbarButtons(getToolbarButtons())
        setSystemBars()
        setTitle(getString(R.string.app_name))
        setupUI()
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

    protected fun setTitle(title: String) {
        toolbarHandler?.setToolbarTitle(title)
    }

    protected fun setTitle(@StringRes titleResource: Int) {
        val title = getString(titleResource)
        toolbarHandler?.setToolbarTitle(title)
    }

    private fun setAppBar() {
        if (showAppBar()) {
            toolbarHandler?.showToolBar()
        } else {
            toolbarHandler?.hideToolBar()
        }
    }

    private fun setBottomNav() {
        if (showBottomNavigation()) {
            bottomNavHandler?.showBottomNav()
        } else {
            bottomNavHandler?.hideBottomNav()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
