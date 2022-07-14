package com.example.managertraining.domain.mapper

import com.example.managertraining.data.model.UserResponse
import com.example.managertraining.domain.model.UserModel

fun Result<UserResponse>.mapper(): Result<UserModel> {
    return this.map {
        UserModel(
            id = it.id,
            name = it.name,
            email = it.email
        )
    }
}