package com.example.budgetwiseexpensetracker.domain.repository

import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import kotlinx.coroutines.flow.Flow


interface Repository {
    suspend fun upsertTransaction(transaction: TransactionModel)
    suspend fun getRecentTransactions(): Flow<MutableList<TransactionModel>>
    suspend fun getTotalBalance(): Flow<Double>
    suspend fun getTotalIncome(): Flow<Double>
    suspend fun getTotalExpense(): Flow<Double>


}