package com.example.myroomdatabase.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budgetwiseexpensetracker.data.model.Model


@Entity(tableName = "transaction")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val title: String,
    var subtitle: String? = null,
    var icon: Int = 0,
    var amount: String? = null,
    var currentTime: String? = null,
    var itemColor: Int = 0,
    var Type: String? = null
)
//
//fun Transaction.toTransactionDetail(): Model = Model(
//    title = title,
//    subtitle = subtitle,
//    icon = icon,
//    amount = amount,
//    currentTime = currentTime,
//    itemColor = itemColor,
//    type = Type
//
//)



