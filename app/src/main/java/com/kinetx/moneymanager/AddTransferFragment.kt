package com.kinetx.moneymanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.kinetx.moneymanager.databinding.FragmentAddTransferBinding


class AddTransferFragment : Fragment() {

    lateinit var binding : FragmentAddTransferBinding
    private lateinit var viewModel : AddTransferViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title = "Add Transfer"
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_transfer, container, false)
        viewModel = ViewModelProvider(this).get(AddTransferViewModel::class.java)

        binding.addTransferViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setFragmentResultListener("SelectCategory")
        {key, bundle ->
            val varBgColor = bundle.getInt("color")
            val varType = bundle.getString("transaction")
            val varImgId = bundle.getInt("imgId")
            val varId = bundle.getLong("id")

            when(varType)
            {
                "source" -> viewModel.updateSource(varId,varImgId,varBgColor)
                "destination" -> viewModel.updateDestination(varId,varImgId,varBgColor)
            }

            Toast.makeText(activity,"The $varType is updated an ID $varId and with bg : $varImgId with $varBgColor ", Toast.LENGTH_LONG).show()
        }

        binding.addTransferSourceButton.setOnClickListener()
        {
            val transactionType: String = "source"
            val actionType: String = "account"
            view?.findNavController()?.navigate(
                AddTransferFragmentDirections.actionAddTransferFragmentToSelectCategoryFragment(
                    transactionType,
                    actionType
                )
            )
        }

        binding.addTransferDestinationButton.setOnClickListener()
        {
            val transactionType: String = "destination"
            val actionType: String = "account"
            view?.findNavController()?.navigate(
                AddTransferFragmentDirections.actionAddTransferFragmentToSelectCategoryFragment(
                    transactionType,
                    actionType
                )
            )
        }

        binding.addTransferAddDateBtn.setOnClickListener()
        {
            viewModel.datePick(it)
        }

        viewModel.sourceAccountImageButton.observe(viewLifecycleOwner)
        {
            data->

                binding.addTransferSourceButton.setBackgroundResource(data.bgColor)
                binding.addTransferSourceButton.setImageResource(data.imageId)
        }

        viewModel.destinationAccountImageButton.observe(viewLifecycleOwner)
        {
                data->

            binding.addTransferDestinationButton.setBackgroundResource(data.bgColor)
            binding.addTransferDestinationButton.setImageResource(data.imageId)
        }


        return binding.root
    }

}