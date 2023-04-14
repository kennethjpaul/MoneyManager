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
            binding.addTransactionCategoryOneBtn.setBackgroundColor(it.buttonColor)
            binding.addTransactionCategoryOneBtn.setImageResource(it.buttonImage)
            binding.transactionCategoryOneSelect.text = it.buttonTitle
        }

        viewModel.categoryPositionTwo.observe(viewLifecycleOwner)
        {
            binding.addTransactionCategoryTwoBtn.setBackgroundColor(it.buttonColor)
            binding.addTransactionCategoryTwoBtn.setImageResource(it.buttonImage)
            binding.transactionCategoryTwoSelect.text = it.buttonTitle
        }





        binding.addTransactionAddDateBtn.setOnClickListener()
        {
            viewModel.datePick(it)
        }

        binding.addTransactionCategoryOneBtn.setOnClickListener()
        {
            view?.findNavController()?.navigate(
                AddTransactionFragmentDirections.actionAddTransactionFragmentToSelectCategoryFragment(
                    viewModel.categoryPositionOne.value?.buttonType ?: CategoryType.ACCOUNT,
                    1
                )
            )
        }

        binding.addTransactionCategoryTwoBtn.setOnClickListener()
        {
            view?.findNavController()?.navigate(
                AddTransactionFragmentDirections.actionAddTransactionFragmentToSelectCategoryFragment(
                    viewModel.categoryPositionTwo.value?.buttonType ?: CategoryType.ACCOUNT,
                2
                )
            )
        }

        setFragmentResultListener("SelectCategory")
        { _, bundle ->
            val itemColor = bundle.getInt("itemColor")
            val categoryPosition = bundle.getInt("categoryPosition")
            val itemImage = bundle.getInt("itemImage")
            val itemId = bundle.getLong("itemId")
            val itemTitle= bundle.getString("itemTitle")!!

            when(categoryPosition)
            {
                1 -> viewModel.updateCategoryPositionOne(itemId,itemImage,itemColor,itemTitle)
                2 -> viewModel.updateCategoryPositionTwo(itemId,itemImage,itemColor,itemTitle)
            }

        }

        return binding.root
    }

}