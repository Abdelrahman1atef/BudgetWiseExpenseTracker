package com.example.budgetwiseexpensetracker.data.local.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.budgetwiseexpensetracker.data.local.database.entities.PersonalInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonalInfoDao {
    @Query("SELECT * FROM personal_info WHERE id = 1")
    fun getPersonalInfo(): Flow<PersonalInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePersonalInfo(personalInfo: PersonalInfoEntity)

    @Delete
    suspend fun deletePersonalInfo(personalInfo: PersonalInfoEntity)
}