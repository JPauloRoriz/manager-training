package com.example.managertraining.presentation.viewmodel.util

import androidx.lifecycle.MutableLiveData
import com.example.managertraining.presentation.viewmodel.login.model.LoginEvent
import com.example.managertraining.presentation.viewmodel.login.model.LoginState
import com.example.managertraining.presentation.viewmodel.register.model.RegisterState

fun MutableLiveData<RegisterState>.setLoadingRegister(value: Boolean) {
    this.value = this.value?.copy(isLoading = value)
}

 fun MutableLiveData<RegisterState>.setMessageErrorRegister(message: String) {
    this.value = this.value?.copy(isLoading = false, messageError = message)
}

fun MutableLiveData<LoginState>.setLoadingLogin(value: Boolean) {
    this.value = this.value?.copy(isLoading = value)
}

fun MutableLiveData<LoginState>.setMessageErrorLogin(message: String) {
    this.value = this.value?.copy(isLoading = false, messageError = message)
}

