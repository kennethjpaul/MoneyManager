package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.kinetx.moneymanager.CategoryFragmentArgs
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.database.DatabaseMain

class CategoryViewModel (argList : CategoryFragmentArgs, application: Application) : AndroidViewModel(application) {

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private val _categoryId = MutableLiveData<Long>()
    val categoryId : LiveData<Long>
        get() = _categoryId

    val categoryName = MutableLiveData<String>()

    private val _categoryHint = MutableLiveData<String>()
    val categoryHint : LiveData<String>
        get() = _categoryHint

    private val _radioEnabled = MutableLiveData<Boolean>()
    val radioEnabled :LiveData<Boolean>
        get() = _radioEnabled

    private val _radioVisible = MutableLiveData<Int>()
    val radioVisible : LiveData<Int>
        get() = _radioVisible

    private val _addVisible = MutableLiveData<Int>()
    val addVisible : LiveData<Int>
        get() = _addVisible

    private val _editVisible = MutableLiveData<Int>()
    val editVisible : LiveData<Int>
        get() = _editVisible

    val expenseSelected =  MutableLiveData<Boolean>()


    val incomeSelected =  MutableLiveData<Boolean>()


    private  val _iconImageSource = MutableLiveData<Int>()
    val iconImageSource : LiveData<Int>
        get() = _iconImageSource

    private val _colorColorCode = MutableLiveData<Int>()
    val coloColorCode : LiveData<Int>
        get() = _colorColorCode


    init {

        if (argList.isEdit)
        {
            _addVisible.value = View.GONE
            _editVisible.value = View.VISIBLE
            _categoryId.value = argList.itemId
            categoryName.value = argList.itemName
            _radioEnabled.value = false
            Log.i("Strange","Initialize layout edit true")
            if (argList.category=="account" || argList.itemType=="transfer")
            {
                _radioVisible.value = View.GONE
                _categoryHint.value = "Account name"
            }
            else
            {
                when(argList.itemType)
                {
                    "income" -> incomeSelected.value = true
                    "expense" -> expenseSelected.value = true
                }
            }

            _iconImageSource.value = argList.itemIcon
            _colorColorCode.value = argList.itemColor
            _fragmentTitle.value = "Edit ${argList.category}"

        }
        else
        {
            _radioEnabled.value = true
            _radioVisible.value = View.VISIBLE
            _addVisible.value = View.VISIBLE
            _editVisible.value = View.GONE
            _iconImageSource.value = R.drawable.help
            _colorColorCode.value = java.lang.Long.decode("0xFFdc582a").toInt()
            _fragmentTitle.value = "Create category"
            _categoryId.value = 1
            _categoryHint.value = "Category name"
            _fragmentTitle.value = "Create ${argList.category}"
            if (argList.category=="account" || argList.itemType=="transfer")
            {
                _radioVisible.value = View.GONE
                _categoryHint.value = "Account name"
            }
        }
        val databaseDao = DatabaseMain.getInstance(application).databaseDao
        Log.i("Strange","Init was called")
    }

    fun updateIcon(itemBackgroundImage: Int) {
        _iconImageSource.value = itemBackgroundImage
    }

    fun updateColor(itemBackgroundColor: Int) {
        _colorColorCode.value = itemBackgroundColor
    }


}