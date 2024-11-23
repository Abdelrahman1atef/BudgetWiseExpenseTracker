package com.example.budgetwiseexpensetracker.utils

import android.util.Log
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
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            Calendar.getInstance().get(Calendar.MONTH) + 1,
            Calendar.getInstance().get(Calendar.YEAR)
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
fun getTransactionDate(transaction: TransactionModel): Calendar {
    return Calendar.getInstance().apply {
        transaction.transactionDateY?.let { set(Calendar.YEAR, it) }
        transaction.transactionDateM?.minus(1)?.let { set(Calendar.MONTH, it) } // Zero-based month
        transaction.transactionDateD?.let { set(Calendar.DAY_OF_MONTH, it) }
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
}


fun isToday(transaction: TransactionModel): Boolean {
    val today = Calendar.getInstance()
    return getTransactionDate(transaction).apply { set(Calendar.HOUR_OF_DAY, 0) }.timeInMillis ==
            today.apply { set(Calendar.HOUR_OF_DAY, 0) }.timeInMillis
}

fun isYesterday(transaction: TransactionModel): Boolean {
    val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
    Log.e("TAG", "isYesterday: ${transaction.transactionDateD}", )
    return getTransactionDate(transaction).apply { set(Calendar.HOUR_OF_DAY, 0) }.timeInMillis ==
            yesterday.apply { set(Calendar.HOUR_OF_DAY, 0) }.timeInMillis
}

fun isThisWeek(transaction: TransactionModel): Boolean {
    val transactionDate = getTransactionDate(transaction)

    // Get the current week of the year
    val currentWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)

    // Get the start of the current week based on the current week of the year
    val startOfThisWeek = Calendar.getInstance().apply {
        set(Calendar.WEEK_OF_YEAR, currentWeek)  // Set to the current week of the year
        set(Calendar.DAY_OF_WEEK, firstDayOfWeek)  // Start of the week (Sunday or Monday)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    // Get the end of the current week (6 days after the start)
    val endOfThisWeek = Calendar.getInstance().apply {
        set(Calendar.WEEK_OF_YEAR, currentWeek)  // Set to the current week of the year
        set(Calendar.DAY_OF_WEEK, firstDayOfWeek)  // Start of the week (Sunday or Monday)
        add(Calendar.DAY_OF_YEAR, 6)  // Add 6 days to get the end of the week
        set(Calendar.HOUR_OF_DAY, 23)  // End of the day (23:59:59.999)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }

    // Check if the transaction is within this week (from the start of the week to the end of the week)
    return transactionDate.timeInMillis in startOfThisWeek.timeInMillis..endOfThisWeek.timeInMillis
}

fun isLastWeek(transaction: TransactionModel): Boolean {
    val transactionDate = getTransactionDate(transaction)
    val startOfLastWeek = Calendar.getInstance().apply {
        add(Calendar.WEEK_OF_YEAR, -1)
        set(Calendar.DAY_OF_WEEK, firstDayOfWeek)  // Start of last week
    }
    val endOfLastWeek = Calendar.getInstance().apply {
        add(Calendar.WEEK_OF_YEAR, -1)
        set(Calendar.DAY_OF_WEEK, firstDayOfWeek)  // End of last week
        add(Calendar.DAY_OF_YEAR, 6)
    }
    return transactionDate.timeInMillis in startOfLastWeek.timeInMillis..endOfLastWeek.timeInMillis
}

fun isRestOfMonth(transaction: TransactionModel): Boolean {
    val transactionDate = getTransactionDate(transaction)
    val today = Calendar.getInstance()
    val startOfMonth = today.apply { set(Calendar.DAY_OF_MONTH, 1) }
    val endOfMonth = today.apply { set(Calendar.DAY_OF_MONTH, today.getActualMaximum(Calendar.DAY_OF_MONTH)) }

    // Ensure the transaction is after yesterday and within the rest of the month
    return transactionDate.after(today) && transactionDate.before(endOfMonth.time)
}


