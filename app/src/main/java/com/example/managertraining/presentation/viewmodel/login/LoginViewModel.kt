package com.example.managertraining.presentation.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managertraining.domain.usecase.login.contract.LoginUseCase
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.login.model.LoginEvent
import com.example.managertraining.presentation.viewmodel.login.model.LoginState
import com.example.managertraining.presentation.viewmodel.util.setLoadingLogin
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validationLoginUseCase: LoginUseCase
) : ViewModel() {
    val stateLiveData = MutableLiveData(LoginState())
    val eventLiveData = SingleLiveEvent<LoginEvent>()


    fun tapOnRegister() {
        eventLiveData.value = LoginEvent.GoToRegister
    }

    suspend fun tapOnLogin(email: String, password: String) {
        viewModelScope.launch {
            stateLiveData.setLoadingLogin(true)
            validationLoginUseCase.invoke(email, password)
                .onSuccess {
                    stateLiveData.setLoadingLogin(false)
                    eventLiveData.value = LoginEvent.SuccessLogin

                }.onFailure {

                }
        }
    }
}