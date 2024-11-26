package com.example.budgetwiseexpensetracker.data.local.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.budgetwiseexpensetracker.data.local.database.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Upsert
    suspend fun upsertAll(transactions: TransactionEntity)

    @Delete
    suspend fun deleteTransactions(
        transactionEntity: TransactionEntity
    )

    @Query("""
        SELECT * FROM `transaction`
        ORDER BY transactionDateY DESC, transactionDateM DESC, transactionDateD DESC, currentTime DESC
    """)
    fun getAllTransactions(): Flow<MutableList<TransactionEntity>>

    @Query(
        """
    SELECT id, title, subtitle, icon, SUM(amount) AS amount, MAX(currentTime) AS currentTime,
            transactionDateD,transactionDateM,transactionDateY,itemColor, type 
    FROM `transaction`
    WHERE type='Expense'
    GROUP BY title
    UNION ALL
    SELECT * FROM `transaction` WHERE title NOT IN (SELECT DISTINCT title FROM `transaction`)
"""
    )
    fun getRecentTransactions(): Flow<MutableList<TransactionEntity>>
    @Query(
        """
    SELECT id, title, subtitle, icon, SUM(amount) AS amount, MAX(currentTime) AS currentTime,
            transactionDateD,transactionDateM,transactionDateY,itemColor, type 
    FROM `transaction`
    where type='Income'
    GROUP BY title""")
    fun getAllIncomeTransactions(): Flow<MutableList<TransactionEntity>>
    @Query(
        """
    SELECT id, title, subtitle, icon, SUM(amount) AS amount, MAX(currentTime) AS currentTime,
            transactionDateD,transactionDateM,transactionDateY,itemColor, type 
    FROM `transaction`
    where type='Expense'
    GROUP BY title""")
    fun getAllExpenseTransactions(): Flow<MutableList<TransactionEntity>>

    @Query("""
        SELECT SUM(
            CASE 
                WHEN type = 'Expense' THEN -amount 
                WHEN type = 'Income' THEN amount 
                ELSE 0 
            END
        ) AS totalAmount
        FROM 'transaction'
    """)
    fun getTotalBalance(): Flow<Double>

    @Query("SELECT SUM(amount) FROM `transaction` WHERE type=='Expense'")
    fun getTotalExpense(): Flow<Double>

    @Query("SELECT SUM(amount) FROM `transaction`WHERE type=='Income'")
    fun getTotalIncome(): Flow<Double>

    @Query("SELECT * FROM `transaction` ORDER BY amount DESC")
    fun getTransactionByHighAmount(): Flow<MutableList<TransactionEntity>>

    @Query("SELECT * FROM `transaction` ORDER BY amount ASC")
    fun getTransactionByLowAmount(): Flow<MutableList<TransactionEntity>>

    @Query("SELECT * FROM `transaction` ORDER BY currentTime DESC")
    fun getTransactionByNewestTime(): Flow<MutableList<TransactionEntity>>

    @Query("SELECT * FROM `transaction` ORDER BY currentTime ASC")
    fun getTransactionByOldestTime(): Flow<MutableList<TransactionEntity>>

//    @Query("""
//        INSERT INTO "transaction"VALUES(15, 'Shopping', '', 2131230978, 100.0, '01:55 AM', 18, 11, 2024, 2131099652, 'Expense');
//
//    """)
//    fun setData():Flow<MutableList<Transaction>>
}




















