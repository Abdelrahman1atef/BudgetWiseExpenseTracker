package com.example.budgetwiseexpensetracker.presentation.ui.income

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.domain.usecase.SaveTransactionUseCase
import kotlinx.coroutines.launch

class IncomeViewModel(
    private val saveTransactionUseCase: SaveTransactionUseCase,
) : ViewModel() {

    fun saveTransaction(transaction: TransactionModel) {
        viewModelScope.launch {
            try {
                saveTransactionUseCase.saveTransaction(transaction)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error saving transaction: ${e.message}")
            }
        }
    }
}
