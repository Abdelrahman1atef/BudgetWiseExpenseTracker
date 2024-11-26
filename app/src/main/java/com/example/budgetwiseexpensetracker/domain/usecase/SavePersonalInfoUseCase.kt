package com.example.budgetwiseexpensetracker.domain.usecase

import com.example.budgetwiseexpensetracker.data.local.database.entities.PersonalInfoEntity
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.domain.repository.Repository

class SavePersonalInfoUseCase(val repository: Repository)  {
    suspend fun savePersonalInfo(personalInfo: PersonalInfoEntity) =
        repository.insertOrUpdatePersonalInfo(personalInfo)
}