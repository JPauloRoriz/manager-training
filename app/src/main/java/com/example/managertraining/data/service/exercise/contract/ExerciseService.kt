package com.example.managertraining.data.service.exercise.contract

import com.example.managertraining.data.model.ExerciseResponse

interface ExerciseService {
    suspend fun createExercise(exercise : ExerciseResponse) : Result<Any?>
    suspend fun deleteExercise(idTraining: String): Result<Any?>
    suspend fun updateExercise(idExercise: String, name: String, note: String) :Result<Any?>
    suspend fun getExercises(idTrainings: String): Result<List<ExerciseResponse>>
}