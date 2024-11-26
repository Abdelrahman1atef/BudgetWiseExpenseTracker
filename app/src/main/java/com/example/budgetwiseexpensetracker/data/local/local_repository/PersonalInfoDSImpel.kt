package com.example.budgetwiseexpensetracker.data.local.local_repository

import com.example.budgetwiseexpensetracker.data.local.database.daos.PersonalInfoDao
import com.example.budgetwiseexpensetracker.data.local.database.entities.PersonalInfoEntity
import com.example.budgetwiseexpensetracker.data.local.interfaces.PersonalInfoDS
import kotlinx.coroutines.flow.Flow

class PersonalInfoDSImpel(private val personalInfoDao: PersonalInfoDao): PersonalInfoDS {
    override suspend fun getPersonalInfo(): Flow<PersonalInfoEntity> =
        personalInfoDao.getPersonalInfo()

    override suspend fun insertOrUpdatePersonalInfo(personalInfo: PersonalInfoEntity) =
        personalInfoDao.insertOrUpdatePersonalInfo(personalInfo)

    override suspend fun deletePersonalInfo(personalInfo: PersonalInfoEntity) {
        TODO("Not yet implemented")
    }
}