package com.example.budgetwiseexpensetracker.domain.usecase

import com.example.budgetwiseexpensetracker.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetTotalIncomeUseCase (private val repository: Repository){
    suspend fun getTotalIncome(): Flow<Double> =
        repository.getTotalIncome()
}