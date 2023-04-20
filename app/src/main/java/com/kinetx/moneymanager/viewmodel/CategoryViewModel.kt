package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.enums.CategoryType
import com.kinetx.moneymanager.fragment.CategoryFragmentArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel (val argList : CategoryFragmentArgs, application: Application) : AndroidViewModel(application) {



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


    private val repository : DatabaseRepository


    init {

        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)
        _radioEnabled.value = false
        var titleString = "Create"
        if (argList.isEdit)
        {
            _addVisible.value = View.GONE
            _editVisible.value = View.VISIBLE



            _categoryId.value = argList.itemId
            categoryName.value = argList.itemName
            _iconImageSource.value = argList.itemIcon
            _colorColorCode.value = argList.itemColor

            titleString = "Edit"

        }
        else
        {
            _addVisible.value = View.VISIBLE
            _editVisible.value = View.GONE



            _categoryId.value = 1
            _iconImageSource.value = R.drawable.help
            _colorColorCode.value = java.lang.Long.decode("0xFFdc582a").toInt()

        }



        when(argList.categoryType)
        {
            CategoryType.INCOME ->
            {
                _radioVisible.value = View.VISIBLE
                incomeSelected.value = true
                _categoryHint.value = "Income name"
                _fragmentTitle.value = "$titleString Income Category"
            }
            CategoryType.EXPENSE->
            {
                _radioVisible.value = View.VISIBLE
                expenseSelected.value = true
                _categoryHint.value = "Expense name"
                _fragmentTitle.value = "$titleString Expense Category"
            }
            CategoryType.ACCOUNT->
            {
                _radioVisible.value = View.GONE
                _categoryHint.value = "Account name"
                _categoryHint.value = "Account name"
                _fragmentTitle.value = "$titleString Account"
            }
        }

    }

    fun updateIcon(itemBackgroundImage: Int) {
        _iconImageSource.value = itemBackgroundImage
    }

    fun updateColor(itemBackgroundColor: Int) {
        _colorColorCode.value = itemBackgroundColor
    }


    fun insertCategory() : Boolean
    {

        if (categoryName.value=="")
        {
            Toast.makeText(getApplication(), "Empty name", Toast.LENGTH_SHORT).show()
            return false
        }
        if (_iconImageSource.value==R.drawable.help)
        {
            Toast.makeText(getApplication(), "Select an icon", Toast.LENGTH_SHORT).show()
            return false
        }

        val category = CategoryDatabase(0,categoryName.value!!, argList.categoryType,_iconImageSource.value!!,_colorColorCode.value!!)
        insertCategoryDao(category)

        return true
    }

    private fun insertCategoryDao(category: CategoryDatabase)
    {

        viewModelScope.launch(Dispatchers.IO)
        {
            repository.insertCategory(category)
        }


    }


    fun updateCategory()
    {
        val category = CategoryDatabase(_categoryId.value!!,categoryName.value!!, argList.categoryType,_iconImageSource.value!!,_colorColorCode.value!!)
        updateCategoryDao(category)
    }

    private fun updateCategoryDao(category: CategoryDatabase) {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.updateCategory(category)
        }
    }

    fun deleteCategory() {

        val category = CategoryDatabase(_categoryId.value!!,categoryName.value!!, argList.categoryType,_iconImageSource.value!!,_colorColorCode.value!!)
        deleteCategoryDao(category)
    }

    private fun deleteCategoryDao(category: CategoryDatabase) {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteCategory(category)
        }
    }


}