package com.example.managertraining.data.repository.exercise.contract

import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import com.example.managertraining.domain.model.ExerciseModel

interface ExerciseRepository {
    suspend fun createExercise(idTraining: String, name: String, note: String, image: String) : Result<ExerciseModel>
    suspend fun deleteExercise(idExercise: String): Result<Any?>
    suspend fun updateExercise(idExercise: String, name: String, note: String, image: String): Result<ExerciseModel>
    suspend fun getExercises(idTrainings: String): Result<List<ExerciseModel>>
    suspend fun deleteAllExercisesOfTraining(idTraining: String)
    suspend fun saveImageExercise(data: Bitmap?): Result<String?>
}