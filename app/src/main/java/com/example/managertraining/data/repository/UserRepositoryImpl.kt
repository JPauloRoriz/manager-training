package com.example.managertraining.data.repository

import com.example.managertraining.data.repository.contract.UserRepository
import com.example.managertraining.data.service.UserFirebaseService
import com.example.managertraining.data.service.contract.UserService
import com.example.managertraining.domain.model.UserDomain

class UserRepositoryImpl(
    private val userService: UserService
) : UserRepository {
    override suspend fun addUser(name: String, login: String, password: String): UserDomain? {
        userService.saveUser(name, login, password)
        return null
    }
}