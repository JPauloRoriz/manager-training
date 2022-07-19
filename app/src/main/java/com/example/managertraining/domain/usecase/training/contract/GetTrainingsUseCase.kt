package com.example.managertraining.domain.usecase.training.contract

import com.example.managertraining.domain.model.TrainingModel

interface GetTrainingsUseCase {
    suspend fun getTrainings(idUser : String) : Result<List<TrainingModel>>
}