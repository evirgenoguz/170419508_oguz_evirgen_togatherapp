package com.evirgenoguz.presentation.auth.intro

import androidx.navigation.fragment.findNavController
import com.evirgenoguz.core.presentation.base.BaseFragment
import com.evirgenoguz.presentation.auth.R
import com.evirgenoguz.presentation.auth.databinding.FragmentIntroBinding

class IntroFragment :
    BaseFragment<FragmentIntroBinding>(FragmentIntroBinding::inflate) {

    override fun showAppBar() = false

    override fun showBottomNavigation() = false

    override fun setupUI() {
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.buttonIntroLogin.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_loginFragment)
        }

        binding.buttonIntroRegister.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_registerFragment)
        }
    }

}