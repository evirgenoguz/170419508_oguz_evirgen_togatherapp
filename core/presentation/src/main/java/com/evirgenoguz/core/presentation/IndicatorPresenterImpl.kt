package com.evirgenoguz.core.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.evirgenoguz.core.domain.util.util.IndicatorPresenter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class IndicatorPresenterImpl constructor(
    private val context: Context
): IndicatorPresenter {
    private var loadingDialog: androidx.appcompat.app.AlertDialog? = null

    init {
        initLoadingDialog()
    }

    override fun showLoading() {
        if (loadingDialog == null || isActivityRunning().not()) return

        loadingDialog?.let {
            if (it.isShowing.not()){
                it.show()
            }
        }
    }


    override fun hideLoading() {
        if (loadingDialog == null || isActivityRunning().not()) return

        loadingDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
    }


    private fun isActivityRunning(): Boolean =
        (context as AppCompatActivity).lifecycle.currentState != Lifecycle.State.DESTROYED


    private fun initLoadingDialog() {
        val builder = MaterialAlertDialogBuilder(context, R.style.TransparentDialog)
        builder.setCancelable(false)
        builder.setView(R.layout.dialog_loading)
        loadingDialog = builder.create()
        loadingDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        (context as AppCompatActivity).lifecycle.addObserver(
            DialogDismissLifecycleObserver(
                loadingDialog
            )
        )
    }


    class DialogDismissLifecycleObserver(
        private var dialog: AppCompatDialog?
    ) : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                dialog?.dismiss()
                dialog = null
            }
        }
    }
}