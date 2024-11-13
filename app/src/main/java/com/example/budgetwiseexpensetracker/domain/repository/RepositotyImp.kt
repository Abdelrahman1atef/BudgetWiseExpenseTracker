package com.example.budgetwiseexpensetracker.domain.repository

import android.content.Context
import com.example.budgetwiseexpensetracker.data.model.Model
import com.example.myroomdatabase.Database.TransactionDatabase

//class RepositotyImp(context:Context):Repository {
//    private var Dao =TransactionDatabase.getDatabase(context).transactionDao()
//    override suspend fun insertTransaction(transaction: List<Model>): List<Model> =Dao.insertAll(transaction)
//    override suspend fun getTransactions(): List<Model> =Dao.getAllTransactions()
//}