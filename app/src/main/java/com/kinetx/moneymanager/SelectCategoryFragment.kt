package com.kinetx.moneymanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kinetx.moneymanager.databinding.FragmentSelectCategoryBinding


class SelectCategoryFragment : Fragment(), SelectCategoryAdapter.OnSelectCategoryListener {

    private lateinit var binding : FragmentSelectCategoryBinding
    private lateinit var argList :SelectCategoryFragmentArgs

    private lateinit var viewModel : SelectCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        argList  = SelectCategoryFragmentArgs.fromBundle(requireArguments())


        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_select_category, container, false)
        viewModel = ViewModelProvider(this).get(SelectCategoryViewModel::class.java)


        viewModel.initializeLayout(argList)



        val adapter = SelectCategoryAdapter(this)
        binding.selectCategoryRecyclerview.layoutManager = GridLayoutManager(context,4)
        binding.selectCategoryRecyclerview.setHasFixedSize(true)
        binding.selectCategoryRecyclerview.adapter = adapter


        viewModel.itemList.observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
        }

        viewModel.fragmentTitle.observe(viewLifecycleOwner)
        {
            (activity as AppCompatActivity).supportActionBar?.title = it
        }


        binding.selectCategorySelectButton.setOnClickListener()
        {
            val isEdit : Boolean = false
            val categoryID : Long = 1
            val categoryName : String = ""
            val categoryType : String = ""
            val iconResource : Int = R.drawable.android
            val colorResource : Int = R.color.teal_700
            val category : String = argList.categoryType

            view?.findNavController()?.navigate(
                SelectCategoryFragmentDirections.actionSelectCategoryFragmentToCategoryFragment(isEdit,categoryID,categoryName,categoryType,iconResource,colorResource,category)
            )
        }

        return binding.root
    }

    override fun onSelectCategoryClick(position: Int) {

        val item = viewModel.itemList.value?.get(position)
        val itemId : Long = item?.itemId!!
        val itemImage : Int = item.itemImage
        val itemColor : Int = item.itemColor
        val itemTitle : String = item.itemTitle

        setFragmentResult("SelectCategory", bundleOf("transaction" to argList.transactionType, "category" to argList.categoryType,"itemId" to itemId, "itemColor" to itemColor, "itemImage" to itemImage, "itemTitle" to itemTitle))

        view?.findNavController()?.navigateUp()

        //Toast.makeText(context, "An item of $id, image $itemImage, color $itemColor and title $itemTitle was clicked", Toast.LENGTH_SHORT).show()

    }

    override fun onSelectCategoryLongClick(position: Int) {

        val item = viewModel.itemList.value?.get(position)

        val isEdit : Boolean = true
        val itemId : Long = item?.itemId!!
        val itemName : String = item.itemTitle
        val itemType : String = argList.transactionType
        val itemIcon : Int = item.itemImage
        val itemColor : Int = item.itemColor
        val category : String = argList.categoryType


        view?.findNavController()?.navigate(
            SelectCategoryFragmentDirections.actionSelectCategoryFragmentToCategoryFragment(isEdit,itemId,itemName,itemType,itemIcon,itemColor,category)
        )
    }

}