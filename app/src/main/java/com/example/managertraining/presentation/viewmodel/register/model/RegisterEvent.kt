package com.example.managertraining.presentation.viewmodel.register.model

sealed class RegisterEvent {

    data class SuccessRegister(
        val message: String
    ) : RegisterEvent()
}