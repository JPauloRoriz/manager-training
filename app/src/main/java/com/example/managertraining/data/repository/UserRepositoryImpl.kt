package com.example.managertraining.data.repository

import com.example.managertraining.data.repository.contract.UserRepository
import com.example.managertraining.data.service.contract.UserService
import com.example.managertraining.domain.exception.DefaultException
import com.example.managertraining.domain.exception.EmailInvalidException
import com.example.managertraining.domain.exception.EmailOrPasswordInvalidException
import com.example.managertraining.domain.exception.NoConnectionInternet
import com.example.managertraining.domain.mapper.mapper
import com.example.managertraining.domain.model.UserModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import java.io.IOException

class UserRepositoryImpl(
    private val userService: UserService
) : UserRepository {
    override suspend fun addUser(name: String, login: String, password: String): Result<Any?> {
        return userService.saveUser(name, login, password).recoverCatching { error ->
            return when (error) {
                is IOException -> Result.failure(NoConnectionInternet())
                is FirebaseAuthInvalidCredentialsException -> Result.failure(EmailInvalidException())
                else -> Result.failure(DefaultException())
            }
        }
    }

    override suspend fun getUser(email: String, password: String): Result<UserModel> {
        return userService.getUser(email, password).mapper().recoverCatching { error ->
            return when (error) {
                is IOException -> Result.failure(NoConnectionInternet())
                is FirebaseAuthInvalidCredentialsException -> Result.failure(
                    EmailOrPasswordInvalidException()
                )
                else -> Result.failure(DefaultException())
            }
        }
    }
}