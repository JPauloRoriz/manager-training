package com.example.managertraining.domain.usecase.exercise.contract

import android.net.Uri

interface SaveImageUseCase {
    suspend fun invoke(data : Uri?): Result<String?>
}