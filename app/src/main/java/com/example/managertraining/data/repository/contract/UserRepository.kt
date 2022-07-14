package com.example.managertraining.data.repository.contract

import com.example.managertraining.domain.model.UserModel

interface UserRepository {
    suspend fun addUser(name : String, login : String, password : String): Result<Any?>
    suspend fun getUser(email : String, password : String): Result<UserModel>
}