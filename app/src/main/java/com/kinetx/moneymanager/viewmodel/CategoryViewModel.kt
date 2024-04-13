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
import com.kinetx.moneymanager.helpers.CommonOperations
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CategoryViewModel (val argList : CategoryFragmentArgs, application: Application) : AndroidViewModel(application) {



    private var transactionCall : Boolean = false

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private val _categoryId = MutableLiveData<Long>()
    val categoryId : LiveData<Long>
        get() = _categoryId

    val categoryName = MutableLiveData<String>()
    val accountBalance = MutableLiveData<String>()
    val categoryBudget = MutableLiveData<String>()




    private val _categoryHint = MutableLiveData<String>()
    val categoryHint : LiveData<String>
        get() = _categoryHint

    private val _accountBalanceVisible = MutableLiveData<Int>()
    val accountBalanceVisible : LiveData<Int>
        get() = _accountBalanceVisible

    private val _categoryBudgetVisible = MutableLiveData<Int>()
    val categoryBudgetVisible : LiveData<Int>
        get() = _categoryBudgetVisible

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

    private  val _iconImageString = MutableLiveData<String>()
    val iconImageString : LiveData<String>
        get() = _iconImageString

    private val _colorColorCode = MutableLiveData<Int>()
    val coloColorCode : LiveData<Int>
        get() = _colorColorCode

    var categoryNames : List<String> = emptyList()
    var categoryNamesDb : LiveData<List<String>>
    var initialCategoryName : String =""

    private var currentCategory = CategoryDatabase()

    private var _categoryDatabase = MutableLiveData<CategoryDatabase>()
    val categoryDatabase : LiveData<CategoryDatabase>
        get() = _categoryDatabase

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

            viewModelScope.launch(Dispatchers.IO)
            {
                _categoryDatabase.postValue(repository.getCategory(argList.itemId))
            }
            currentCategory.categoryId = argList.itemId
            currentCategory.categoryName = argList.itemName
            currentCategory.categoryType = argList.categoryType
            currentCategory.categoryColor = argList.itemColor
            currentCategory.categoryImage = argList.itemIcon
            currentCategory.categoryImageString = argList.itemImageString

            _categoryId.value = argList.itemId
            categoryName.value = argList.itemName
            _iconImageSource.value = argList.itemIcon
            _iconImageString.value = argList.itemImageString
            _colorColorCode.value = argList.itemColor

            initialCategoryName = argList.itemName

            titleString = "Edit"

        }
        else
        {
            _addVisible.value = View.VISIBLE
            _editVisible.value = View.GONE

            currentCategory.categoryId = 0
            currentCategory.categoryName = ""
            currentCategory.categoryType = argList.categoryType
            currentCategory.categoryColor = java.lang.Long.decode("0xFFdc582a").toInt()
            currentCategory.categoryImage = R.drawable.help
            currentCategory.categoryImageString = "help"

            initialCategoryName = ""
            _categoryId.value = 1
            _iconImageSource.value = R.drawable.help
            _iconImageString.value = "help"
            _colorColorCode.value = java.lang.Long.decode("0xFFdc582a").toInt()

        }



        when(argList.categoryType)
        {
            CategoryType.INCOME ->
            {
                _categoryHint.value = "Income name"
                _fragmentTitle.value = "$titleString Income Category"
                _accountBalanceVisible.value = View.GONE
                _categoryBudgetVisible.value = View.GONE
            }
            CategoryType.EXPENSE->
            {
                _categoryHint.value = "Expense name"
                _fragmentTitle.value = "$titleString Expense Category"
                _accountBalanceVisible.value = View.GONE
                _categoryBudgetVisible.value = View.VISIBLE
            }
            CategoryType.ACCOUNT->
            {
                _categoryHint.value = "Account name"
                _fragmentTitle.value = "$titleString Account"
                _accountBalanceVisible.value = View.VISIBLE
                _categoryBudgetVisible.value = View.GONE

                if (argList.isEdit)
                {
                    viewModelScope.launch(Dispatchers.IO)
                    {
                        _accountBalanceQuery.postValue(repository.getTransactionInitialBalance(argList.itemId))
                    }
                }
                else
                {
                    accountBalance.value = ""
                    _initialBalanceTransactionId.value = 0
                }

            }
        }

    }

    fun updateIcon(itemBackgroundImage: Int, itemImageString : String) {
        _iconImageSource.value = itemBackgroundImage
        _iconImageString.value = itemImageString
        currentCategory.categoryImageString = itemImageString
        currentCategory.categoryImage = itemBackgroundImage
    }

    fun updateColor(itemBackgroundColor: Int) {
        _colorColorCode.value = itemBackgroundColor
        currentCategory.categoryColor = itemBackgroundColor
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

        val category = CategoryDatabase(0,categoryName.value!!, argList.categoryType,_iconImageSource.value!!,currentCategory.categoryImageString,_colorColorCode.value!!,CommonOperations.convertToFloat(categoryBudget.value!!))
        insertCategoryDao(category)

        return true
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun insertCategoryDao(category: CategoryDatabase)
    {

        GlobalScope.launch(Dispatchers.IO)
        {
            repository.insertCategory(category)

            if (argList.categoryType==CategoryType.ACCOUNT)
            {
                val insertedCategory = repository.getCategoryByName(category.categoryName)
                val transaction = TransactionDatabase(0,accountBalance.value!!.toFloat(),TransactionType.BALANCE,insertedCategory!!.categoryId,-1L,0,"")
                repository.insertTransaction(transaction)

            }
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
        val category = CategoryDatabase(_categoryId.value!!,categoryName.value!!, argList.categoryType,_iconImageSource.value!!,currentCategory.categoryImageString,_colorColorCode.value!!,CommonOperations.convertToFloat(categoryBudget.value!!))

        updateCategoryDao(category)

        if (argList.categoryType==CategoryType.ACCOUNT)
        {
            val transaction = TransactionDatabase(
                _initialBalanceTransactionId.value!!,
                accountBalance.value!!.toFloat(),
                TransactionType.BALANCE,
                argList.itemId,
                -1L,
                0,
                ""
            )

            updateInitialBalanceDao(transaction)
        }

        return true
    }




    @OptIn(DelicateCoroutinesApi::class)
    private fun updateCategoryDao(category: CategoryDatabase) {
        GlobalScope.launch(Dispatchers.IO)
        {
            repository.updateCategory(category)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun updateInitialBalanceDao(transaction: TransactionDatabase)
    {
        GlobalScope.launch(Dispatchers.IO)
        {
            repository.updateTransaction(transaction)
        }

    }

    fun deleteCategory() {

        val category = CategoryDatabase(_categoryId.value!!,categoryName.value!!, argList.categoryType,_iconImageSource.value!!,currentCategory.categoryImageString,_colorColorCode.value!!,CommonOperations.convertToFloat(categoryBudget.value!!))
        deleteCategoryDao(category)
    }

    private fun deleteCategoryDao(category: CategoryDatabase) {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteCategory(category)
            repository.deleteTransactionsOfCategory(category.categoryId)
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
            accountBalance.value = ""
        }
    }

    fun updateCategoryEntry(it: CategoryDatabase?) {
        if (!transactionCall) {
            if (it != null) {
                _categoryId.value = it.categoryId
                categoryName.value = it.categoryName
                _iconImageSource.value = it.categoryImage
                _iconImageString.value = it.categoryImageString
                _colorColorCode.value = it.categoryColor
                categoryBudget.value = it.categoryBudget.toString()
                transactionCall = true
            }
        }

    }


}