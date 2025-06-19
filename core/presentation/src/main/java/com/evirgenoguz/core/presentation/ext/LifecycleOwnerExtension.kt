package com.evirgenoguz.core.presentation.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

inline fun LifecycleOwner.launchWhen(
    state: Lifecycle.State,
    crossinline action: suspend () -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            action()
        }
    }
}