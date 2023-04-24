package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.database.TransactionDatabase
import com.kinetx.moneymanager.enums.CategoryType
import com.kinetx.moneymanager.enums.TransactionType
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
    val accountBalance = MutableLiveData<String>()

    private val _categoryHint = MutableLiveData<String>()
    val categoryHint : LiveData<String>
        get() = _categoryHint

    private val _accountBalanceVisible = MutableLiveData<Int>()
    val accountBalanceVisible : LiveData<Int>
        get() = _accountBalanceVisible

    private val _initialBalanceTransactionId = MutableLiveData<Long>()
    val initialBalanceTransactionId : LiveData<Long>
        get() = _initialBalanceTransactionId

    private val _accountBalanceQuery = MutableLiveData<TransactionDatabase>()
    val accountBalanceQuery : LiveData<TransactionDatabase>
        get() = _accountBalanceQuery

    private val _addVisible = MutableLiveData<Int>()
    val addVisible : LiveData<Int>
        get() = _addVisible

    private val _editVisible = MutableLiveData<Int>()
    val editVisible : LiveData<Int>
        get() = _editVisible

    private  val _iconImageSource = MutableLiveData<Int>()
    val iconImageSource : LiveData<Int>
        get() = _iconImageSource

    private val _colorColorCode = MutableLiveData<Int>()
    val coloColorCode : LiveData<Int>
        get() = _colorColorCode

    var categoryNames : List<String> = emptyList()
    var categoryNamesDb : LiveData<List<String>>
    var initialCategoryName : String =""

    private val repository : DatabaseRepository


    init {

        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)


        categoryNamesDb = repository.readAllCategoryNames

        var titleString = "Create"
        if (argList.isEdit)
        {
            _addVisible.value = View.GONE
            _editVisible.value = View.VISIBLE



            _categoryId.value = argList.itemId
            categoryName.value = argList.itemName
            _iconImageSource.value = argList.itemIcon
            _colorColorCode.value = argList.itemColor

            initialCategoryName = argList.itemName

            titleString = "Edit"

        }
        else
        {
            _addVisible.value = View.VISIBLE
            _editVisible.value = View.GONE


            initialCategoryName = ""
            _categoryId.value = 1
            _iconImageSource.value = R.drawable.help
            _colorColorCode.value = java.lang.Long.decode("0xFFdc582a").toInt()

        }



        when(argList.categoryType)
        {
            CategoryType.INCOME ->
            {
                _categoryHint.value = "Income name"
                _fragmentTitle.value = "$titleString Income Category"
                _accountBalanceVisible.value = View.GONE
            }
            CategoryType.EXPENSE->
            {
                _categoryHint.value = "Expense name"
                _fragmentTitle.value = "$titleString Expense Category"
                _accountBalanceVisible.value = View.GONE
            }
            CategoryType.ACCOUNT->
            {
                _categoryHint.value = "Account name"
                _categoryHint.value = "Account name"
                _fragmentTitle.value = "$titleString Account"
                _accountBalanceVisible.value = View.VISIBLE

                if (argList.isEdit)
                {
                    viewModelScope.launch(Dispatchers.IO)
                    {
                        _accountBalanceQuery.postValue(repository.getTransactionInitialBalance(argList.itemId))
                    }
                }
                else
                {
                    accountBalance.value = "0"
                    _initialBalanceTransactionId.value = 0
                }

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

        //TODO : Check for trailing spaces and other things in the category name

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

        if (categoryName.value?.trim() in categoryNames)
        {
            Toast.makeText(getApplication(), "An account or category with the same name exists", Toast.LENGTH_SHORT).show()
            return false
        }
        if (accountBalance.value=="")
        {
            Toast.makeText(getApplication(), "Enter an initial balance", Toast.LENGTH_SHORT).show()
            return false
        }

        val category = CategoryDatabase(0,categoryName.value!!, argList.categoryType,_iconImageSource.value!!,_colorColorCode.value!!)
        insertCategoryDao(category)

        val transaction = TransactionDatabase(-1,accountBalance.value!!.toFloat(),TransactionType.BALANCE,argList.itemId,-1L,0,"")
        insertInitialBalanceDao(transaction)
        return true
    }

    private fun insertCategoryDao(category: CategoryDatabase)
    {

        viewModelScope.launch(Dispatchers.IO)
        {
            repository.insertCategory(category)
        }


    }

    private fun insertInitialBalanceDao(transaction: TransactionDatabase)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.insertTransaction(transaction)
        }
    }


    fun updateCategory() : Boolean
    {

        //TODO : Check for trailing spaces and other things in the category name

        if (categoryName.value=="")
        {
            Toast.makeText(getApplication(), "Empty name", Toast.LENGTH_SHORT).show()
            return false
        }

        if (accountBalance.value=="")
        {
            Toast.makeText(getApplication(), "Enter an initial balance", Toast.LENGTH_SHORT).show()
            return false
        }

        if (categoryName.value?.trim()  in categoryNames && categoryName.value != initialCategoryName)
        {
            Toast.makeText(getApplication(), "An account or category with the same name exists", Toast.LENGTH_SHORT).show()
            return false
        }
        val category = CategoryDatabase(_categoryId.value!!,categoryName.value!!, argList.categoryType,_iconImageSource.value!!,_colorColorCode.value!!)
        updateCategoryDao(category)

        val transaction = TransactionDatabase(_initialBalanceTransactionId.value!!,accountBalance.value!!.toFloat(),TransactionType.BALANCE,argList.itemId,-1L,0,"")

        if (_initialBalanceTransactionId.value==0L)
        {
            Log.i("DD","${_initialBalanceTransactionId.value} insert update")
            insertInitialBalanceDao(transaction)
        }
        else
        {
            updateInitialBalanceDao(transaction)
        }

        return true
    }




    private fun updateCategoryDao(category: CategoryDatabase) {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.updateCategory(category)
        }
    }

    private fun updateInitialBalanceDao(transaction: TransactionDatabase)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.updateTransaction(transaction)
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

    fun updateInitialBalance(it: TransactionDatabase?) {
        if (it!=null) {
            accountBalance.value = it.transactionAmount.toString()
            _initialBalanceTransactionId.value = it.transactionId
        }
        else
        {
            _initialBalanceTransactionId.value = 0
            accountBalance.value = "0"
        }
    }


}