package com.example.budgetwiseexpensetracker.di

import androidx.room.Room
import com.example.budgetwiseexpensetracker.data.local.Database.interfaces.TransactionDS
import com.example.budgetwiseexpensetracker.data.local.Database.local_repository.TransactionDSImpel
import com.example.budgetwiseexpensetracker.domain.repository.Repository
import com.example.budgetwiseexpensetracker.domain.repository.RepositoryImp
import com.example.budgetwiseexpensetracker.domain.usecase.GetRecentTransactionUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.GetTotalBalanceUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.GetTotalExpenseUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.GetTotalIncomeUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.SaveTransactionUseCase
import com.example.budgetwiseexpensetracker.presentation.ui.Expense.ExpenseViewModel
import com.example.budgetwiseexpensetracker.presentation.ui.home.HomeViewModel
import com.example.budgetwiseexpensetracker.presentation.ui.income.IncomeViewModel
import com.example.myroomdatabase.Database.TransactionDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{HomeViewModel(get(),get(),get(),get())}
    viewModel{ExpenseViewModel(get())}
    viewModel{ IncomeViewModel(get()) }
}
val useCaseModule = module {
    factory { GetRecentTransactionUseCase(get()) }
    factory { SaveTransactionUseCase(get()) }
    factory { GetTotalBalanceUseCase(get()) }
    factory { GetTotalExpenseUseCase(get()) }
    factory { GetTotalIncomeUseCase(get()) }
}

val repositoryModule = module {
    single {
        RepositoryImp(get()) as Repository
    }
}
val dataSourceModule = module {
    single { TransactionDSImpel(get()) as TransactionDS }
}
val databaseModule = module {
    // Provide the Room Database instance
    single {
        Room.databaseBuilder(
            androidContext(),
            TransactionDatabase::class.java,
            "movie_database"
        ).build()
    }
    single { get<TransactionDatabase>().transactionDao() }
}
