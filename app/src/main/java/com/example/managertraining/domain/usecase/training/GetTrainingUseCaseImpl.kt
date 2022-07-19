package com.example.managertraining.domain.usecase.training

import com.example.managertraining.data.repository.training.contract.TrainingRepository
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.domain.usecase.training.contract.GetTrainingsUseCase

class GetTrainingUseCaseImpl(
    private val repository: TrainingRepository
) : GetTrainingsUseCase {
    override suspend fun getTrainings(idUser: String): Result<List<TrainingModel>> {
        return repository.getTrainings(idUser)
    }
}