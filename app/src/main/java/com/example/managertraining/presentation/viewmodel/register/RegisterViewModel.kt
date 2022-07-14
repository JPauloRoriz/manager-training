package com.example.managertraining.presentation.viewmodel.register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managertraining.R
import com.example.managertraining.domain.usecase.register.contract.RegisterUseCase
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.register.model.RegisterEvent
import com.example.managertraining.presentation.viewmodel.register.model.RegisterState
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val context: Context
) : ViewModel() {
    val stateLiveData = MutableLiveData(RegisterState())

    val eventLiveData = SingleLiveEvent<RegisterEvent>()


    suspend fun tapOnRegister(
        name: String,
        login: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            stateLiveData.setLoading(true)
            val result = registerUseCase.invoke(name, login, password, confirmPassword)
            if (result.isSuccess) {
                stateLiveData.setLoading(false)
                eventLiveData.value =
                    RegisterEvent.SuccessRegister(context.getString(R.string.register_success))
            }
            if (result.isFailure) {
                stateLiveData.setLoading(false)
                stateLiveData.value =
                    result.exceptionOrNull()?.message?.let { RegisterState(messageError = it) }
            }
        }
    }

    private fun MutableLiveData<RegisterState>.setLoading(value: Boolean) {
        this.value = this.value?.copy(isLoading = value)
    }
}
