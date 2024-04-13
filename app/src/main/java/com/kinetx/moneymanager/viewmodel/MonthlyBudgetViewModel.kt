package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.graphics.Color
import android.icu.util.Calendar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.dataclass.CategoryBudgetData
import com.kinetx.moneymanager.dataclass.CategoryListData
import com.kinetx.moneymanager.dataclass.CategoryQueryData
import com.kinetx.moneymanager.helpers.CommonOperations
import com.kinetx.moneymanager.helpers.DateManipulation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MonthlyBudgetViewModel(application: Application):AndroidViewModel(application) {


    val a = application

    private val sp = PreferenceManager.getDefaultSharedPreferences(getApplication())
    private val startOfMonth : Int = sp.getString("startDayOfMonth","1")!!.toInt()
    private var weekendEnabled : Boolean = sp.getBoolean("weekendSwitch",false);
    private var weekendShift : Int = sp.getString("weekendPref","0")?.toInt() ?: 0


    var myCalendarEnd: Calendar = Calendar.getInstance()
    var myCalendarStart : Calendar = Calendar.getInstance()

    private val _startDate = MutableLiveData<String>()
    val startDate : LiveData<String>
        get() = _startDate

    private val _endDate = MutableLiveData<String>()
    val endDate : LiveData<String>
        get() = _endDate

    private var _categorySummaryQuery = MutableLiveData<List<CategoryQueryData>>()
    val categorySummaryQuery : LiveData<List<CategoryQueryData>>
        get() = _categorySummaryQuery

    private var _categorySummary = MutableLiveData<List<CategoryBudgetData>>()
    val categorySummary : LiveData<List<CategoryBudgetData>>
        get() = _categorySummary

    private val repository : DatabaseRepository

    init {

        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)

        var myCalendar : Calendar = Calendar.getInstance()
        myCalendar = DateManipulation.resetToMidnight(myCalendar)

        myCalendarStart = DateManipulation.getStartOfMonth(myCalendar,startOfMonth, weekendEnabled,weekendShift)
        myCalendarEnd = myCalendar.clone() as Calendar

        _startDate.value = DateManipulation.getDateArray(myCalendarStart)
        _endDate.value = DateManipulation.getDateArray(myCalendar)

        databaseQuery()
    }

    fun databaseQuery() {
        viewModelScope.launch(Dispatchers.IO)
        {
            _categorySummaryQuery.postValue(repository.getCategorySummary(myCalendarStart.timeInMillis,myCalendarEnd.timeInMillis))
        }
    }

    fun updateRecyclerView(it: List<CategoryQueryData>?) {

        val sortedList = it?.sortedWith(compareByDescending<CategoryQueryData> {it.budget}.thenByDescending { it.amount })

//        it.sortedWith(compareBy<CategoryQueryData> {it.budget}.thenBy { it.amount })

        _categorySummary.value = sortedList?.map {
            var t = 0
            var progressColor = java.lang.Long.decode("0xFF003b49").toInt()

            if (it.budget!=0f) {
                t = ((1 - (it.budget - it.amount) / it.budget)*100).toInt()
            }
            if (t>100)
            {
                progressColor = java.lang.Long.decode("0xFF7b151e").toInt()
            }

            CategoryBudgetData(it.categoryName, it.categoryId, CommonOperations.getResourceInt(a,it.categoryImage),it.categoryColor,it.amount, if (it.budget==0f) "/ \u221E" else "/ ${it.budget}", t,progressColor)}
    }
}