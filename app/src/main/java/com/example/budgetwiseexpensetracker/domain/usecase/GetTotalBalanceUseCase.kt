package com.example.budgetwiseexpensetracker.domain.usecase

import com.example.budgetwiseexpensetracker.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetTotalBalanceUseCase (private val repository: Repository){
    suspend fun getTotalBalance(): Flow<Double> =
        repository.getTotalBalance()
}