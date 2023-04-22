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
import com.kinetx.moneymanager.databinding.FragmentSummaryBinding
import com.kinetx.moneymanager.viewmodel.SummaryViewModel
import com.kinetx.moneymanager.viewmodelfactory.SummaryViewModelFactory


class SummaryFragment : Fragment() {

    lateinit var binding : FragmentSummaryBinding
    lateinit var viewModel : SummaryViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val application = requireNotNull(this.activity).application
        val viewModelFactory = SummaryViewModelFactory(application)


        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_summary,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SummaryViewModel::class.java)


        binding.summaryViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        binding.summaryAdvanceLeftBtn.setOnClickListener()
        {
            val scope = binding.summaryScopeRadioGroup.checkedRadioButtonId
            viewModel.changeDate(-1,scope)
        }

        binding.summaryAdvanceRightBtn.setOnClickListener()
        {
            val scope = binding.summaryScopeRadioGroup.checkedRadioButtonId
            viewModel.changeDate(1,scope)
        }




        viewModel.fragmentTitle.observe(viewLifecycleOwner){
            (activity as AppCompatActivity).supportActionBar?.title =it
        }



        return binding.root
    }

}