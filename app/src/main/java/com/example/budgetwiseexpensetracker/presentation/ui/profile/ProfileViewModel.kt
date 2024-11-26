package com.example.budgetwiseexpensetracker.presentation.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetwiseexpensetracker.data.local.database.entities.PersonalInfoEntity
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.domain.usecase.GetAllTransactionsUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.GetPersonalInfoUseCase
import com.example.budgetwiseexpensetracker.domain.usecase.SavePersonalInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val savePersonalInfoUseCase: SavePersonalInfoUseCase,
    private val getPersonalInfoUseCase: GetPersonalInfoUseCase
) : ViewModel() {


    fun savePersonalInfo(personalInfo: PersonalInfoEntity) {
        viewModelScope.launch {
            try {
                savePersonalInfoUseCase.savePersonalInfo(personalInfo)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error saving transaction: ${e.message}")
            }
        }
    }

    private val _showPersonalInfo =
        MutableStateFlow<PersonalInfoEntity>(PersonalInfoEntity(id = 1, "UserName", null))
    val showPersonalInfo: MutableStateFlow<PersonalInfoEntity> =
        _showPersonalInfo

    fun getPersonalInfo() {
        viewModelScope.launch {
            try {
                getPersonalInfoUseCase.getPersonalInfo().collect { personalInfo ->
                    if (personalInfo != null) {
                        _showPersonalInfo.value = personalInfo
                    } else {
                        // Optionally set default or fallback values
                        _showPersonalInfo.value = PersonalInfoEntity(id = 1, "Default User", null)
                    }
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error fetching personal info: ${e.message}")
            }
        }
    }
}
