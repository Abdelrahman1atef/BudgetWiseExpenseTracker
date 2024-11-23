package com.example.budgetwiseexpensetracker.domain.usecase

import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetIncomeTransactionUseCase(val repository: Repository) {
   suspend fun getIncomeTransaction(): Flow<MutableList<TransactionModel>> =
        repository.getIncomeTransaction()

}
