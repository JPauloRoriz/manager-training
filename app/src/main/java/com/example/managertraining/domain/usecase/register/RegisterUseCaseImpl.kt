package com.example.managertraining.domain.usecase.register

import android.content.Context
import com.example.managertraining.R
import com.example.managertraining.data.repository.user.contract.UserRepository
import com.example.managertraining.domain.exception.EmptyFildException
import com.example.managertraining.domain.exception.PasswordLenghtException
import com.example.managertraining.domain.exception.PasswordNotConfirmException
import com.example.managertraining.domain.usecase.register.contract.RegisterUseCase

class RegisterUseCaseImpl(
    private val repository: UserRepository,
    private val context: Context
) : RegisterUseCase {

    override suspend fun invoke(
        name: String,
        login: String,
        password: String,
        confirmPassword: String
    ): Result<Any?> {
        return if (name.isEmpty() || login.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Result.failure(EmptyFildException(context.getString(R.string.empty_fild)))
        } else if (password.length < 6) {
            Result.failure(PasswordLenghtException(context.getString(R.string.password_lenght)))
        } else if (password != confirmPassword) {
            Result.failure(PasswordNotConfirmException(context.getString(R.string.password_not_confirm)))
        } else {
           return repository.addUser(name, login, password)
        }
    }

}

