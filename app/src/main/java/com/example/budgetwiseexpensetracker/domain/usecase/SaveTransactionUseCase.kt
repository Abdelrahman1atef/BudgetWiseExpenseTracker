package com.example.budgetwiseexpensetracker.domain.usecase

import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.domain.repository.Repository

class SaveTransactionUseCase(val repository: Repository)  {
    suspend fun saveTransaction(transaction: TransactionModel) =
        repository.upsertTransaction(transaction)
}