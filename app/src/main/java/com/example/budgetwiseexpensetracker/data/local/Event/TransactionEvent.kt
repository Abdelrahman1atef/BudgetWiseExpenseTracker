package com.example.budgetwiseexpensetracker.data.local.Event

import com.example.myroomdatabase.Database.Transaction

sealed interface TransactionEvent {
    object SaveTransaction : TransactionEvent
    data class SetTitle(val title: String) : TransactionEvent
    data class SetSubtitle(val subtitle: String) : TransactionEvent
    data class SetIcon(val icon: Int) : TransactionEvent
    data class SetAmount(val amount: String) : TransactionEvent
    data class SetTime(val time: String) : TransactionEvent
    data class SetColor(val color: Int) : TransactionEvent
    data class SetType(val type: String) : TransactionEvent
data class SortTransactions(val sortType: SortType) : TransactionEvent
    data class DeleteTransaction(val transaction: Transaction) : TransactionEvent

}