package com.example.budgetwiseexpensetracker.presentation.ui.expenseList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.domain.usecase.GetAllTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ExpenseListViewModel(
    val getAllTransactionsUseCase: GetAllTransactionsUseCase,
) : ViewModel() {

    private val _showAllTransactions =
        MutableStateFlow<MutableList<TransactionModel>>(mutableListOf())
    val showAllTransactions: MutableStateFlow<MutableList<TransactionModel>> =
        _showAllTransactions

    fun getAllTransactions() {
        viewModelScope.launch {
            getAllTransactionsUseCase.getAllTransactions().collect { transaction ->
                _showAllTransactions.value = transaction.toMutableList()
            }
        }
    }
}
