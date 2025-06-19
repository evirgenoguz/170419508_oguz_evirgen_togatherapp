package com.evirgenoguz.core.presentation.util

interface ToolbarHandler {
    fun setToolbarTitle(title: String)
    fun setToolbarButtons(buttons: List<ButtonConfig>)
    fun setBackButton(show: Boolean)
    fun hideToolBar()
    fun showToolBar()
}

data class ButtonConfig(
    val title: String,
    val iconResId: Int,
    val onClick: () -> Unit
)