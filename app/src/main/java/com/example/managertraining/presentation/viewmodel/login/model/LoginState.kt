package com.example.managertraining.presentation.viewmodel.login.model

data class LoginState(
    private val isLoading: Boolean = false,
    private val messageError: String = ""
)