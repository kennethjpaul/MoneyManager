package com.kinetx.moneymanager

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.kinetx.moneymanager.databinding.FragmentAddIncomeBinding


class AddIncomeFragment : Fragment() {

    lateinit var binding: FragmentAddIncomeBinding

    private val monthArray = arrayOf(
        "Jan", "Feb",
        "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title = "Add Income"
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_income, container, false)

        setFragmentResultListener("SelectCategory")
        {key, bundle ->
            val varColor = bundle.getString("color")
            val varType = bundle.getString("category")
            val varBg = bundle.getInt("bg")
            val varId = bundle.getLong("id")
            Toast.makeText(activity,"The $varType is updated an ID $varId and with bg : $varBg with $varColor ", Toast.LENGTH_LONG).show()
        }

        binding.addIncomeAddCategoryBtn.setOnClickListener()
        {
            val transactionType: String = "income"
            val actionType: String = "category"
            view?.findNavController()?.navigate(
                AddIncomeFragmentDirections.actionAddIncomeFragmentToSelectCategoryFragment(
                    transactionType,
                    actionType
                )
            )
        }

        binding.addIncomeAddAccountBtn.setOnClickListener()
        {
            val transactionType: String = "income"
            val actionType: String = "account"
            view?.findNavController()?.navigate(
                AddIncomeFragmentDirections.actionAddIncomeFragmentToSelectCategoryFragment(
                    transactionType,
                    actionType
                )
            )
        }

        val myCalendar = Calendar.getInstance()

        "${myCalendar.get(Calendar.DAY_OF_MONTH)} \n ${monthArray[myCalendar.get(Calendar.MONTH)]}\n${
            myCalendar.get(
                Calendar.YEAR
            )
        }".also { binding.addIncomeAddDateBtn.text = it }

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayofMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofMonth)
            updateDate(year, month, dayofMonth)
        }

        binding.addIncomeAddDateBtn.setOnClickListener()
        {
            DatePickerDialog(
                it.context,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        return binding.root
    }

    private fun updateDate(year: Int, month: Int, dayofMonth: Int) {
        Toast.makeText(activity, "date is ${year}-${month}-${dayofMonth}", Toast.LENGTH_SHORT)
            .show()
        "$dayofMonth \n ${monthArray[month]} \n$year".also { binding.addIncomeAddDateBtn.text = it }
    }

}