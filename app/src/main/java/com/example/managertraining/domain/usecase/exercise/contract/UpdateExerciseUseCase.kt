package com.example.managertraining.domain.usecase.exercise.contract

import android.net.Uri
import com.example.managertraining.domain.model.ExerciseModel

interface UpdateExerciseUseCase {
    suspend fun invoke(idExercise: String, name: String, note: String, image: String) : Result<ExerciseModel>
}