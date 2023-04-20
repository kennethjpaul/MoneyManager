package com.kinetx.moneymanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentPlotTransactionBinding
import com.kinetx.moneymanager.enums.TransactionType
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

        binding.plotTransactionDateStart.setOnClickListener()
        {
            viewModel.dateStartPick(it)
        }

        binding.plotTransactionDateEnd.setOnClickListener()
        {
            viewModel.dateEndPick(it)
        }

        viewModel.fragmentTitle.observe(viewLifecycleOwner)
        {
            (activity as AppCompatActivity).supportActionBar?.title = it
        }

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



            val transactionType : TransactionType = when(binding.radioGroup.checkedRadioButtonId)
            {
                R.id.plot_category_income ->
                {
                    TransactionType.INCOME
                }
                R.id.plot_category_expense ->
                {
                     TransactionType.EXPENSE
                }
                R.id.plot_category_transfer ->
                {
                    TransactionType.TRANSFER
                }
                else ->
                {
                    TransactionType.EXPENSE
                }
            }

            val(accountId : Long, categoryId : Long) = viewModel.getAccountCategoryIds(transactionType)
            //Toast.makeText(context, "Account id is $accountId and category id is $categoryId", Toast.LENGTH_SHORT).show()

            val dateStart   = viewModel.myCalendarStart.timeInMillis
            val dateEnd     = viewModel.myCalendarEnd.timeInMillis

            view?.findNavController()?.navigate(PlotTransactionFragmentDirections.actionPlotTransactionFragmentToTransactionListFragment(transactionType,accountId,categoryId,dateStart,dateEnd))

        }


        return binding.root
    }

}