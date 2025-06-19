package com.evirgenoguz.core.presentation.permission

import android.Manifest
import androidx.fragment.app.Fragment

class LocationPermission(
    fragment: Fragment
): MultiPermission(fragment) {

    override val permissions: Array<String> = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

}