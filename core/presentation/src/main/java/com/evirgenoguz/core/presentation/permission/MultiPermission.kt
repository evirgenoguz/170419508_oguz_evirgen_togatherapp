package com.evirgenoguz.core.presentation.permission

import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

abstract class MultiPermission private constructor(): Permission {

    private var context: Context? = null

    private var permissionLauncher: ActivityResultLauncher<Array<String>>? = null

    constructor(fragment: Fragment): this() {
        context = fragment.requireContext()
        permissionLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { results ->
            val deniedPermissions = results.filterValues { !it }.keys.toList()

            if (deniedPermissions.isNotEmpty()) {
                onDeny?.invoke(deniedPermissions)
            } else {
                onSuccess?.invoke()
            }
        }
    }

    constructor(activity: AppCompatActivity): this() {
        context = activity
        permissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { results ->
            val deniedPermissions = results.filterValues { !it }.keys.toList()

            if (deniedPermissions.isNotEmpty()) {
                onDeny?.invoke(deniedPermissions)
            } else {
                onSuccess?.invoke()
            }
        }
    }

    abstract val permissions: Array<String>

    private var onSuccess: (() -> Unit)? = null

    private var onDeny: ((permissions: List<String>) -> Unit)? = null

    override fun isGranted(): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(
                context!!,
                it
            ) == PERMISSION_GRANTED
        }
    }

    override fun request() {
        permissionLauncher?.launch(permissions)
    }

    fun onSuccess(listener: () -> Unit): MultiPermission {
        this.onSuccess = listener
        return this
    }

    fun onDeny(listener: (permissions: List<String>) -> Unit): MultiPermission {
        this.onDeny = listener
        return this
    }
}