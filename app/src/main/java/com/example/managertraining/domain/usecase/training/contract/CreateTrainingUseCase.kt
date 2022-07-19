package com.example.managertraining.domain.usecase.training.contract

import com.example.managertraining.domain.model.TrainingModel

interface CreateTrainingUseCase {
    suspend fun invoke(
        idUser: String?,
        nameTraining: String,
        descriptionTraining: String
    ): Result<Any?>
}