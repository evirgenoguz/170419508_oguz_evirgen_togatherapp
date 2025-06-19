package com.evirgenoguz.core.presentation.ext

import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.showQuestionDialog(
    title: String = "",
    message: String? = "",
    positiveButtonText: String = "",
    negativeButtonText: String = "",
    onPositiveClick: () -> Unit = {},
    onNegativeClick: () -> Unit = {}
) {
    MaterialAlertDialogBuilder(requireContext())
        .setPositiveButton(positiveButtonText) { dialog, which ->
            onPositiveClick.invoke()
        }
        .setNegativeButton(negativeButtonText) { dialog, which ->
            onNegativeClick.invoke()
        }
        .setMessage(message)
        .setTitle(title)
        .show()
}

fun Fragment.showInfoDialog(
    title: String = "",
    message: String? = "",
    positiveButtonText: String = "OK",
    onPositiveClick: () -> Unit = {}
) {
    MaterialAlertDialogBuilder(requireContext())
        .setPositiveButton(positiveButtonText) { dialog, which ->
            onPositiveClick.invoke()
        }
        .setTitle(title)
        .setMessage(message)
}
