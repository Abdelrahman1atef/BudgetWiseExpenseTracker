package com.example.budgetwiseexpensetracker.presentation.UI.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.data.model.Model

class HomeViewModel : ViewModel() {
    private val _shoppingSpendingSummary = MutableLiveData<Model>()
    val shoppingSpendingSummary: LiveData<Model> = _shoppingSpendingSummary

    fun setShoppingSpendingSummary(model: Model) {
        _shoppingSpendingSummary.value = model
    }
    private val _subscriptionSpendingSummary = MutableLiveData<Model>()
    val subscriptionSpendingSummary: LiveData<Model> = _subscriptionSpendingSummary
    fun setSubscriptionSpendingSummary(model: Model) {
        _subscriptionSpendingSummary.value = model
    }
    private val _foodSpendingSummary = MutableLiveData<Model>()
    val foodSpendingSummary: LiveData<Model> = _foodSpendingSummary
    fun setFoodSpendingSummary(model: Model) {
        _foodSpendingSummary.value = model
    }
    private val _otherSpendingSummary = MutableLiveData<Model>()
    val otherSpendingSummary: LiveData<Model> = _otherSpendingSummary
    fun setOtherSpendingSummary(model: Model) {
        _otherSpendingSummary.value = model
    }

    private val _spendingData = MutableLiveData<List<Model>>()
    val spendingData: LiveData<List<Model>> = _spendingData

    init {
        // Initialize with default data
        _spendingData.value = listOf(
            Model("Shopping", "Buy some grocery", R.drawable.shopping, "- $120", "10:00 AM"),
            Model("Subscription", "Netflix", R.drawable.subscription, "- $80", "10:00 AM"),
            Model("Food", "Pizza", R.drawable.food, "- $40", "10:00 AM"),
            Model("Other", "Salary", R.drawable.other, "- $20", "10:00 AM")
        )
    }

    // Function to update a specific item's amount
    fun updateAmount(position: Int, newAmount: String) {
        _spendingData.value = _spendingData.value?.mapIndexed { index, item ->
            if (index == position) item.copy(amount = newAmount) else item
        }
    }

}