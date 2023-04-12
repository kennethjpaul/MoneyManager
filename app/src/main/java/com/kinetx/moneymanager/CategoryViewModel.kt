package com.kinetx.moneymanager

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoryViewModel : ViewModel() {

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private val _categoryId = MutableLiveData<Long>()
    val categoryId : LiveData<Long>
        get() = _categoryId

    private val _categoryName = MutableLiveData<String>()
    val categoryName : LiveData<String>
        get() = _categoryName

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

    private val _expenseSelected =  MutableLiveData<Boolean>()
    val expenseSelected : LiveData<Boolean>
        get() = _expenseSelected

    private val _incomeSelected =  MutableLiveData<Boolean>()
    val incomeSelected : LiveData<Boolean>
        get() = _incomeSelected

    private  val _iconImageSource = MutableLiveData<Int>()
    val iconImageSource : LiveData<Int>
        get() = _iconImageSource

    private val _colorColorCode = MutableLiveData<Int>()
    val coloColorCode : LiveData<Int>
        get() = _colorColorCode



    init {
        _categoryName.value = ""
        _radioEnabled.value = true
        _radioVisible.value = View.VISIBLE
        _addVisible.value = View.VISIBLE
        _editVisible.value = View.GONE
        _expenseSelected.value = false
        _incomeSelected.value = false
        _iconImageSource.value = R.drawable.help
        _colorColorCode.value = java.lang.Long.decode("0xFFdc582a").toInt()
        _fragmentTitle.value = "Create category"
        _categoryId.value = 1
        _categoryHint.value = "Category name"
    }

    fun initializeLayout(argList: CategoryFragmentArgs) {

        if (argList.isEdit)
        {
            _addVisible.value = View.GONE
            _editVisible.value = View.VISIBLE
            _categoryId.value = argList.itemId
            _categoryName.value = argList.itemName
            _radioEnabled.value = false

            if (argList.category=="account" || argList.itemType=="transfer")
            {
                _radioVisible.value = View.GONE
                _categoryHint.value = "Account name"
            }
            else
            {
                when(argList.itemType)
                {
                    "income" -> _incomeSelected.value = true
                    "expense" -> _expenseSelected.value = true
                }
            }

            _iconImageSource.value = argList.itemIcon
            _colorColorCode.value = argList.itemColor
            _fragmentTitle.value = "Edit ${argList.category}"

        }
        else
        {
            _fragmentTitle.value = "Create ${argList.category}"
            if (argList.category=="account" || argList.itemType=="transfer")
            {
                _radioVisible.value = View.GONE
                _categoryHint.value = "Account name"
            }
        }
    }

    fun updateIcon(itemBackgroundImage: Int) {
        _iconImageSource.value = itemBackgroundImage
    }

    fun updateColor(itemBackgroundColor: Int) {
        _colorColorCode.value = itemBackgroundColor
    }


}