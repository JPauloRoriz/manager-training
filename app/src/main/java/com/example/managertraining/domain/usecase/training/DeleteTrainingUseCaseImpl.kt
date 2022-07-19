package com.example.managertraining.domain.usecase.training

import com.example.managertraining.data.repository.training.contract.TrainingRepository
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.domain.usecase.training.contract.DeleteTrainingUseCase

class DeleteTrainingUseCaseImpl(
    private val repository: TrainingRepository
) : DeleteTrainingUseCase{
    override suspend fun invoke(training: TrainingModel): Result<Any?> {
        return repository.deleteTraining(training)
    }
}