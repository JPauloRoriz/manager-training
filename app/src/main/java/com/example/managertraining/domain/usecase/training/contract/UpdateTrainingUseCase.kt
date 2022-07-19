package com.example.managertraining.domain.usecase.training.contract

interface UpdateTrainingUseCase {
    suspend fun invoke(
        idTraining: String?,
        nameTraining: String,
        descriptionTraining: String
    ):Result<Any?>
}