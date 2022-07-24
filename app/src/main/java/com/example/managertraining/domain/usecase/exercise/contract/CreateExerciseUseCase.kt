package com.example.managertraining.domain.usecase.exercise.contract

import android.content.res.Resources
import android.net.Uri

interface CreateExerciseUseCase {
    suspend fun invoke(idTraining: String, name: String, note: String, image: String) : Result<Any?>
}