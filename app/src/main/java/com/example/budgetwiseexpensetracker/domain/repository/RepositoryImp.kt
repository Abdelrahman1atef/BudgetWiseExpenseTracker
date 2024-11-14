package com.example.budgetwiseexpensetracker.domain.repository

import com.example.budgetwiseexpensetracker.data.local.Database.interfaces.TransactionDS
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import kotlinx.coroutines.flow.Flow


class RepositoryImp(val transactionDS: TransactionDS):Repository {
    override suspend fun upsertTransaction(transaction: TransactionModel)=
        transactionDS.upsertTransaction(transaction)
    override suspend fun getRecentTransactions(): Flow<MutableList<TransactionModel>> =
        transactionDS.getRecentTransactions()
}