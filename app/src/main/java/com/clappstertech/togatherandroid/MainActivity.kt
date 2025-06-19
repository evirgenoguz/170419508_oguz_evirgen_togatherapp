package com.clappstertech.togatherandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.clappstertech.togatherandroid.databinding.ActivityMainBinding
import com.clappstertech.togatherandroid.deeplink.DeepLink
import com.clappstertech.togatherandroid.deeplink.DeepLinkArgs
import com.clappstertech.togatherandroid.deeplink.DeepLinkDispatcher
import com.clappstertech.togatherandroid.util.SystemBarWindowInsetListener
import com.evirgenoguz.core.presentation.ext.gone
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.core.presentation.ext.visible
import com.evirgenoguz.core.presentation.util.BottomNavHandler
import com.evirgenoguz.core.presentation.util.ButtonConfig
import com.evirgenoguz.core.presentation.util.SystemBarInsets
import com.evirgenoguz.core.presentation.util.ToolbarHandler
import com.evirgenoguz.domain.model.groupdetail.UpdateLiveLocationDomainModel
import com.evirgenoguz.domain.repository.EventRepository
import com.evirgenoguz.domain.util.SessionManager
import com.evirgenoguz.location.LocationHelper
import com.evirgenoguz.location.LocationHelperBuilder
import com.evirgenoguz.location.LocationPriority
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ToolbarHandler, BottomNavHandler, SystemBarInsets {

    private lateinit var binding: ActivityMainBinding

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navController: NavController

    override val statusBarHeightLiveData: MutableLiveData<Int> = MutableLiveData()

    override var isBottomNavigationVisible: Boolean = false

    override val navigationBarHeightLiveData: MutableLiveData<Int> = MutableLiveData()

    @Inject
    lateinit var deepLinkDispatcher: DeepLinkDispatcher

    @Inject
    lateinit var eventRepository: EventRepository

    private val locationHelper: LocationHelper by lazy {
        LocationHelperBuilder()
            .with(this)
            .setIntervalInMillis(2000L * 10)
            .setPriority(LocationPriority.HIGH_ACCURACY)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavigation()
        setOnBackPressedDispatcher()
        applyWindowInsetListener()
        useDeeplink()
        observeDeeplink()
        collectLocationUpdates()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.d("MainActivity", "destination: $destination")
        }
    }

    private fun collectLocationUpdates() {
        launchWhen(Lifecycle.State.RESUMED) {
            locationHelper.location.collect {
                eventRepository.updateLocation(
                    UpdateLiveLocationDomainModel(
                        latitude = it.latitude,
                        longitude = it.longitude,
                        username = SessionManager.getCurrentUser()?.username.orEmpty()
                    )
                )
            }
        }
        launchWhen(Lifecycle.State.STARTED) {
            SessionManager.isNextEventLessThanTwoHoursAway.collect {
                if (it) {
                    locationHelper.enableLocationUpdates()
                }
            }
        }
    }

    private fun setOnBackPressedDispatcher() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                when (navController.currentDestination?.id) {
                    com.evirgenoguz.presentation.home.R.id.homeFragment -> {
                        finish()
                    }

                    com.evirgenoguz.presentation.foryou.R.id.forYouFragment -> {
                        binding.bottomNavigation.selectedItemId = R.id.nav_graph_home
                    }

                    com.evirgenoguz.presentation.profile.R.id.profileFragment -> {
                        binding.bottomNavigation.selectedItemId = R.id.nav_graph_home
                    }

                    else -> {
                        navController.popBackStack()
                    }
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun setNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph_main)

        appBarConfiguration = AppBarConfiguration.Builder(
            com.evirgenoguz.presentation.home.R.id.nav_graph_home,
            com.evirgenoguz.presentation.foryou.R.id.nav_graph_for_you,
            com.evirgenoguz.presentation.profile.R.id.nav_graph_profile
        ).build()

        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            isBottomNavigationVisible =
                appBarConfiguration.topLevelDestinations.contains(destination.parent?.id)
            binding.bottomNavigation.isVisible = isBottomNavigationVisible
        }

        navController.addOnDestinationChangedListener(listener)

        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun setToolbarTitle(title: String) {
        binding.topAppBar.title = title
    }

    override fun setToolbarButtons(buttons: List<ButtonConfig>) {
        binding.topAppBar.menu?.clear()
        buttons.forEach { config ->
            binding.topAppBar.menu?.add(config.title)?.apply {
                setIcon(config.iconResId)
                setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                setOnMenuItemClickListener {
                    config.onClick()
                    true
                }
            }
        }
    }

    override fun setBackButton(show: Boolean) {
        if (show) {
            binding.topAppBar.navigationIcon = AppCompatResources.getDrawable(
                this,
                com.evirgenoguz.core.presentation.R.drawable.ic_back
            )
            binding.topAppBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        } else {
            binding.topAppBar.navigationIcon = null
            binding.topAppBar.setNavigationOnClickListener(null)
        }

    }

    private fun applyWindowInsetListener() {
        val listener =
            object : SystemBarWindowInsetListener() {
                override fun onSystemBarHeight(
                    statusBarHeight: Int,
                    navigationBarHeight: Int
                ) {
                    if (statusBarHeightLiveData.value != statusBarHeight)
                        statusBarHeightLiveData.value = statusBarHeight
                    if (navigationBarHeightLiveData.value != navigationBarHeight)
                        navigationBarHeightLiveData.value = navigationBarHeight
                }
            }
        window?.decorView
            ?.setOnApplyWindowInsetsListener(listener)
    }

    override fun setTopPaddingToAppBar(height: Int) {
        binding.topAppBar.updatePadding(top = height)
    }

    override fun setBottomPaddingToBottomNavigation(height: Int) {
        binding.bottomNavigation.post {
            binding.bottomNavigation.updatePadding(bottom = height)
        }
    }

    override fun hideToolBar() {
        binding.appBarLayout.gone()
    }

    override fun showToolBar() {
        binding.appBarLayout.visible()
    }

    override fun showBottomNav() {
        binding.bottomNavigation.visible()
    }

    override fun hideBottomNav() {
        binding.bottomNavigation.gone()
    }

    private fun observeDeeplink() {
        launchWhen(Lifecycle.State.RESUMED) {
            deepLinkDispatcher.deeplinkFlow.filterNotNull().collect { deepLink ->
                if (deepLink.isActive.not()) return@collect

                startDeeplink(deepLink)
            }
        }
    }

    private fun startDeeplink(deepLink: DeepLink) {
        executeDeeplink(deepLink)
        deepLink.executed()
    }

    private fun useDeeplink() {
        if (intent.data != null) {
            val url = intent.data.toString()
            val deepLinkArgs = DeepLinkArgs(url)
            deepLinkDispatcher.dispatch(deepLinkArgs)
        }
    }

    private fun executeDeeplink(deepLink: DeepLink) {
        when (deepLink) {
            is DeepLink.ProfileDeepLink -> {
                navController.navigate(R.id.nav_graph_bottom_nav_host)
                binding.bottomNavigation.selectedItemId = R.id.nav_graph_profile
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        useDeeplink()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationHelper.clear()
    }
}