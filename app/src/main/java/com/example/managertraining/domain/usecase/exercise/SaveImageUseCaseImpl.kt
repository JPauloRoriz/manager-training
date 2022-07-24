package com.example.managertraining.domain.usecase.exercise

import android.net.Uri
import com.example.managertraining.data.repository.exercise.contract.ExerciseRepository
import com.example.managertraining.domain.usecase.exercise.contract.SaveImageUseCase

class SaveImageUseCaseImpl(
    private val repository: ExerciseRepository
): SaveImageUseCase {
    override suspend fun invoke(data: Uri?): Result<String?> {
        return repository.saveImageExercise(data)
    }
}