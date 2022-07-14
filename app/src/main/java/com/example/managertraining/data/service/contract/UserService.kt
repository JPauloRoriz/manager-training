package com.example.managertraining.data.service.contract

import com.example.managertraining.data.model.UserResponse
import com.example.managertraining.domain.model.UserModel

interface UserService {
    suspend fun saveUser(name: String, email: String, password: String): Result<Any?>
    suspend fun getUser(email: String, password: String): Result<UserResponse>
}