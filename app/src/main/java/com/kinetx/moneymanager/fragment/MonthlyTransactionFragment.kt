package com.kinetx.moneymanager.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentMonthlyTransactionBinding
import com.kinetx.moneymanager.dataclass.TransactionChildList
import com.kinetx.moneymanager.dataclass.TransactionParentList
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.helpers.CommonOperations
import com.kinetx.moneymanager.recyclerview.TransactionParentRV
import com.kinetx.moneymanager.viewmodel.MonthlyTransactionViewModel
import com.kinetx.moneymanager.viewmodelfactory.MonthlyTransactionViewModelFactory


class MonthlyTransactionFragment : Fragment(), TransactionParentRV.TransactionParentListener {

    lateinit var binding : FragmentMonthlyTransactionBinding
    lateinit var viewModel: MonthlyTransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val viewModelFactory = MonthlyTransactionViewModelFactory(application)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_monthly_transaction,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory)[MonthlyTransactionViewModel::class.java]

        binding.monthlyTransactionViewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = TransactionParentRV(this)
        binding.monthlyTransactionsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.monthlyTransactionsRecyclerView.setHasFixedSize(true)
        binding.monthlyTransactionsRecyclerView.adapter = adapter


        viewModel.listRoomDatabase.observe(viewLifecycleOwner)
        {
            val data = it.groupBy { it.date }.map {
                TransactionParentList(it.key, it.value.map {
                    TransactionChildList(it.transactionId,it.categoryOne,it.categoryTwo,when(it.transactionType)
                    {
                        TransactionType.TRANSFER -> Color.parseColor("#FFa4c639")
                        TransactionType.INCOME -> Color.parseColor("#FF5d8aa8")
                        TransactionType.EXPENSE -> Color.parseColor("#FF970203")
                        else-> Color.parseColor("#FFa4c639")
                    },it.comments,it.amount,
                        CommonOperations.getResourceInt(application,it.categoryImageString),it.categoryColor,it.transactionType)
                }, it.value.filter { it.transactionType==TransactionType.EXPENSE }.map { it.amount }.sum() - it.value.filter { it.transactionType==TransactionType.INCOME }.map { it.amount }.sum() )
            }.sortedByDescending {
                it.date
            }
            adapter.setData(data)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateTransactions()
    }

    override fun onLongClickTransactionParent(position: Long, transactionType: TransactionType) {
        view?.findNavController()?.navigate(MonthlyTransactionFragmentDirections.actionMonthlyTransactionFragmentToAddTransactionFragment(transactionType,position))
    }

}