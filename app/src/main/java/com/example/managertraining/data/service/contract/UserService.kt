package com.example.managertraining.data.service.contract

interface UserService {
    suspend fun saveUser(name: String, email: String, password: String): Result<Any?>
}