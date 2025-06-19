package com.evirgenoguz.core.presentation.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

abstract class SinglePermission private constructor(): Permission {

    abstract val permission: String

    private var onSuccess: (() -> Unit)? = null

    private var onDeny: ((permission: String) -> Unit)? = null

    private var context: Context? = null


    private var permissionLauncher: ActivityResultLauncher<String>? = null

    constructor(fragment: Fragment): this() {
        context = fragment.requireContext()
        permissionLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){ isGranted ->
            if (isGranted) {
                onSuccess?.invoke()
            } else {
                onDeny?.invoke(permission)
            }
        }
    }

    constructor(activity: AppCompatActivity): this() {
        context = activity
        permissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){ isGranted ->
            if (isGranted) {
                onSuccess?.invoke()
            } else {
                onDeny?.invoke(permission)
            }
        }

    }

    override fun isGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context!!,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun request() {
        permissionLauncher?.launch(permission)
    }

    fun onSuccess(listener: () -> Unit): SinglePermission {
        this.onSuccess = listener
        return this
    }

    fun onDeny(listener: (permission: String) -> Unit): SinglePermission {
        this.onDeny = listener
        return this
    }

}