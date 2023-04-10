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

    private val _radioEnabled = MutableLiveData<Boolean>()
    val radioEnabled :LiveData<Boolean>
        get() = _radioEnabled

    private val _radioVisible = MutableLiveData<Int>()
    val radioVisible : LiveData<Int>
        get() = _radioVisible

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


    private val _submitButtonText = MutableLiveData<String>()
    val submitButtonText : LiveData<String>
        get() = _submitButtonText



    init {
        _categoryName.value = ""
        _radioEnabled.value = true
        _radioVisible.value = View.VISIBLE
        _expenseSelected.value = false
        _incomeSelected.value = false
        _iconImageSource.value = R.drawable.android
        _colorColorCode.value = R.color.teal_700
        _submitButtonText.value = "Add"
        _fragmentTitle.value = "Create category"
        _categoryId.value = 1
    }

    fun initializeLayout(argList: CategoryFragmentArgs) {

        if (argList.isEdit)
        {
            _categoryId.value = argList.categoryID
            _categoryName.value = argList.categoryName
            _radioEnabled.value = false

            if (argList.category=="account")
            {
                _radioVisible.value = View.GONE
            }
            else
            {
                when(argList.categoryType)
                {
                    "income" -> _incomeSelected.value = true
                    "expense" -> _expenseSelected.value = true
                }
            }

            _iconImageSource.value = argList.iconResource
            _colorColorCode.value = argList.colorResource
            _fragmentTitle.value = "Edit ${argList.category}"

        }
        else
        {
            _fragmentTitle.value = "Create ${argList.category}"
        }
    }

    fun updateIcon(itemBackgroundImage: Int) {
        _iconImageSource.value = itemBackgroundImage
    }

    fun updateColor(itemBackgroundColor: Int) {
        _colorColorCode.value = itemBackgroundColor
    }


}