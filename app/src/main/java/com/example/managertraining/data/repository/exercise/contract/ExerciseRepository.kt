package com.example.managertraining.data.repository.exercise.contract

import com.example.managertraining.domain.model.ExerciseModel

interface ExerciseRepository {
    suspend fun createExercise(idTraining: String, name: String, note: String, image: String) : Result<Any?>
    suspend fun deleteExercise(idExercise: String): Result<Any?>
    suspend fun updateExercise(idExercise: String, name: String, note: String, image: String): Result<Any?>
    suspend fun getExercises(idTrainings: String): Result<List<ExerciseModel>>
}