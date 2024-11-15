package com.example.budgetwiseexpensetracker.domain.usecase

import com.example.budgetwiseexpensetracker.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetTotalExpenseUseCase (private val repository: Repository){
    suspend fun getTotalExpense(): Flow<Double> =
        repository.getTotalExpense()
}