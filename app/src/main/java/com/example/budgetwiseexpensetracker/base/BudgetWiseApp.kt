package com.example.budgetwiseexpensetracker.base

import android.app.Application
import com.example.budgetwiseexpensetracker.di.dataSourceModule
import com.example.budgetwiseexpensetracker.di.databaseModule
import com.example.budgetwiseexpensetracker.di.repositoryModule
import com.example.budgetwiseexpensetracker.di.useCaseModule
import com.example.budgetwiseexpensetracker.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BudgetWiseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BudgetWiseApp)
            modules(
                viewModelModule,
                useCaseModule,
                repositoryModule,
                dataSourceModule,
                databaseModule
            )
        }
    }
}