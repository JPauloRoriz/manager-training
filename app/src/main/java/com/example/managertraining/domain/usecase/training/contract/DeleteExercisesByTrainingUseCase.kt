package com.example.managertraining.domain.usecase.training.contract

interface DeleteExercisesByTrainingUseCase {
    suspend fun invoke(idTraining : String)
}