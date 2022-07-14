package com.example.managertraining.domain.usecase.login.contract

import com.example.managertraining.domain.model.UserModel

interface LoginUseCase {
    suspend fun invoke(email: String, password: String): Result<UserModel>
}