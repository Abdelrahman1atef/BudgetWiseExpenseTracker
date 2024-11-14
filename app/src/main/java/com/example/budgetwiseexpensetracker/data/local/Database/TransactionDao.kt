package com.example.myroomdatabase.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Upsert
    suspend fun upsertAll(transactions: Transaction)

    @Delete
    suspend fun deleteTransactions(
        transaction: Transaction
    )

    @Query("SELECT * FROM `transaction`")
     fun getAllTransactions(): Flow<MutableList<Transaction>>

    @Query("SELECT * FROM `transaction` ORDER BY amount DESC")
     fun getTransactionByHighAmount(): Flow<MutableList<Transaction>>

    @Query("SELECT * FROM `transaction` ORDER BY amount ASC")
     fun getTransactionByLowAmount(): Flow<MutableList<Transaction>>

    @Query("SELECT * FROM `transaction` ORDER BY currentTime DESC")
     fun getTransactionByNewestTime(): Flow<MutableList<Transaction>>

    @Query("SELECT * FROM `transaction` ORDER BY currentTime ASC")
     fun getTransactionByOldestTime(): Flow<MutableList<Transaction>>


}
