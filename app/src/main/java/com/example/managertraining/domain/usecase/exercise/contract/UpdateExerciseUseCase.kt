package com.example.managertraining.domain.usecase.exercise.contract

import android.net.Uri

interface UpdateExerciseUseCase {
    suspend fun invoke(idExercise: String, name: String, note: String, image: String) : Result<Any?>
}