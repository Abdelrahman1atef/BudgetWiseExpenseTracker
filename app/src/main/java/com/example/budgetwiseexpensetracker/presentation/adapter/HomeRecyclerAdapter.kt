package com.example.budgetwiseexpensetracker.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.budgetwiseexpensetracker.data.model.Model
import com.example.budgetwiseexpensetracker.databinding.MonthlySpendingItemBinding

class HomeRecyclerAdapter : RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {
//    private var model: ArrayList<Model> = ArrayList()
//    init {
//        model.add(Model("Shopping", "Buy some grocery", R.drawable.shopping, "- $120", "10:00 AM"))
//        model.add(Model("Subscription", "Netflix", R.drawable.subscription, "- $80", "10:00 AM"))
//        model.add(Model("Food", "Pizza", R.drawable.food, "- $32", "10:00 AM"))
//        model.add(Model("Other", "Salary", R.drawable.other, "- $20", "10:00 AM"))
//    }
    private var model: List<Model> = emptyList()
    inner class HomeViewHolder(val binding: MonthlySpendingItemBinding) : ViewHolder(binding.root) {
        fun onBind(model: Model) {
            binding.tvTitle.text = model.title
            binding.tvSubtitle.text = model.subtitle
            binding.ivIcon.setImageResource(model.icon)
            binding.tvAmount.text = model.amount
            binding.tvTime.text = model.currentTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(MonthlySpendingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return model.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.onBind(model[position])
    }
    fun setData(newData: List<Model>) {
        model = newData
        notifyDataSetChanged()
    }
}

