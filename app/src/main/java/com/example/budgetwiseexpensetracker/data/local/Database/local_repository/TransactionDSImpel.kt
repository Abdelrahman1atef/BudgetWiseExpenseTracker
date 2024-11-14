package com.example.budgetwiseexpensetracker.data.local.Database.local_repository

import com.example.budgetwiseexpensetracker.data.local.Database.interfaces.TransactionDS
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.myroomdatabase.Database.Transaction
import com.example.myroomdatabase.Database.TransactionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class TransactionDSImpel(private val dao: TransactionDao) : TransactionDS {
    override suspend fun upsertTransaction(transaction: TransactionModel) {
        val entity: Transaction? = transaction.title?.let {
            Transaction(
                title = it,
                subtitle = transaction.subtitle,
                icon = transaction.icon,
                amount = transaction.amount,
                currentTime = transaction.currentTime,
                itemColor = transaction.itemColor,
                type = transaction.type
            )
        }
        if (entity != null) {
            dao.upsertAll(entity)
        }
    }

override suspend fun getRecentTransactions(): Flow<MutableList<TransactionModel>> =
    dao.getAllTransactions().map { transactions ->
        transactions.map { entity ->
            TransactionModel(
                title = entity.title,
                subtitle = entity.subtitle,
                icon = entity.icon,
                amount = entity.amount,
                currentTime = entity.currentTime,
                itemColor = entity.itemColor,
                type = entity.type
            )
        }.toMutableList()
    }.flowOn(Dispatchers.IO)
}