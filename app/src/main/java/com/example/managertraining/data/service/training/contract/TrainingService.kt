package com.example.managertraining.data.service.training.contract

import com.example.managertraining.data.model.TrainingResponse
import com.example.managertraining.data.model.UserResponse

interface TrainingService {
    suspend fun createTraining(email: String): Result<TrainingResponse>
    suspend fun getTrainings(email: String, password: String): Result<UserResponse>
}