package com.kinetx.moneymanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentPlotTransactionBinding
import com.kinetx.moneymanager.viewmodel.PlotTransactionViewModel
import com.kinetx.moneymanager.viewmodelfactory.PlotTransactionViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class PlotTransactionFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding : FragmentPlotTransactionBinding
    private lateinit var viewModel: PlotTransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding  = DataBindingUtil.inflate(inflater,
            R.layout.fragment_plot_transaction,container,false)




        val application = requireNotNull(this.activity).application
        val viewModelFactory = PlotTransactionViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(PlotTransactionViewModel::class.java)


        binding.plotTransactionViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        viewModel.accountDbList.observe(viewLifecycleOwner)
        {

        }

        viewModel.expenseDbList.observe(viewLifecycleOwner)
        {

        }

        viewModel.incomeDbList.observe(viewLifecycleOwner)
        {

        }

        binding.plotSubmitButton.setOnClickListener()
        {

            view?.findNavController()?.navigate(PlotTransactionFragmentDirections.actionPlotTransactionFragmentToTransactionListFragment("d"))

            /*
            val accountAll : Boolean = binding.plotAccountSpinner.selectedItemPosition == 0
            val categoryAll : Boolean = binding.plotCategorySpinner.selectedItemPosition ==0



            if (categoryAll and accountAll)
            {

            }
            else if (categoryAll)
            {
                val accountId : Long = viewModel.accountDbList.value?.get(binding.plotAccountSpinner.selectedItemPosition-1)?.categoryId!!
            }
            else if (accountAll)
            {

            }
            else
            {
                val accountId : Long = viewModel.accountDbList.value?.get(binding.plotAccountSpinner.selectedItemPosition-1)?.categoryId!!
            }

            */

        }


        return binding.root
    }

}