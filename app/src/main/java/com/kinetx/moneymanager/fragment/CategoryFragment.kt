package com.kinetx.moneymanager.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
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
import com.kinetx.moneymanager.databinding.FragmentCategoryBinding
import com.kinetx.moneymanager.enums.ColorIconType
import com.kinetx.moneymanager.helpers.CommonOperations
import com.kinetx.moneymanager.viewmodel.CategoryViewModel
import com.kinetx.moneymanager.viewmodelfactory.CategoryViewModelFactory


class CategoryFragment : Fragment() {

    lateinit var binding : FragmentCategoryBinding
    lateinit var viewModel : CategoryViewModel
    private lateinit var argList : CategoryFragmentArgs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


            argList  = CategoryFragmentArgs.fromBundle(requireArguments())

            val application = requireNotNull(this.activity).application
            val viewModelFactory = CategoryViewModelFactory(argList,application)

            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category,container,false)
            viewModel = ViewModelProvider(this,viewModelFactory).get(CategoryViewModel::class.java)


            binding.categoryViewModel = viewModel
            binding.lifecycleOwner = viewLifecycleOwner


            viewModel.fragmentTitle.observe(viewLifecycleOwner){
                (activity as AppCompatActivity).supportActionBar?.title =it
            }

            viewModel.categoryDatabase.observe(viewLifecycleOwner)
            {
                Log.i("III","Observe function")
                viewModel.updateCategoryEntry(it)
            }

            viewModel.iconImageSource.observe(viewLifecycleOwner){
              //  binding.categoryIconButton.setImageResource(it)
            }

            viewModel.iconImageString.observe(viewLifecycleOwner)
            {
                binding.categoryIconButton.setImageResource(CommonOperations.getResourceInt(application,it))
            }

            viewModel.coloColorCode.observe(viewLifecycleOwner){
                binding.categoryColorButton.setBackgroundColor(it)
            }


            viewModel.accountBalanceQuery.observe(viewLifecycleOwner)
            {

                viewModel.updateInitialBalance(it)

            }

            viewModel.categoryNamesDb.observe(viewLifecycleOwner)
            {
                viewModel.categoryNames = it
            }

            binding.categoryIconButton.setOnClickListener()
            {
                view?.findNavController()?.navigate(
                    CategoryFragmentDirections.actionCategoryFragmentToSelectColorIconFragment(
                        ColorIconType.ICON
                    )
                )
            }

            binding.categoryColorButton.setOnClickListener()
            {
                view?.findNavController()?.navigate(
                    CategoryFragmentDirections.actionCategoryFragmentToSelectColorIconFragment(
                        ColorIconType.COLOR
                    )
                )
            }

            binding.categoryAddButton.setOnClickListener()
            {

               if(viewModel.insertCategory())
               {
                   view?.findNavController()?.navigateUp()
               }
            }

            binding.categoryUpdateButton.setOnClickListener()
            {
                if(viewModel.updateCategory())
                {
                    view?.findNavController()?.navigateUp()
                }
            }

            binding.categoryDeleteButton.setOnClickListener()
            {
                val builder = AlertDialog.Builder(requireContext())
                builder.setPositiveButton("Yes")
                {
                    _,_ ->
                    viewModel.deleteCategory()
                    view?.findNavController()?.navigateUp()
                }
                builder.setNegativeButton("No")
                {
                    _,_ ->
                }
                builder.setTitle("Do you want to delete this category")
                builder.setMessage("Deleting will purge all entries associated with it")
                builder.create().show()


            }

            setFragmentResultListener("SelectColorIcon")
            { _, bundle ->

                val colorIconType = bundle.get("colorIconType")
                val itemBackgroundColor= bundle.getInt("itemBackgroundColor")
                val itemBackgroundImage = bundle.getInt("itemBackgroundImage")
                val itemImageString = bundle.getString("itemImageString")

                when(colorIconType)
                {
                    ColorIconType.ICON-> viewModel.updateIcon(itemBackgroundImage,itemImageString!!)
                    ColorIconType.COLOR -> viewModel.updateColor(itemBackgroundColor)
                }

            }

            return binding.root
    }

}