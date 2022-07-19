package com.example.managertraining.data.service.training.contract

import com.example.managertraining.data.model.TrainingResponse
import com.example.managertraining.data.model.UserResponse

interface TrainingService {
    suspend fun createTraining(
        idUser: String?, nameTraining: String, descriptionTraining: String
    ): Result<Any?>

    suspend fun getTrainings(idUser : String): Result<List<TrainingResponse>>
    suspend fun updateTraining(
        idTraining: String?,
        nameTraining: String,
        descriptionTraining: String
    ): Result<Any?>

    suspend fun deleteTraining(trainingResponse: TrainingResponse): Result<Any?>
}