package com.example.managertraining.domain.usecase.exercise.contract

interface DeleteExerciseUseCase {
    suspend fun invoke(idExercise : String) : Result<Any?>
}