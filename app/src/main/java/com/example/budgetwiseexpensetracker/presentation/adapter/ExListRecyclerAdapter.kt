package com.example.budgetwiseexpensetracker.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.databinding.FragmentExpenseListBinding
import com.example.budgetwiseexpensetracker.databinding.MonthlySpendingItemBinding
import com.example.budgetwiseexpensetracker.utils.isLastThreeWeeks
import com.example.budgetwiseexpensetracker.utils.isLastWeek
import com.example.budgetwiseexpensetracker.utils.isToday
import com.example.budgetwiseexpensetracker.utils.isYesterday

class ExListRecyclerAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var transactionModels: MutableList<TransactionModel> =
        emptyList<TransactionModel>().toMutableList()

    private val TYPE_HEADER = 0
    private val TYPE_TRANSACTION = 1

    inner class TransactionViewHolder(val binding: MonthlySpendingItemBinding) :
        ViewHolder(binding.root) {
        fun onBind(transactionModel: TransactionModel) {
            binding.tvTitle.text = transactionModel.title
            binding.tvSubtitle.text = transactionModel.subtitle
            binding.ivIcon.setImageResource(transactionModel.icon)
            binding.tvTime.text = transactionModel.currentTime
            when (transactionModel.type) {
                "Income" -> {
                    binding.tvAmount.setTextColor(binding.root.context.getColor(R.color.sec_green))
                    binding.tvAmount.text = "+ $${transactionModel.amount.toString()}"
                }

                "Expense" -> {
                    binding.tvAmount.setTextColor(binding.root.context.getColor(R.color.main_red))
                    binding.tvAmount.text = "- $${transactionModel.amount.toString()}"
                }
            }

        }
    }

    inner class HeaderViewHolder(val binding: FragmentExpenseListBinding) : ViewHolder(binding.root) {
        fun onBind(headerText: String) {
            binding.tvSectionHeader.text = headerText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val binding =
                    FragmentExpenseListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding)
            }

            TYPE_TRANSACTION -> {
                val binding = MonthlySpendingItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TransactionViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Unknown viewType $viewType")
        }
    }

    override fun getItemCount(): Int {
        return transactionModels.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is TransactionViewHolder -> holder.onBind(transactionModels[position])
            is HeaderViewHolder -> transactionModels[position].header?.let { holder.onBind(it) } // Use any property you want to show in header
        }
    }

    override fun getItemViewType(position: Int): Int {
        // Check if it's a header or a transaction item
        return if (position % 2 == 0) TYPE_HEADER else TYPE_TRANSACTION // Simple logic for this example
    }

    fun setData(newData: MutableList<TransactionModel>) {
        val todayTransactions = mutableListOf<TransactionModel>()
        val yesterdayTransactions = mutableListOf<TransactionModel>()
        val lastWeekTransactions = mutableListOf<TransactionModel>()
        val lastThreeWeeksTransactions = mutableListOf<TransactionModel>()

        Log.e("TestDATA","""
            ToDay: $todayTransactions
            yesterday: $yesterdayTransactions
        """.trimIndent())

        // Sort transactions into categories
        for (transaction in newData) {
            when {
                isToday(transaction) -> todayTransactions.add(transaction)
                isYesterday(transaction) -> yesterdayTransactions.add(transaction)
                isLastWeek(transaction) -> lastWeekTransactions.add(transaction)
                isLastThreeWeeks(transaction) -> lastThreeWeeksTransactions.add(transaction)
            }
        }

        // Now you can combine the categories in a desired order
        val categorizedTransactions = mutableListOf<TransactionModel>()

        // Add headers and transactions
        if (todayTransactions.isNotEmpty()) {
            categorizedTransactions.add(TransactionModel(header = "Today"))
            categorizedTransactions.addAll(todayTransactions)
        }
        if (yesterdayTransactions.isNotEmpty()) {
            categorizedTransactions.add(TransactionModel(header = "Yesterday"))
            categorizedTransactions.addAll(yesterdayTransactions)
        }
        if (lastWeekTransactions.isNotEmpty()) {
            categorizedTransactions.add(TransactionModel(header = "Last Week"))
            categorizedTransactions.addAll(lastWeekTransactions)
        }
        if (lastThreeWeeksTransactions.isNotEmpty()) {
            categorizedTransactions.add(TransactionModel(header = "Last 3 Weeks"))
            categorizedTransactions.addAll(lastThreeWeeksTransactions)
        }

        // Set the filtered data
        transactionModels = categorizedTransactions
        notifyDataSetChanged()
    }

}

