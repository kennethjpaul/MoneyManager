package com.kinetx.moneymanager.fragment

import android.app.AlertDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentAddTransactionBinding
import com.kinetx.moneymanager.enums.CategoryType
import com.kinetx.moneymanager.helpers.CommonOperations
import com.kinetx.moneymanager.viewmodel.AddTransactionViewModel
import com.kinetx.moneymanager.viewmodelfactory.AddTransactionViewModelFactory


class AddTransactionFragment : Fragment() {

    private lateinit var binding : FragmentAddTransactionBinding
    private lateinit var viewModel : AddTransactionViewModel
    private  lateinit var argList : AddTransactionFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        argList = AddTransactionFragmentArgs.fromBundle(requireArguments())
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_transaction, container, false)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = AddTransactionViewModelFactory(argList,application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(AddTransactionViewModel::class.java)


        binding.addTransactionViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        viewModel.fragmentTitle.observe(viewLifecycleOwner)
        {
            (activity as AppCompatActivity).supportActionBar?.title = it
        }


        viewModel.categoryPositionOne.observe(viewLifecycleOwner)
        {
            binding.addTransactionCategoryOneBtn.setCardBackgroundColor(it.categoryColor)
            binding.transactionCategoryOneSelectImg.setImageResource(CommonOperations.getResourceInt(application,it.categoryImageString))
            binding.transactionCategoryOneSelect.text = it.categoryName
        }

        viewModel.categoryPositionTwo.observe(viewLifecycleOwner)
        {
            binding.addTransactionCategoryTwoBtn.setCardBackgroundColor(it.categoryColor)
            binding.transactionCategoryTwoSelectImg.setImageResource(CommonOperations.getResourceInt(application,it.categoryImageString))
            binding.transactionCategoryTwoSelect.text = it.categoryName
        }

        viewModel.transaction.observe(viewLifecycleOwner)
        {
            Log.i("Date","Transaction changed")
            viewModel.categoryUpdate(it.transactionCategoryOne,1)
            viewModel.categoryUpdate(it.transactionCategoryTwo,2)
            viewModel.transactionAmount.value = it.transactionAmount.toString()
            viewModel.transactionComment.value = it.transactionComment

            val cal : Calendar = Calendar.getInstance()
            cal.timeInMillis = it.transactionDate

            viewModel.setCalendar(cal)

            it.transactionDate
        }


        binding.addTransactionAddDateBtn.setOnClickListener()
        {
            viewModel.datePick(it)
        }

        binding.addTransactionCategoryOneBtn.setOnClickListener()
        {
            view?.findNavController()?.navigate(
                AddTransactionFragmentDirections.actionAddTransactionFragmentToSelectCategoryFragment(
                    viewModel.categoryPositionOne.value?.categoryType ?: CategoryType.ACCOUNT,
                    1
                )
            )
        }

        binding.addTransactionCategoryTwoBtn.setOnClickListener()
        {
            view?.findNavController()?.navigate(
                AddTransactionFragmentDirections.actionAddTransactionFragmentToSelectCategoryFragment(
                    viewModel.categoryPositionTwo.value?.categoryType ?: CategoryType.ACCOUNT,
                2
                )
            )
        }

        setFragmentResultListener("SelectCategory")
        { _, bundle ->
            val categoryPosition = bundle.getInt("categoryPosition")
            val itemId = bundle.getLong("itemId")
            viewModel.categoryUpdate(itemId,categoryPosition)

        }

        binding.addTransactionSubmitBtn.setOnClickListener()
        {
            if(viewModel.addTransaction())
            {
                view?.findNavController()?.navigateUp()
            }
        }

        binding.addTransactionUpdateBtn.setOnClickListener()
        {
            if (viewModel.updateTransaction())
            {
                view?.findNavController()?.navigateUp()
            }
        }

        binding.addTransactionDeleteBtn.setOnClickListener()
        {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes")
            {
                    _,_ ->
                viewModel.deleteTransaction()
                view?.findNavController()?.navigateUp()
            }
            builder.setNegativeButton("No")
            {
                    _,_ ->
            }
            builder.setTitle("Do you want to delete this category")
            builder.setMessage("This action is permanent")
            builder.create().show()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.categoryPositionOne.value!!.categoryId!=-1L)
        {
            Log.i("Date","${viewModel.categoryPositionOne.value!!.categoryId} for 1")
          //  viewModel.categoryUpdate(viewModel.categoryPositionOne.value!!.categoryId, 1)
        }

        if (viewModel.categoryPositionTwo.value!!.categoryId!=-1L)
        {
            Log.i("Date","${viewModel.categoryPositionTwo.value!!.categoryId} for 2")
          //  viewModel.categoryUpdate(viewModel.categoryPositionTwo.value!!.categoryId, 2)
        }
    }
}