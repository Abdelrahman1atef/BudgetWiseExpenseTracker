package com.example.budgetwiseexpensetracker.utils

import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

val calendar = Calendar.getInstance()
object DateUtils {
    fun getCurrentTime(): String =
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)

    fun getCurrentDayMonthYear(): Triple<Int, Int, Int> =
        Triple(
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.YEAR)
        )
     fun getMonth() :String{
        val monthName = arrayOf(
            "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"
        )
        return monthName[calendar.get(Calendar.MONTH)]
    }
}

fun isToday(transaction: TransactionModel): Boolean {
    val currentDate = calendar
    val transactionDate = calendar.apply {
        transaction.transactionDateY?.let { set(Calendar.YEAR, it) }
        transaction.transactionDateM?.minus(1)?.let { set(Calendar.MONTH, it) } // Adjust for zero-based month
        transaction.transactionDateD?.let { set(Calendar.DAY_OF_MONTH, it) }
    }
    return currentDate.get(Calendar.YEAR) == transactionDate.get(Calendar.YEAR) &&
            currentDate.get(Calendar.DAY_OF_YEAR) == transactionDate.get(Calendar.DAY_OF_YEAR)
}

fun isYesterday(transaction: TransactionModel): Boolean {
    val yesterday = calendar.apply { add(Calendar.DAY_OF_YEAR, -1) }
    val transactionDate = calendar.apply {
        transaction.transactionDateY?.let { set(Calendar.YEAR, it) }
        transaction.transactionDateM?.minus(1)?.let { set(Calendar.MONTH, it) } // Adjust for zero-based month
        transaction.transactionDateD?.let { set(Calendar.DAY_OF_MONTH, it) }
    }
    return yesterday.get(Calendar.YEAR) == transactionDate.get(Calendar.YEAR) &&
            yesterday.get(Calendar.DAY_OF_YEAR) == transactionDate.get(Calendar.DAY_OF_YEAR)
}

fun isLastWeek(transaction: TransactionModel): Boolean {
    val lastWeek = calendar.apply { add(Calendar.WEEK_OF_YEAR, -1) }
    val transactionDate = calendar.apply {
        transaction.transactionDateY?.let { set(Calendar.YEAR, it) }
        transaction.transactionDateM?.minus(1)?.let { set(Calendar.MONTH, it) } // Adjust for zero-based month
        transaction.transactionDateD?.let { set(Calendar.DAY_OF_MONTH, it) }
    }
    return lastWeek.get(Calendar.YEAR) == transactionDate.get(Calendar.YEAR) &&
            lastWeek.get(Calendar.WEEK_OF_YEAR) == transactionDate.get(Calendar.WEEK_OF_YEAR)
}

fun isLastThreeWeeks(transaction: TransactionModel): Boolean {
    val threeWeeksAgo = calendar.apply { add(Calendar.WEEK_OF_YEAR, -3) }
    val transactionDate = calendar.apply {
        transaction.transactionDateY?.let { set(Calendar.YEAR, it) }
        transaction.transactionDateM?.minus(1)?.let { set(Calendar.MONTH, it) } // Adjust for zero-based month
        transaction.transactionDateD?.let { set(Calendar.DAY_OF_MONTH, it) }
    }
    return threeWeeksAgo.get(Calendar.YEAR) == transactionDate.get(Calendar.YEAR) &&
            threeWeeksAgo.get(Calendar.WEEK_OF_YEAR) == transactionDate.get(Calendar.WEEK_OF_YEAR)
}

