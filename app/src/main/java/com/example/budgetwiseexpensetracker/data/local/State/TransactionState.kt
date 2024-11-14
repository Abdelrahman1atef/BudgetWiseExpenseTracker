package com.example.budgetwiseexpensetracker.data.local.State

import com.example.budgetwiseexpensetracker.data.local.Event.SortType
import com.example.myroomdatabase.Database.Transaction

data class TransactionState (
    val transactions: MutableList<Transaction> = mutableListOf<Transaction>(),
    val title: String = "",
    val subtitle: String = "",
    val icon: Int = 0,
    val amount: String = "",
    val time: String = "",
    val itemColor: Int = 0,
    val type: String = "",
    val sortType: SortType = SortType.NONE

    )