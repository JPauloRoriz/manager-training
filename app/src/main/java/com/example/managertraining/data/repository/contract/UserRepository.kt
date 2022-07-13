package com.example.managertraining.data.repository.contract

import com.example.managertraining.domain.model.UserDomain

interface UserRepository {
    suspend fun addUser(name : String, login : String, password : String): UserDomain?
}