package com.example.managertraining.domain.usecase.exercise.contract

interface UpdateExerciseUseCase {
    suspend fun invoke(idExercise: String, name : String, note : String) : Result<Any?>
}