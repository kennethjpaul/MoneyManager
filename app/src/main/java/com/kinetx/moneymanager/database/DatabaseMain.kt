package com.kinetx.moneymanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [CategoryDatabase::class,TransactionDatabase::class,BalanceDatabase::class], version = 8, exportSchema = false)
abstract class DatabaseMain : RoomDatabase(){

    abstract val databaseDao : DatabaseDao

    companion object
    {

        private val MIGRATION_7_8 = object :Migration(7,8)
        {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
            CREATE TABLE IF NOT EXISTS `balance_table` (
                `entryId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `account_id` INTEGER NOT NULL,
                `month_end` INTEGER NOT NULL,
                'balance' REAL NOT NULL
            )
        """.trimIndent())
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
                        .addMigrations(MIGRATION_7_8)
                        .createFromAsset("database/main.db")
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}