package com.example.managertraining.data.service.user.contract

import com.example.managertraining.data.model.UserResponse

interface UserService {
    suspend fun saveUser(name: String, email: String, password: String): Result<Any?>
    suspend fun getUser(email: String, password: String): Result<UserResponse>
}