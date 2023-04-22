package com.kinetx.moneymanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentAddTransactionBinding
import com.kinetx.moneymanager.enums.CategoryType
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
            binding.addTransactionCategoryOneBtn.setBackgroundColor(it.categoryColor)
            binding.addTransactionCategoryOneBtn.setImageResource(it.categoryImage)
            binding.transactionCategoryOneSelect.text = it.categoryName
        }

        viewModel.categoryPositionTwo.observe(viewLifecycleOwner)
        {
            binding.addTransactionCategoryTwoBtn.setBackgroundColor(it.categoryColor)
            binding.addTransactionCategoryTwoBtn.setImageResource(it.categoryImage)
            binding.transactionCategoryTwoSelect.text = it.categoryName
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

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.categoryPositionOne.value!!.categoryId!=-1L)
        {
            viewModel.categoryUpdate(viewModel.categoryPositionOne.value!!.categoryId, 1)
        }

        if (viewModel.categoryPositionTwo.value!!.categoryId!=-1L)
        {
            viewModel.categoryUpdate(viewModel.categoryPositionTwo.value!!.categoryId, 2)
        }
    }
}