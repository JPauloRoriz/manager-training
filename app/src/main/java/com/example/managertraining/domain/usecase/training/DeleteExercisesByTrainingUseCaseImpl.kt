package com.example.managertraining.domain.usecase.training

import com.example.managertraining.data.repository.exercise.contract.ExerciseRepository
import com.example.managertraining.domain.usecase.training.contract.DeleteExercisesByTrainingUseCase

class DeleteExercisesByTrainingUseCaseImpl(private val repository: ExerciseRepository) :
    DeleteExercisesByTrainingUseCase {
    override suspend fun invoke(idTraining: String) {
        repository.deleteAllExercisesOfTraining(idTraining)
    }
}