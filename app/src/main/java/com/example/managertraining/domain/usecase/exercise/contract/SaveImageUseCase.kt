package com.example.managertraining.domain.usecase.exercise.contract

import android.graphics.Bitmap

interface SaveImageUseCase {
    suspend fun invoke(data: Bitmap?): Result<String?>
}