package com.example.managertraining.data.repository.user

import com.example.managertraining.data.repository.user.contract.UserRepository
import com.example.managertraining.data.service.user.contract.UserService
import com.example.managertraining.domain.exception.*
import com.example.managertraining.domain.mapper.mapper
import com.example.managertraining.domain.model.UserModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import java.io.IOException

class UserRepositoryImpl(
    private val userService: UserService
) : UserRepository {
    override suspend fun addUser(name: String, login: String, password: String): Result<Any?> {
        return userService.saveUser(name, login, password).recoverCatching { error ->
            return when (error) {
                is IOException -> Result.failure(NoConnectionInternetException())
                is FirebaseAuthInvalidCredentialsException -> Result.failure(EmailInvalidException())
                is FirebaseAuthUserCollisionException -> Result.failure(EmailExistingException())
                is FirebaseNetworkException -> Result.failure(NoConnectionInternetException())
                else -> Result.failure(DefaultException())
            }
        }
    }

    override suspend fun getUser(email: String, password: String): Result<UserModel> {
        return userService.getUser(email, password).mapper().recoverCatching { error ->
            return when (error) {
                is IOException -> Result.failure(NoConnectionInternetException())
                is FirebaseAuthInvalidCredentialsException -> Result.failure(
                    EmailOrPasswordInvalidException()
                )
                is FirebaseAuthInvalidUserException -> Result.failure(
                    EmailOrPasswordInvalidException()
                )

                is FirebaseNetworkException -> Result.failure(
                    NoConnectionInternetException()
                )
                else -> Result.failure(DefaultException())
            }
        }
    }
}