package com.example.budgetwiseexpensetracker.presentation.adapter

import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.databinding.MonthlySpendingItemBinding
import com.example.budgetwiseexpensetracker.databinding.SectionHeaderBinding
import com.example.budgetwiseexpensetracker.utils.getTransactionDate
import java.text.SimpleDateFormat
import java.util.Locale

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
            val formattedText = formatHeaderText(headerText)
            binding.tvSectionHeader.text = formattedText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val binding = SectionHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                HeaderViewHolder(binding)
            }

            TYPE_TRANSACTION -> {
                val binding = MonthlySpendingItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
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
            is HeaderViewHolder -> transactionModels[position].header?.let { holder.onBind(it) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (transactionModels[position].header != null) TYPE_HEADER else TYPE_TRANSACTION
    }

    fun setData(newData: MutableList<TransactionModel>) {
        val categorizedTransactions = mutableListOf<TransactionModel>()

        // Group transactions by day
        val groupedByDate = newData.groupBy {
            SimpleDateFormat("EEEE\ndd/MM/yyyy", Locale.getDefault()).format(getTransactionDate(it).time)
        }

        // Add headers and their corresponding transactions
        for ((date, transactions) in groupedByDate) {
            categorizedTransactions.add(TransactionModel(header = date)) // Add header with date
            categorizedTransactions.addAll(transactions) // Add transactions under this date
        }

        // Update the list and notify
        transactionModels = categorizedTransactions
        notifyDataSetChanged()
    }

    fun formatHeaderText(fullDate: String): SpannableStringBuilder {
        val parts = fullDate.split("\n")
        val dayName = parts[0]  // EEEE
        val date = parts[1]     // dd/MM/yyyy

        val spannableBuilder = SpannableStringBuilder()

        // Append day name with default size
        spannableBuilder.append(dayName)

        // Append date with smaller size
        spannableBuilder.append("\n")
        val start = spannableBuilder.length
        spannableBuilder.append(date)
        val end = spannableBuilder.length

        spannableBuilder.setSpan(RelativeSizeSpan(0.6f), start, end, 0) // Set date to 70% of original size

        return spannableBuilder
    }
}


