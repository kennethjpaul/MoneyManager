package com.kinetx.moneymanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CategoryDatabase::class,TransactionDatabase::class], version = 5, exportSchema = false)
abstract class DatabaseMain : RoomDatabase(){

    abstract val databaseDao : DatabaseDao

    companion object
    {

        @Volatile
        private var INSTANCE : DatabaseMain? = null

        fun getInstance(context: Context): DatabaseMain
        {
            synchronized(this)
            {
                var instance = INSTANCE

                if(instance==null)
                {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseMain::class.java,
                        "main_database"
                    )
                        .fallbackToDestructiveMigration()
                        .createFromAsset("database/main.db")
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}