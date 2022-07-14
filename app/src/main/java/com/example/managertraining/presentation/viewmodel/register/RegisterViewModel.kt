package com.example.managertraining.presentation.viewmodel.register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managertraining.R
import com.example.managertraining.domain.exception.EmailInvalidException
import com.example.managertraining.domain.exception.NoConnectionInternet
import com.example.managertraining.domain.usecase.register.contract.RegisterUseCase
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.register.model.RegisterEvent
import com.example.managertraining.presentation.viewmodel.register.model.RegisterState
import com.example.managertraining.presentation.viewmodel.util.setLoadingRegister
import com.example.managertraining.presentation.viewmodel.util.setMessageErrorRegister
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
            stateLiveData.setLoadingRegister(true)
            registerUseCase.invoke(
                name = name,
                login = login,
                password = password,
                confirmPassword = confirmPassword
            ).onSuccess {
                stateLiveData.setLoadingRegister(false)
                eventLiveData.value =
                    RegisterEvent.SuccessRegister(context.getString(R.string.register_success))
            }.onFailure { error ->
                when (error) {
                    is EmailInvalidException -> {
                        stateLiveData.setMessageErrorRegister(context.getString(R.string.email_or_password_invald))
                    }
                    is NoConnectionInternet -> {
                        stateLiveData.setMessageErrorRegister(context.getString(R.string.not_internet))
                    }
                    else -> {
                        stateLiveData.setMessageErrorRegister(error.message.toString())
                    }
                }
            }
        }
    }

}
