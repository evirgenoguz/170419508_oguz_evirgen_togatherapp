package com.evirgenoguz.core.presentation.ext

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

val DialogFragment.parentPrimaryNavigationFragment: Fragment
    get(){
        val navHostFragment = requireParentFragment() as? NavHostFragment
            ?: throw IllegalStateException("Parent fragment is not a NavHostFragment")
        val primaryNavigationFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
            ?: throw IllegalStateException("Primary navigation fragment not found")
        return primaryNavigationFragment
    }