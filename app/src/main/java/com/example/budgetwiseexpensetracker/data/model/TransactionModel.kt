package com.example.budgetwiseexpensetracker.data.model


data class TransactionModel  (
    var title: String? = null,
    var subtitle: String? = null,
    var icon: Int = 0,
    var amount: Double? = null,
    var currentTime: String? = null,
    var transactionDateD: Int? = null,
    var transactionDateM: Int? = null,
    var transactionDateY: Int? = null,
    var header: String? = null,
    var itemColor: Int = 0,
    var type: String? = null
)