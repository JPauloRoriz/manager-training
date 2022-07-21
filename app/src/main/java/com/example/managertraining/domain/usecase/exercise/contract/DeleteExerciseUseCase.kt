package com.example.managertraining.domain.usecase.exercise.contract

interface DeleteExerciseUseCase {
    suspend fun invoke(idTraining : String) : Result<Any?>
}