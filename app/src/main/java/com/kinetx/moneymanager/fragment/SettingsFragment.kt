package com.kinetx.moneymanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentSettingsBinding
import com.kinetx.moneymanager.databinding.FragmentSummaryBinding
import com.kinetx.moneymanager.viewmodel.SettingsViewModel
import com.kinetx.moneymanager.viewmodelfactory.SettingsViewModelFactory


class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding
    lateinit var viewModel : SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val application = requireNotNull(this.activity).application
        val viewModelFactory = SettingsViewModelFactory(application)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_settings,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SettingsViewModel::class.java)

        binding.settingsViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.settingsNumberpickerStartday.maxValue = 31
        binding.settingsNumberpickerStartday.minValue = 1

        viewModel.fragmentTitle.observe(viewLifecycleOwner){
            (activity as AppCompatActivity).supportActionBar?.title =it
        }

        return binding.root
    }


}