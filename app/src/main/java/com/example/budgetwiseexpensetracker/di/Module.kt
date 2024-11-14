package com.example.budgetwiseexpensetracker.di

import androidx.room.Room
import com.example.budgetwiseexpensetracker.data.local.Database.interfaces.TransactionDS
import com.example.budgetwiseexpensetracker.data.local.Database.local_repository.TransactionDSImpel
import com.example.budgetwiseexpensetracker.domain.repository.Repository
import com.example.budgetwiseexpensetracker.domain.repository.RepositoryImp
import com.example.budgetwiseexpensetracker.domain.usecase.GetRecentTransactionUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.SaveTransactionUseCase
import com.example.budgetwiseexpensetracker.presentation.UI.Expense.ExpenseViewModel
import com.example.budgetwiseexpensetracker.presentation.UI.Home.HomeViewModel
import com.example.myroomdatabase.Database.TransactionDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { ExpenseViewModel(get()) }
}
val useCaseModule = module {
    factory { GetRecentTransactionUseCase(get()) }
    factory { SaveTransactionUseCase(get()) }
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
