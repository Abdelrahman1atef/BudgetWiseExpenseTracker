package com.example.budgetwiseexpensetracker.domain.repository

import com.example.budgetwiseexpensetracker.data.local.database.entities.PersonalInfoEntity
import com.example.budgetwiseexpensetracker.data.local.interfaces.PersonalInfoDS
import com.example.budgetwiseexpensetracker.data.local.interfaces.TransactionDS
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import kotlinx.coroutines.flow.Flow


class RepositoryImp(
    private val transactionDS: TransactionDS,
    private val personalInfoDS: PersonalInfoDS
) : Repository {
    override suspend fun upsertTransaction(transaction: TransactionModel) =
        transactionDS.upsertTransaction(transaction)

    override suspend fun getRecentTransactions(): Flow<MutableList<TransactionModel>> =
        transactionDS.getRecentTransactions()

    override suspend fun getTotalBalance(): Flow<Double> =
        transactionDS.getTotalBalance()

    override suspend fun getTotalExpense(): Flow<Double> =
        transactionDS.getTotalExpense()

    override suspend fun getAllTransactions(): Flow<MutableList<TransactionModel>> =
        transactionDS.getAllTransactions()

    override suspend fun getIncomeTransaction(): Flow<MutableList<TransactionModel>> =
        transactionDS.getIncomeTransaction()

    override suspend fun getExpenseTransaction(): Flow<MutableList<TransactionModel>> =
        transactionDS.getExpenseTransaction()

    override suspend fun getTotalIncome(): Flow<Double> =
        transactionDS.getTotalIncome()

    override suspend fun getPersonalInfo(): Flow<PersonalInfoEntity> =
        personalInfoDS.getPersonalInfo()

    override suspend fun insertOrUpdatePersonalInfo(personalInfo: PersonalInfoEntity) =
        personalInfoDS.insertOrUpdatePersonalInfo(personalInfo)

    override suspend fun deletePersonalInfo(personalInfo: PersonalInfoEntity)=
        personalInfoDS.deletePersonalInfo(personalInfo)



}