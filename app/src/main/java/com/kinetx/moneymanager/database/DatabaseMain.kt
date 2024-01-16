package com.kinetx.moneymanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [CategoryDatabase::class,TransactionDatabase::class], version = 6, exportSchema = false)
abstract class DatabaseMain : RoomDatabase(){

    abstract val databaseDao : DatabaseDao

    companion object
    {

        private val MIGRATION_5_6 = object : Migration(5,6)
        {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE category_table ADD COLUMN category_image_string TEXT NOT NULL DEFAULT ''")
            }

        }
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
                        .addMigrations(MIGRATION_5_6)
                        .createFromAsset("database/main.db")
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}