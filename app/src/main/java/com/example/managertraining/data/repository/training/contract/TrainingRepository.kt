package com.example.managertraining.data.repository.training.contract

import com.example.managertraining.domain.model.TrainingModel

interface TrainingRepository {
    suspend fun createTraining(
        idUser: String?,
        nameTraining: String,
        descriptionTraining: String
    ): Result<Any?>

    suspend fun updateTraining(
        idTraining: String?,
        nameTraining: String,
        descriptionTraining: String
    ): Result<Any?>

    suspend fun getTrainings(idUser: String): Result<List<TrainingModel>>

    suspend fun deleteTraining(training: TrainingModel): Result<Any?>
}