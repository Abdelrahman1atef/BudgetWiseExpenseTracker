package com.example.myroomdatabase.Database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "transaction")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val title: String,
    var subtitle: String? = null,
    var icon: Int = 0,
    var amount: Double? = null,
    var currentTime: String? = null,
    var itemColor: Int = 0,
    var type: String? = null
)




