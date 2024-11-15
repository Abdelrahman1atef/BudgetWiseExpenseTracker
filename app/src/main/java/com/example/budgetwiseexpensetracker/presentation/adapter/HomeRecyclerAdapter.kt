package com.example.budgetwiseexpensetracker.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.databinding.MonthlySpendingItemBinding

class HomeRecyclerAdapter : RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {
//    private var model: ArrayList<Model> = ArrayList()
private var transactionModels: MutableList<TransactionModel> = emptyList<TransactionModel>().toMutableList()

    inner class HomeViewHolder(val binding: MonthlySpendingItemBinding) : ViewHolder(binding.root) {
        fun onBind(transactionModel: TransactionModel) {
            binding.tvTitle.text = transactionModel.title
            binding.tvSubtitle.text = transactionModel.subtitle
            binding.ivIcon.setImageResource(transactionModel.icon)
            binding.tvAmount.text = "- $${transactionModel.amount.toString()}"
            binding.tvTime.text = transactionModel.currentTime
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

