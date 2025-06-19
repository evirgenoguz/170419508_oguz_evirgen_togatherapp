package com.evirgenoguz.core.presentation.permission

interface Permission {

    fun request()

    fun isGranted(): Boolean

}