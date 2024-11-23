package com.example.budgetwiseexpensetracker.presentation.ui.monthly_spending_summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.domain.usecase.GetExpenseTransactionUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.GetIncomeTransactionUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.GetRecentTransactionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MonthlySpendingSummaryViewModel(
    private val getIncomeTransactionUseCase: GetIncomeTransactionUseCase,
    private val getExpenseTransactionUseCase: GetExpenseTransactionUseCase,
) : ViewModel() {

    private val _showIncomeTransaction =
        MutableStateFlow<MutableList<TransactionModel>>(mutableListOf())
    val showIncomeTransaction: MutableStateFlow<MutableList<TransactionModel>> =
        _showIncomeTransaction

    fun getIncomeTransaction() {
        viewModelScope.launch {
            getIncomeTransactionUseCase.getIncomeTransaction().collect { transactions ->
                _showIncomeTransaction.value = transactions.toMutableList()
            }
        }
    }
    private val _showExpenseTransaction =
        MutableStateFlow<MutableList<TransactionModel>>(mutableListOf())
    val showExpenseTransaction: MutableStateFlow<MutableList<TransactionModel>> =
        _showExpenseTransaction

    fun getExpenseTransaction() {
        viewModelScope.launch {
            getExpenseTransactionUseCase.getExpenseTransaction().collect { transactions ->
                _showExpenseTransaction.value = transactions.toMutableList()
            }
        }
    }
}