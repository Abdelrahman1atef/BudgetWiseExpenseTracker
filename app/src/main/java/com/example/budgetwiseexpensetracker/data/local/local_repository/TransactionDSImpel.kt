package com.example.budgetwiseexpensetracker.data.local.local_repository

import com.example.budgetwiseexpensetracker.data.local.database.daos.TransactionDao
import com.example.budgetwiseexpensetracker.data.local.interfaces.TransactionDS
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.data.local.database.entities.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class TransactionDSImpel(private val dao: TransactionDao) : TransactionDS {
    override suspend fun upsertTransaction(transaction: TransactionModel) {
        val entity: TransactionEntity? = transaction.title?.let {
            TransactionEntity(
                title = it,
                subtitle = transaction.subtitle,
                icon = transaction.icon,
                amount = transaction.amount,
                currentTime = transaction.currentTime,
                transactionDateD = transaction.transactionDateD,
                transactionDateM = transaction.transactionDateM,
                transactionDateY = transaction.transactionDateY,
                itemColor = transaction.itemColor,
                type = transaction.type
            )
        }
        if (entity != null) {
            dao.upsertAll(entity)
        }
    }

override suspend fun getRecentTransactions(): Flow<MutableList<TransactionModel>> =
    dao.getRecentTransactions().map { transactions ->
        transactions.map { entity ->
            TransactionModel(
                title = entity.title,
                subtitle = entity.subtitle,
                icon = entity.icon,
                amount = entity.amount,
                currentTime = entity.currentTime,
                transactionDateD = entity.transactionDateD,
                transactionDateM = entity.transactionDateM,
                transactionDateY = entity.transactionDateY,
                itemColor = entity.itemColor,
                type = entity.type
            )
        }.toMutableList()
    }.flowOn(Dispatchers.IO)
    override suspend fun getAllTransactions(): Flow<MutableList<TransactionModel>> =
        dao.getAllTransactions().map { transactions ->
            transactions.map{entity ->
                TransactionModel(
                    title = entity.title,
                    subtitle = entity.subtitle,
                    icon = entity.icon,
                    amount = entity.amount,
                    currentTime = entity.currentTime,
                    transactionDateD = entity.transactionDateD,
                    transactionDateM = entity.transactionDateM,
                    transactionDateY = entity.transactionDateY,
                    itemColor = entity.itemColor,
                    type = entity.type
                )
            }.toMutableList()
    }.flowOn(Dispatchers.IO)

    override suspend fun getIncomeTransaction(): Flow<MutableList<TransactionModel>> =
        dao.getAllIncomeTransactions().map { transactions ->
            transactions.map{entity ->
                TransactionModel(
                    title = entity.title,
                    subtitle = entity.subtitle,
                    icon = entity.icon,
                    amount = entity.amount,
                    currentTime = entity.currentTime,
                    transactionDateD = entity.transactionDateD,
                    transactionDateM = entity.transactionDateM,
                    transactionDateY = entity.transactionDateY,
                    itemColor = entity.itemColor,
                    type = entity.type
                )
            }.toMutableList()
        }.flowOn(Dispatchers.IO)

    override suspend fun getExpenseTransaction(): Flow<MutableList<TransactionModel>> =
        dao.getAllExpenseTransactions().map { transactions ->
            transactions.map{entity ->
                TransactionModel(
                    title = entity.title,
                    subtitle = entity.subtitle,
                    icon = entity.icon,
                    amount = entity.amount,
                    currentTime = entity.currentTime,
                    transactionDateD = entity.transactionDateD,
                    transactionDateM = entity.transactionDateM,
                    transactionDateY = entity.transactionDateY,
                    itemColor = entity.itemColor,
                    type = entity.type
                )
            }.toMutableList()
        }.flowOn(Dispatchers.IO)


    override suspend fun getTotalIncome(): Flow<Double> =
        dao.getTotalIncome()

    override suspend fun getTotalExpense(): Flow<Double> =
        dao.getTotalExpense()

    override suspend fun getTotalBalance(): Flow<Double> =
        dao.getTotalBalance()


}

























