package com.kinetx.moneymanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kinetx.moneymanager.databinding.FragmentSelectCategoryBinding


class SelectCategoryFragment : Fragment(), SelectCategoryAdapter.OnSelectCategoryListener {

    private lateinit var binding : FragmentSelectCategoryBinding
    private lateinit var argList :SelectCategoryFragmentArgs

    override fun onSelectCategoryClick(position: Int) {

        val item = viewModel.itemList.value?.get(position)
        val id : Long = item?.itemId!!
        val itemImage : Int = item.itemImage
        val itemColor : Int = item.itemColor
        val itemTitle : String = item.itemTitle

        setFragmentResult("SelectCategory", bundleOf("transaction" to argList.transactionType, "category" to argList.actionType,"itemId" to id, "itemColor" to itemColor, "itemImage" to itemImage, "itemTitle" to itemTitle))

        view?.findNavController()?.navigateUp()

        //Toast.makeText(context, "An item of $id, image $itemImage, color $itemColor and title $itemTitle was clicked", Toast.LENGTH_SHORT).show()

    }

    override fun onSelectCategoryLongClick(position: Int) {

        val item = viewModel.itemList.value?.get(position)
        val id : Long = item?.itemId!!
        val itemImage : Int = item.itemImage
        val itemColor : Int = item.itemColor
        val itemTitle : String = item.itemTitle
        Toast.makeText(context, "An item of $id, image $itemImage, color $itemColor and title $itemTitle was clicked", Toast.LENGTH_SHORT).show()
    }

    private lateinit var viewModel : SelectCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        argList  = SelectCategoryFragmentArgs.fromBundle(requireArguments())

        var screenTitle: String =""

        when(argList.actionType)
        {
            "category" -> screenTitle = "Select Categories"
            "account" -> screenTitle = "Select Accounts"
        }
        (activity as AppCompatActivity).supportActionBar?.title = screenTitle


        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_select_category, container, false)
        binding.selectCategoryRecyclerview.layoutManager = GridLayoutManager(context,4)
        binding.selectCategoryRecyclerview.setHasFixedSize(true)

        viewModel = ViewModelProvider(this).get(SelectCategoryViewModel::class.java)
        viewModel.updateData(argList)


        val adapter = SelectCategoryAdapter(this)
        binding.selectCategoryRecyclerview.adapter = adapter

        viewModel.itemList.observe(viewLifecycleOwner, Observer
        {
         it?.let{
                adapter.selectCategoryItemArray = it
            }
        })



        binding.selectCategorySelectButton.setOnClickListener()
        {
            val num = R.drawable.android
            val color = R.color.black
            val id : Long =1

            setFragmentResult("SelectCategory", bundleOf("transaction" to argList.transactionType, "category" to argList.actionType,"imgId" to num, "color" to color, "id" to id))

            view?.findNavController()?.navigateUp()
        }
        return binding.root
    }


}