package com.example.managertraining.domain.usecase.exercise.contract

interface CreateExerciseUseCase {
    suspend fun invoke(idTraining : String, name : String, note : String, image : String) : Result<Any?>
}