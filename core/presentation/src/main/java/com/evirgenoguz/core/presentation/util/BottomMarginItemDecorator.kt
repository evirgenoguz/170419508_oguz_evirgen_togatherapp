package com.evirgenoguz.core.presentation.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BottomMarginItemDecorator(
    private val topMargin: Int = 0,
    private val bottomMargin: Int = 0,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        if (position == 0) {
            outRect.top = topMargin
        }

        if (position < itemCount - 1) {
            outRect.bottom = bottomMargin
        }
    }
}