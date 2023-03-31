package com.kinetx.moneymanager

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.kinetx.moneymanager.databinding.FragmentAddExpenseBinding


class AddExpenseFragment : Fragment() {

    private val monthArray = arrayOf(
        "Jan", "Feb",
        "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    lateinit var binding: FragmentAddExpenseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title = "Add Expense"
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_expense, container, false)

        setFragmentResultListener("SelectCategory")
        {key, bundle ->
            val varColor = bundle.getString("color")
            val varType = bundle.getString("category")
            val varBg = bundle.getInt("bg")
            val varId = bundle.getLong("id")
            Toast.makeText(activity,"The $varType is updated an ID $varId and with bg : $varBg with $varColor ", Toast.LENGTH_LONG).show()
        }

        binding.addExpenseAddCategoryBtn.setOnClickListener()
        {
            val transactionType: String = "expense"
            val actionType: String = "category"
            view?.findNavController()?.navigate(
                AddExpenseFragmentDirections.actionAddExpenseFragmentToSelectCategoryFragment(
                    transactionType,
                    actionType
                )
            )
        }

        binding.addExpenseAddAccountBtn.setOnClickListener()
        {
            val transactionType: String = "expense"
            val actionType: String = "account"
            view?.findNavController()?.navigate(
                AddExpenseFragmentDirections.actionAddExpenseFragmentToSelectCategoryFragment(
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
        }".also { binding.addExpenseAddDateBtn.text = it }


        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayofMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofMonth)
            updateDate(year, month, dayofMonth)
        }

        binding.addExpenseAddDateBtn.setOnClickListener()
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
        "$dayofMonth \n ${monthArray[month]} \n$year".also {
            binding.addExpenseAddDateBtn.text = it
        }
    }


}