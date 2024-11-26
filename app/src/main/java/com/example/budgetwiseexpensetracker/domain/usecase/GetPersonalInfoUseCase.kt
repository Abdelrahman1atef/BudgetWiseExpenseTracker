package com.example.budgetwiseexpensetracker.domain.usecase

import com.example.budgetwiseexpensetracker.data.local.database.entities.PersonalInfoEntity
import com.example.budgetwiseexpensetracker.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetPersonalInfoUseCase (private val repository: Repository){
    suspend fun getPersonalInfo(): Flow<PersonalInfoEntity> =
        repository.getPersonalInfo()
}