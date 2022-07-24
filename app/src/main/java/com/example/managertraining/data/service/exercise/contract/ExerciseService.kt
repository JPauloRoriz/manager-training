package com.example.managertraining.data.service.exercise.contract

import android.graphics.Bitmap
import android.net.Uri
import com.example.managertraining.data.model.ExerciseResponse

interface ExerciseService {
    suspend fun createExercise(exercise : ExerciseResponse) : Result<ExerciseResponse>
    suspend fun deleteExercise(idExercise: String): Result<Any?>
    suspend fun updateExercise(idExercise: String, name: String, note: String, image: String) :Result<ExerciseResponse>
    suspend fun getExercises(idTrainings: String): Result<List<ExerciseResponse>>
    suspend fun deleteAllExercisesOfTraining(idTraining: String)
    suspend fun saveImageExercise(bitmap: Bitmap?): Result<String?>
}