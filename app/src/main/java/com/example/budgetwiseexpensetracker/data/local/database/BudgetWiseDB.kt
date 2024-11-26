package com.example.budgetwiseexpensetracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.budgetwiseexpensetracker.data.local.database.daos.PersonalInfoDao
import com.example.budgetwiseexpensetracker.data.local.database.daos.TransactionDao
import com.example.budgetwiseexpensetracker.data.local.database.entities.PersonalInfoEntity
import com.example.budgetwiseexpensetracker.data.local.database.entities.TransactionEntity

@Database(entities = [TransactionEntity::class,PersonalInfoEntity::class], version = 1, exportSchema = false)
abstract class BudgetWiseDatabase : RoomDatabase() {

    // Define abstract functions to get DAOs
    abstract fun transactionDao(): TransactionDao
    abstract fun personalInfoDao(): PersonalInfoDao

}
