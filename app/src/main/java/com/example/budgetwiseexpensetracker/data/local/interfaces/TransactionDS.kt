package com.example.budgetwiseexpensetracker.data.local.interfaces

import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import kotlinx.coroutines.flow.Flow

interface TransactionDS {
    suspend fun upsertTransaction(transaction: TransactionModel)
    suspend fun getRecentTransactions(): Flow<MutableList<TransactionModel>>
    suspend fun getTotalIncome(): Flow<Double>
    suspend fun getTotalExpense(): Flow<Double>
    suspend fun getTotalBalance(): Flow<Double>
    suspend fun getAllTransactions(): Flow<MutableList<TransactionModel>>
    suspend fun getIncomeTransaction(): Flow<MutableList<TransactionModel>>
    suspend fun getExpenseTransaction(): Flow<MutableList<TransactionModel>>

}