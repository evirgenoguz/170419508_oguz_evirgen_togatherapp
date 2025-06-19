package com.evirgenoguz.core.presentation.ext

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView

fun TextView.setClickableSpan(
    fullText: String,
    clickablePart: String,
    color: Int = Color.BLUE,
    underline: Boolean = false,
    onClick: () -> Unit
) {
    val spannable = SpannableString(fullText)
    val startIndex = fullText.indexOf(clickablePart)
    val endIndex = startIndex + clickablePart.length

    if (startIndex == -1) {
        text = fullText
        return
    }

    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            onClick()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = color
            ds.isUnderlineText = underline
        }
    }

    spannable.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannable.setSpan(StyleSpan(Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    text = spannable
    movementMethod = LinkMovementMethod.getInstance()
    highlightColor = Color.TRANSPARENT
}