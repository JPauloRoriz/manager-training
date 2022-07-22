package com.example.managertraining.domain.usecase.exercise

import com.example.managertraining.data.repository.exercise.contract.ExerciseRepository
import com.example.managertraining.domain.model.ExerciseModel
import com.example.managertraining.domain.usecase.exercise.contract.GetExerciseUseCase

class GetExerciseUseCaseImpl(
    private val repository: ExerciseRepository
) : GetExerciseUseCase {
    override suspend fun invoke(idTrainings: String): Result<List<ExerciseModel>> {
        return repository.getExercises(idTrainings)
    }
}