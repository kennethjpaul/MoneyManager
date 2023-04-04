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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kinetx.moneymanager.databinding.FragmentSelectCategoryBinding
import java.util.ArrayList


class SelectCategoryFragment : Fragment() {

    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mCategoryList : ArrayList<SelectCategoryItem>
    private lateinit var imageID : Array<Int>
    private lateinit var titleArray : Array<String>
    private lateinit var binding : FragmentSelectCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val argList  = SelectCategoryFragmentArgs.fromBundle(requireArguments())

        var screenTitle: String =""

        when(argList.actionType)
        {
            "category" -> screenTitle = "Select Categories"
            "account" -> screenTitle = "Select Accounts"
        }

        initializeData(argList)

        (activity as AppCompatActivity).supportActionBar?.title = screenTitle
        //Toast.makeText(activity, "The user came from ${argList.transactionType} for the action ${argList.actionType}", Toast.LENGTH_SHORT).show()
         binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_select_category, container, false)


        binding.selectCategoryRecyclerview.layoutManager = GridLayoutManager(context,4)
        binding.selectCategoryRecyclerview.setHasFixedSize(true)


        mCategoryList = arrayListOf<SelectCategoryItem>()
        getData()

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

    private fun initializeData(argList: SelectCategoryFragmentArgs) {

        imageID = arrayOf(
            R.drawable.android,
            R.drawable.android,
            R.drawable.android,
            R.drawable.android,
            R.drawable.android,
            R.drawable.android
        )


        if (argList.actionType=="category" && argList.transactionType=="expense")
        {
            titleArray = arrayOf(
                "Household",
                "Eating out",
                "Groceries",
                "Health",
                "Sports",
                "Entertainment"
            )
        }

        if (argList.actionType=="category" && argList.transactionType=="income")
        {
            titleArray = arrayOf(
                "Salary",
                "Interest",
                "Borrowed",
                "Profit",
                "Cash",
                "Random"
            )
        }

        if (argList.actionType=="account")
        {
            titleArray = arrayOf(
                "Main Account",
                "Wallet",
                "Credit card 1",
                "Credit card 2",
                "Credit card 3",
                "Secret"
            )
        }



    }

    private fun getData() {
        for (i in imageID.indices)
        {
            val item = SelectCategoryItem(imageID[i],titleArray[i])
            mCategoryList.add(item)
        }

        binding.selectCategoryRecyclerview.adapter = SelectCategoryAdapter(mCategoryList)
    }


}