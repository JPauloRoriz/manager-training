package com.example.managertraining.domain.usecase.exercise

import android.graphics.Bitmap
import com.example.managertraining.data.repository.exercise.contract.ExerciseRepository
import com.example.managertraining.domain.usecase.exercise.contract.SaveImageUseCase

class SaveImageUseCaseImpl(
    private val repository: ExerciseRepository
) : SaveImageUseCase {
    override suspend fun invoke(data: Bitmap?): Result<String?> {
        return repository.saveImageExercise(data)
    }
}