package com.example.managertraining.presentation.viewmodel.login.model

import com.example.managertraining.domain.model.UserModel

sealed class LoginEvent {
    object GoToRegister: LoginEvent()
    data class SuccessLogin(val user : UserModel): LoginEvent()
}