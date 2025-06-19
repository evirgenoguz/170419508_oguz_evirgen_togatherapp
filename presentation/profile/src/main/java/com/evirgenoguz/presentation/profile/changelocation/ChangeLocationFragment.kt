package com.evirgenoguz.presentation.profile.changelocation

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.evirgenoguz.core.presentation.base.ViewModelFragment
import com.evirgenoguz.core.presentation.ext.launchWhen
import com.evirgenoguz.presentation.profile.databinding.FragmentChangeLocationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeLocationFragment :
    ViewModelFragment<FragmentChangeLocationBinding, ChangeLocationViewModel>(
        FragmentChangeLocationBinding::inflate
    ) {
    override val viewModel: ChangeLocationViewModel by viewModels()

    override fun showBackButton(): Boolean = true

    override fun setupUI() {
        viewModel.onAction(ChangeLocationAction.LookupCountries)
        collectEvents()
        initListeners()
    }

    private fun initListeners() = with(binding) {
        buttonEditProfileUpdate.setOnClickListener {
            viewModel.onAction(ChangeLocationAction.ChangeLocation)
        }
        autoCompleteTextViewCountry.doOnTextChanged { country, _, _, _ ->
            viewModel.onAction(ChangeLocationAction.LookupCities(country.toString()))
        }
        autoCompleteTextViewCity.doOnTextChanged { city, _, _, _ ->
            viewModel.onAction(ChangeLocationAction.UpdateCity(city.toString()))
        }
    }

    private fun collectEvents() {
        launchWhen(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                when (event) {
                    ChangeLocationEvent.CitiesSuccess -> addCitiesToDropdown()
                    ChangeLocationEvent.CountriesSuccess -> addCountriesToDropdown()
                    is ChangeLocationEvent.Error -> showErrorDialog(event.message)
                    ChangeLocationEvent.ChangeLocationSuccess -> locationChangeSuccess()
                }
            }
        }
    }

    private fun locationChangeSuccess() {
        Toast.makeText(requireContext(), "Location successfully changed", Toast.LENGTH_LONG).show()
        findNavController().navigateUp()
    }

    private fun addCitiesToDropdown() {
        val items = viewModel.state.value.cityList.map { it.name }.toTypedArray()
        binding.autoCompleteTextViewCity.setSimpleItems(items)
    }

    private fun addCountriesToDropdown() {
        val items = viewModel.state.value.countryList.map { it.name }.toTypedArray()
        binding.autoCompleteTextViewCountry.setSimpleItems(items)
    }

}