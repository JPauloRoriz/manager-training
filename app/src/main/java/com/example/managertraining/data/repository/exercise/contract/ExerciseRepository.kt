package com.example.managertraining.data.repository.exercise.contract

interface ExerciseRepository {
    suspend fun createExercise(idTraining: String, name: String, note: String, image: String) : Result<Any?>
    suspend fun deleteExercise(idTraining: String): Result<Any?>
    suspend fun updateExercise(idExercise: String, name: String, note: String): Result<Any?>
}