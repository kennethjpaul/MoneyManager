package com.kinetx.moneymanager

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.database.DatabaseDao
import com.kinetx.moneymanager.database.DatabaseMain
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var databaseDao: DatabaseDao
    private lateinit var db : DatabaseMain

    @Before
    fun createDb()
    {
        val context = ApplicationProvider.getApplicationContext<Context>()


        db = Room.inMemoryDatabaseBuilder(context,DatabaseMain::class.java).build()


        databaseDao = db.databaseDao


    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }



    @Test
    @Throws(Exception::class)
    fun insertAndGetCategory()
    {
        val category = CategoryDatabase()
        databaseDao.insertCategory(category)

        val latestCategory  = databaseDao.getLatestCategory()
        assertEquals(latestCategory?.categoryImage,0)

    }
}