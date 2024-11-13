package com.example.budgetwiseexpensetracker.domain.repository

import com.example.budgetwiseexpensetracker.data.model.Model


interface Repository {
    suspend fun insertTransaction(transaction: List<Model>): List<Model>
    suspend fun getTransactions(): List<Model>

}