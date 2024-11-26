package com.example.budgetwiseexpensetracker.domain.repository

import com.example.budgetwiseexpensetracker.data.local.database.entities.PersonalInfoEntity
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import kotlinx.coroutines.flow.Flow


interface Repository {
    suspend fun upsertTransaction(transaction: TransactionModel)
    suspend fun getRecentTransactions(): Flow<MutableList<TransactionModel>>
    suspend fun getTotalBalance(): Flow<Double>
    suspend fun getTotalIncome(): Flow<Double>
    suspend fun getTotalExpense(): Flow<Double>
    suspend fun getAllTransactions(): Flow<MutableList<TransactionModel>>
    suspend fun getIncomeTransaction(): Flow<MutableList<TransactionModel>>
    suspend fun getExpenseTransaction(): Flow<MutableList<TransactionModel>>

    suspend fun getPersonalInfo(): Flow<PersonalInfoEntity>
    suspend fun insertOrUpdatePersonalInfo(personalInfo: PersonalInfoEntity)
    suspend fun deletePersonalInfo(personalInfo: PersonalInfoEntity)


}