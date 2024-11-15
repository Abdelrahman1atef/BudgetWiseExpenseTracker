package com.example.budgetwiseexpensetracker.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.domain.usecase.GetRecentTransactionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
   private val getRecentTransaction: GetRecentTransactionUseCase
)
 : ViewModel() {

    private val _showRecentTransaction = MutableStateFlow<MutableList<TransactionModel>>(mutableListOf())
    val showRecentTransaction: MutableStateFlow<MutableList<TransactionModel>> = _showRecentTransaction

    fun getRecentTransaction(){
        viewModelScope.launch {
            getRecentTransaction.getRecentTransactions().collect { transactions ->
                _showRecentTransaction.value = transactions.toMutableList()
            }
        }
    }



//    var list = mutableListOf<TransactionModel>()
//
//
//    private val _totalIncome: MutableLiveData<Int> = MutableLiveData<Int>()
//    val totalIncome: LiveData<Int> = _totalIncome
//    fun totalIncome() {
//        _totalIncome.value = list.sumOf { item -> item.amount?.replace()?.toInt() ?: 0 }
//    }
//
//    private val _totalExpense: MutableLiveData<Int> = MutableLiveData<Int>()
//    val totalExpense: LiveData<Int> = _totalExpense
//    fun totalSpending() {
//        _totalExpense.value = list.sumOf { item -> item.amount?.replace()?.toInt() ?: 0 }
//    }
//
//    private val _spendingData = MutableLiveData<MutableList<TransactionModel>>()
//    val spendingData: LiveData<MutableList<TransactionModel>> = _spendingData
//    fun updateSpendingData(position: String, newAmount: String, newTime: String) {
//        _spendingData.value = list?.map { item ->
//            if (item.title == position) item.apply {
//                amount = "- $" + (((amount)?.replace())?.plus((newAmount).toFloat())).toString()
//                currentTime = newTime
//            }
//            else item
//
//        }?.toMutableList()
//    }
//
//    init {
//        // Initialize with default data
//        list = mutableListOf(
//            TransactionModel(
//                "Shopping",
//                "Buy some grocery",
//                R.drawable.shopping,
//                "- $0",
//                "~",
//                R.color.Shopping
//            ),
//            TransactionModel(
//                "Subscription",
//                "Netflix",
//                R.drawable.subscription,
//                "- $0",
//                "~",
//                R.color.Subscription
//            ),
//            TransactionModel("Food", "Pizza", R.drawable.food, "- $0", "~", R.color.Food),
//            TransactionModel("Other", "Salary", R.drawable.other, "- $0", "~", R.color.Other)
//        )
//        _totalExpense.value = 0
//        _totalIncome.value = 0
//
//
//        Log.e("init", "updateFromInit: ")
//
//    }
//
//    private fun String.replace(): Float {
//        return this.replace("- $", "").toFloat()
//    }


    /*
//    private val _sortType = MutableStateFlow(SortType.NONE)
//    @OptIn(ExperimentalCoroutinesApi::class)
//    private val _trasaction= _sortType
//        .flatMapLatest {sortType ->
//            when(sortType){
//                SortType.HIGHEST->dao.getTransactionByHighAmount()
//                SortType.LOWEST->dao.getTransactionByLowAmount()
//                SortType.NEWEST->dao.getTransactionByNewestTime()
//                SortType.OLDEST->dao.getTransactionByOldestTime()
//                SortType.NONE->dao.getAllTransactions()
//            }
//        }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
//    private val _state = MutableStateFlow(TransactionState())
//    val state = combine(_state, _sortType, _trasaction) { state, sortType, transactions ->
//        state.copy(
//            transactions=transactions.toMutableList(),
//            sortType=sortType,
//
//        )
//
//
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TransactionState())
//    fun onEvent(event: TransactionEvent) {
//        when (event) {
//            is TransactionEvent.DeleteTransaction -> {
//                viewModelScope.launch {
//                    dao.deleteTransactions(event.transaction)
//                }
//            }
//
//            TransactionEvent.SaveTransaction -> {
//                val title = state.value.title
//                val subtitle = state.value.subtitle
//                val icon = state.value.icon
//                val amount = state.value.amount
//                val time = state.value.time
//                val type = state.value.type
//                val itemColor = state.value.itemColor
//                if (title.isBlank()||subtitle.isBlank()||amount.isBlank()||time.isBlank()||type.isBlank()){
//                    return
//                }
//                val transaction=Transaction(
//                     title = title,
//                    subtitle = subtitle,
//                    icon = icon,
//                    Type = type,
//                    currentTime = time,
//                    amount = amount,
//                    itemColor = itemColor,
//                )
//                viewModelScope.launch {
//                    dao.upsertAll(transaction)
//                }
//
//            }
//
//            is TransactionEvent.SetAmount -> {
//                _state.update {
//                    it.copy(
//                        amount = event.amount
//                    )
//                }
//            }
//
//            is TransactionEvent.SetColor -> {
//                _state.update {
//                    it.copy(
//                        itemColor = event.color
//                    )
//                }
//            }
//
//            is TransactionEvent.SetIcon -> {
//                _state.update {
//                    it.copy(
//                        icon = event.icon
//                    )
//                }
//            }
//
//            is TransactionEvent.SetSubtitle -> {
//                _state.update {
//                    it.copy(
//                        subtitle = event.subtitle
//                    )
//                }
//            }
//
//            is TransactionEvent.SetTime -> {
//                _state.update {
//                    it.copy(
//                        time = event.time
//                    )
//                }
//            }
//
//            is TransactionEvent.SetTitle -> {
//                _state.update {
//                    it.copy(
//                        title = event.title
//                    )
//                }
//            }
//
//            is TransactionEvent.SetType -> {
//                _state.update {
//                    it.copy(
//                        type = event.type
//                    )
//                }
//            }
//
//            is TransactionEvent.SortTransactions -> {
//                _sortType.value = event.sortType
//                _state.update {
//                    it.copy(
//                        sortType = event.sortType
//                    )
//                }
//            }
//
//        }
//    }
*/
}