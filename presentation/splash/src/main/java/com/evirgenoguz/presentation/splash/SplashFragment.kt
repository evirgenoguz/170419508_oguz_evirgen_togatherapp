package com.evirgenoguz.presentation.splash

import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.util.Deeplink
import com.evirgenoguz.presentation.splash.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashFragment() :
    ViewModelFragment<FragmentSplashBinding, SplashViewModel>(FragmentSplashBinding::inflate) {

    override val viewModel: SplashViewModel by viewModels()

    override fun showAppBar(): Boolean = false
    override fun showBottomNavigation() = false

    override fun setupUI() {
        viewModel.getAccessToken()
        viewModel.clearAllCache()
        splashRequests()
        collectState()
    }

    private fun collectState() {
        launchWhen(Lifecycle.State.STARTED) {
            delay(2000)
            viewModel.state.collect {
                if (it.accessToken.isNullOrEmpty().not()) {
                    navigateToHome()
                } else {
                    navigateToIntro()
                }
            }
        }
    }

    private fun splashRequests() {

    }

    private fun navigateToHome() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.splashFragment, true)
            .build()

        val request = NavDeepLinkRequest.Builder
            .fromUri(Deeplink.TO_HOME.link.toUri())
            .build()
        findNavController().navigate(request, navOptions)
    }

    private fun navigateToIntro() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.splashFragment, true)
            .build()

        val request = NavDeepLinkRequest.Builder
            .fromUri(Deeplink.TO_INTRO.link.toUri())
            .build()
        findNavController().navigate(request, navOptions)
    }
}