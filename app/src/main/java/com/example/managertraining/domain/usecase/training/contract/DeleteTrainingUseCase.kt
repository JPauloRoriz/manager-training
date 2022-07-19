package com.example.managertraining.domain.usecase.training.contract

import com.example.managertraining.domain.model.TrainingModel

interface DeleteTrainingUseCase {
    suspend fun invoke(training : TrainingModel) : Result<Any?>
}