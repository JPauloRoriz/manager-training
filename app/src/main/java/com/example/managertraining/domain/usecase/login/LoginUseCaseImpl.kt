package com.example.managertraining.domain.usecase.login

import android.content.Context
import com.example.managertraining.R
import com.example.managertraining.data.repository.user.contract.UserRepository
import com.example.managertraining.domain.exception.EmptyFildException
import com.example.managertraining.domain.model.UserModel
import com.example.managertraining.domain.usecase.login.contract.LoginUseCase

class LoginUseCaseImpl(
    private val context : Context,
    private val repository: UserRepository
) : LoginUseCase {
    override suspend fun invoke(email: String, password: String): Result<UserModel> {
        return if (email.isEmpty() || password.isEmpty()) {
            Result.failure(EmptyFildException(context.getString(R.string.empty_fild)))
        } else {
            return repository.getUser(email, password)
        }
    }
}