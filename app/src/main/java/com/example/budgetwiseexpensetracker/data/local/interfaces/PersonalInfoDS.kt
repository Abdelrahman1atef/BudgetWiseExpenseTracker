package com.example.budgetwiseexpensetracker.data.local.interfaces

import com.example.budgetwiseexpensetracker.data.local.database.entities.PersonalInfoEntity
import kotlinx.coroutines.flow.Flow

interface PersonalInfoDS {
    suspend fun getPersonalInfo(): Flow<PersonalInfoEntity>
    suspend fun insertOrUpdatePersonalInfo(personalInfo: PersonalInfoEntity)
    suspend fun deletePersonalInfo(personalInfo: PersonalInfoEntity)
}