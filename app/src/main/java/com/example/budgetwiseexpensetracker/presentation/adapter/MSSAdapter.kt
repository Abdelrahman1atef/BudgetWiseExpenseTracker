package com.example.budgetwiseexpensetracker.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.databinding.MonthlySpendingItemBinding

class MSSAdapter : RecyclerView.Adapter<MSSAdapter.HomeViewHolder>() {
private var transactionModels: MutableList<TransactionModel> = emptyList<TransactionModel>().toMutableList()

    inner class HomeViewHolder(val binding: MonthlySpendingItemBinding) : ViewHolder(binding.root) {
        fun onBind(transactionModel: TransactionModel) {
            binding.tvTitle.text = transactionModel.title
            binding.tvSubtitle.text = transactionModel.subtitle
            binding.ivIcon.setImageResource(transactionModel.icon)
            binding.tvTime.text = transactionModel.currentTime
            when(transactionModel.type){
                "Income"-> {
                    binding.tvAmount.setTextColor(binding.root.context.getColor(R.color.sec_green))
                    binding.tvAmount.text = "+ $${transactionModel.amount.toString()}"
                }
                "Expense"-> {
                    binding.tvAmount.setTextColor(binding.root.context.getColor(R.color.main_red))
                    binding.tvAmount.text = "- $${transactionModel.amount.toString()}"
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(MonthlySpendingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return transactionModels.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.onBind(transactionModels[position])
    }
    fun setData(newData: MutableList<TransactionModel>) {
        transactionModels = newData
        notifyDataSetChanged()
    }
}

