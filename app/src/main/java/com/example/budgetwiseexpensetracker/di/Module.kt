package com.example.budgetwiseexpensetracker.di

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.budgetwiseexpensetracker.data.local.database.BudgetWiseDatabase
import com.example.budgetwiseexpensetracker.data.local.interfaces.PersonalInfoDS
import com.example.budgetwiseexpensetracker.data.local.interfaces.TransactionDS
import com.example.budgetwiseexpensetracker.data.local.local_repository.PersonalInfoDSImpel
import com.example.budgetwiseexpensetracker.data.local.local_repository.TransactionDSImpel
import com.example.budgetwiseexpensetracker.domain.repository.Repository
import com.example.budgetwiseexpensetracker.domain.repository.RepositoryImp
import com.example.budgetwiseexpensetracker.domain.usecase.GetAllTransactionsUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.GetExpenseTransactionUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.GetIncomeTransactionUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.GetPersonalInfoUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.GetRecentTransactionUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.GetTotalBalanceUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.GetTotalExpenseUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.GetTotalIncomeUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.SavePersonalInfoUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.SaveTransactionUseCase
import com.example.budgetwiseexpensetracker.presentation.ui.expense.ExpenseViewModel
import com.example.budgetwiseexpensetracker.presentation.ui.expenseList.ExpenseListViewModel
import com.example.budgetwiseexpensetracker.presentation.ui.home.HomeViewModel
import com.example.budgetwiseexpensetracker.presentation.ui.income.IncomeViewModel
import com.example.budgetwiseexpensetracker.presentation.ui.monthly_spending_summary.MonthlySpendingSummaryViewModel
import com.example.budgetwiseexpensetracker.presentation.ui.profile.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{HomeViewModel(get(),get(),get(),get())}
    viewModel{ExpenseViewModel(get())}
    viewModel{ IncomeViewModel(get()) }
    viewModel { ExpenseListViewModel(get()) }
    viewModel { MonthlySpendingSummaryViewModel(get(),get()) }
    viewModel {ProfileViewModel(get(),get())}
}
val useCaseModule = module {
    factory { GetAllTransactionsUseCase(get()) }
    factory { GetRecentTransactionUseCase(get()) }
    factory { SaveTransactionUseCase(get()) }
    factory { GetTotalBalanceUseCase(get()) }
    factory { GetTotalExpenseUseCase(get()) }
    factory { GetTotalIncomeUseCase(get()) }
    factory { GetIncomeTransactionUseCase(get()) }
    factory { GetExpenseTransactionUseCase(get()) }
    factory { SavePersonalInfoUseCase(get()) }
    factory { GetPersonalInfoUseCase(get()) }
}

val repositoryModule = module {
    single {
        RepositoryImp(get(),get()) as Repository
    }
}
val dataSourceModule = module {
    single { TransactionDSImpel(get()) as TransactionDS }
    single { PersonalInfoDSImpel(get()) as PersonalInfoDS }
}
val databaseModule = module {
    single {
        // Build the Room database with the new name
        Room.databaseBuilder(
            androidContext(),
            BudgetWiseDatabase::class.java,
            "BudgetWise_database"
        ).build()
    }
    single { get<BudgetWiseDatabase>().transactionDao() }
    single { get<BudgetWiseDatabase>().personalInfoDao() }
}

