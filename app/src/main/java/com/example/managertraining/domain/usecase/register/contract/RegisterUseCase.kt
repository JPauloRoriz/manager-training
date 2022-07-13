package com.example.managertraining.domain.usecase.register.contract

import com.example.managertraining.domain.model.UserDomain

interface RegisterUseCase {
    suspend fun invoke(
        name: String,
        login: String,
        password: String,
        confirmPassword: String,
    ) : Result<UserDomain?>
}