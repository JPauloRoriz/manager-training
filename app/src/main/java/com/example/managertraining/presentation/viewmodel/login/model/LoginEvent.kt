package com.example.managertraining.presentation.viewmodel.login.model

sealed class LoginEvent {
    object GoToHome: LoginEvent()
    object GoToRegister: LoginEvent()
}