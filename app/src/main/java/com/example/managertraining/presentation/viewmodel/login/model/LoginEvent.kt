package com.example.managertraining.presentation.viewmodel.login.model

sealed class LoginEvent {
    object GoToRegister: LoginEvent()
    object SuccessLogin: LoginEvent()
}