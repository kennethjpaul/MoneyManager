package com.kinetx.moneymanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kinetx.moneymanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout
        val navController = this.findNavController(R.id.myNavHostFragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.mainFragment,R.id.plotTransactionFragment,R.id.balancesFragment, R.id.transactionsFragment, R.id.summaryFragment, R.id.monthlyTransactionFragment, R.id.monthlyBudgetFragment, R.id.expenseAnalysisFragment),drawerLayout)
        setupActionBarWithNavController(navController,appBarConfiguration)
        NavigationUI.setupWithNavController(binding.navDrawer,navController)

        supportActionBar?.title = "Money Manager"
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController,appBarConfiguration)
    }

}