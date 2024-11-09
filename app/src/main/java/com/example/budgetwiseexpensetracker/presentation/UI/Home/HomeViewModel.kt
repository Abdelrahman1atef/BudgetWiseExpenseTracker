package com.example.budgetwiseexpensetracker.presentation.UI.Home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.data.model.Model

class HomeViewModel : ViewModel() {
    var list = mutableListOf<Model>()

    private val _totalSpending = MutableLiveData<String>()
    val totalSpending: LiveData<String> = _totalSpending
    fun setTotalSpending(total: String) {
        _totalSpending.value = total
    }
    private val _totalIncome = MutableLiveData<String>()
    val totalIncome: LiveData<String> = _totalIncome
    fun setTotalIncome(total: String) {
        _totalIncome.value = total
    }

    val _spendingData = MutableLiveData<MutableList<Model>>()
    val spendingData: LiveData<MutableList<Model>> = _spendingData
    fun updateSpendingData(position: String, newAmount: String,newTime:String) {
        _spendingData.value = list?.map {  item ->
            if (item.title == position) item.copy(amount = "- $"+newAmount, currentTime = newTime) else item

        }?.toMutableList()

        Log.e("TAG", "updateSpendingData: ${position+" "+newAmount+" "+newTime}")
        Log.e("TAG", "updateSpendingData: ${_spendingData.value}")


    }
    init {
        // Initialize with default data
          list= mutableListOf(
            Model("Shopping", "Buy some grocery", R.drawable.shopping, "- $0", "~", R.color.Shopping),
            Model("Subscription", "Netflix", R.drawable.subscription, "- $0", "~", R.color.Subscription),
            Model("Food", "Pizza", R.drawable.food, "- $0", "~", R.color.Food),
            Model("Other", "Salary", R.drawable.other, "- $0", "~", R.color.Other)
        )
        Log.e("init", "updateFromINit: ")

    }
}