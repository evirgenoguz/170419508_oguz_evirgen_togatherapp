package com.clappstertech.togatherandroid.util

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.WindowInsets
import androidx.core.graphics.Insets
import androidx.core.view.WindowInsetsCompat

/**
 * A custom listener that will manage status bar
 * placement for the decor view, of a view.
 *
 */
open class SystemBarWindowInsetListener : View.OnApplyWindowInsetsListener {

    /**
     * The status bar height, holds the maximum value.
     */
    private var statusBarHeight = 0

    /**
     * The navigation bar height, holds the maximum value.
     */
    private var navigationBarHeight = 0


    open fun onSystemBarHeight(statusBarHeight: Int, navigationBarHeight: Int) {

    }

    /**
     * Called to apply the window insets to the view. This method updates the status bar and
     * navigation bar heights based on the insets provided, and triggers a callback if any height
     * changes are detected. It also consumes the insets to adjust the layout appropriately.
     *
     * @param v The view that the insets are being applied to.
     * @param insets The `WindowInsets` object containing the current insets for the view.
     * @return The modified `WindowInsets` object after applying and consuming the insets.
     * *
     */
    override fun onApplyWindowInsets(v: View, insets: WindowInsets): WindowInsets {
        val insetsCompat = WindowInsetsCompat.toWindowInsetsCompat(insets)

        // status bar height changes
        val statusBarHeightLocal = insetsCompat
            .getInsets(WindowInsetsCompat.Type.statusBars())
            .top
        var isChanged = false
        if (statusBarHeight < statusBarHeightLocal) {
            statusBarHeight = statusBarHeightLocal
            isChanged = true
        }
        // navigation bar height changes
        val navigationBarInsets = insetsCompat
            .getInsets(WindowInsetsCompat.Type.navigationBars())

        val navigationBarHeightLocal = navigationBarInsets.bottom
        if (navigationBarHeight < navigationBarHeightLocal) {
            navigationBarHeight = navigationBarHeightLocal
            isChanged = true
        }
        if (isChanged) {
            onSystemBarHeight(statusBarHeight, navigationBarHeight)
        }


        val newStatusBarInsets = Insets.of(Rect())
        val newNavigationBarInsets = Insets.of(
            navigationBarInsets.left,
            navigationBarInsets.top,
            navigationBarInsets.right,
            0
        )
        val consumedInsets: WindowInsets = WindowInsetsCompat.Builder(insetsCompat)
            .setInsets(WindowInsetsCompat.Type.statusBars(), newStatusBarInsets)
            .setInsets(WindowInsetsCompat.Type.navigationBars(), newNavigationBarInsets)
            .build()
            .toWindowInsets() ?: insets
        val isConsumed = consumedInsets.isConsumed
        Log.i(this::class.java.simpleName, "isInsetsConsumed: $isConsumed")
        return consumedInsets
    }
}