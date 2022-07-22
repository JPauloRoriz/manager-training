package com.example.managertraining.domain.usecase.exercise.contract

import com.example.managertraining.domain.model.ExerciseModel

interface GetExerciseUseCase {
    suspend fun invoke(idTrainings: String): Result<List<ExerciseModel>>
}