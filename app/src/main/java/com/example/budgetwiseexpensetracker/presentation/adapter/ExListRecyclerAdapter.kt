package com.example.budgetwiseexpensetracker.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.databinding.MonthlySpendingItemBinding
import com.example.budgetwiseexpensetracker.databinding.SectionHeaderBinding
import com.example.budgetwiseexpensetracker.utils.isLastWeek
import com.example.budgetwiseexpensetracker.utils.isRestOfMonth
import com.example.budgetwiseexpensetracker.utils.isThisWeek
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

    inner class HeaderViewHolder(val binding: SectionHeaderBinding) : ViewHolder(binding.root) {
        fun onBind(headerText: String) {
            binding.tvSectionHeader.text = headerText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val binding =
                    SectionHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        return if (transactionModels[position].header != null) TYPE_HEADER else TYPE_TRANSACTION
    }

    fun setData(newData: MutableList<TransactionModel>) {
        val categorizedTransactions = mutableListOf<TransactionModel>()

        // Group transactions by category
        val categories = mapOf(
            "Today" to newData.filter { isToday(it) },
            "Yesterday" to newData.filter { isYesterday(it) },
            "This Week" to newData.filter { isThisWeek(it) },
            "Last Week" to newData.filter { isLastWeek(it) },
            "Rest of Month" to newData.filter { isRestOfMonth(it) }
        )

        // Add headers and their corresponding transactions
        for ((header, transactions) in categories) {
            if (transactions.isNotEmpty()) {
                categorizedTransactions.add(TransactionModel(header = header))
                categorizedTransactions.addAll(transactions)
            }
        }

        // Update the list and notify
        transactionModels = categorizedTransactions
        notifyDataSetChanged()

    }

}

