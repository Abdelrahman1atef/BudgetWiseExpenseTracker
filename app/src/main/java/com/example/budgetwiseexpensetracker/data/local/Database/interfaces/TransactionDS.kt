package com.example.budgetwiseexpensetracker.data.local.Database.interfaces

import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import kotlinx.coroutines.flow.Flow

interface TransactionDS {
    suspend fun upsertTransaction(transaction: TransactionModel)
    suspend fun getRecentTransactions(): Flow<MutableList<TransactionModel>>
    suspend fun getTotalIncome(): Flow<Double>
    suspend fun getTotalExpense(): Flow<Double>
    suspend fun getTotalBalance(): Flow<Double>

}