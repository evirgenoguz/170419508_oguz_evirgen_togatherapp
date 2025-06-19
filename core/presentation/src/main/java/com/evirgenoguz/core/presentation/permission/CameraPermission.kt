package com.evirgenoguz.core.presentation.permission

import android.Manifest
import androidx.appcompat.app.AppCompatActivity

class CameraPermission(
    activity: AppCompatActivity
): SinglePermission(activity) {

    override val permission: String = Manifest.permission.CAMERA

}