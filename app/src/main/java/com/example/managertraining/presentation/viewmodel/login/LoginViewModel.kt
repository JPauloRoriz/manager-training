package com.example.managertraining.presentation.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.login.model.LoginEvent
import com.example.managertraining.presentation.viewmodel.login.model.LoginState

class LoginViewModel : ViewModel() {
    val stateLiveData = MutableLiveData(LoginState())
    val eventLiveData = SingleLiveEvent<LoginEvent>()



    fun tapOnRegister() {
        eventLiveData.value = LoginEvent.GoToRegister
    }

    fun tapOnLogin(){

    }
}