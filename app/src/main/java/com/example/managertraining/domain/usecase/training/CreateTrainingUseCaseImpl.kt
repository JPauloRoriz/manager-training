package com.example.managertraining.domain.usecase.training

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.managertraining.R
import com.example.managertraining.data.repository.training.contract.TrainingRepository
import com.example.managertraining.domain.exception.EmptyFildException
import com.example.managertraining.domain.exception.NoConnectionInternetException
import com.example.managertraining.domain.usecase.training.contract.CreateTrainingUseCase

class CreateTrainingUseCaseImpl(
    private val repository: TrainingRepository,
    private val context: Context
) : CreateTrainingUseCase {
    override suspend fun invoke(
        idUser: String?,
        nameTraining: String,
        descriptionTraining: String
    ): Result<Any?> {
        return if (nameTraining.isEmpty() || descriptionTraining.isEmpty()) {
            return Result.failure(EmptyFildException(context.getString(R.string.empty_fild)))
        } else {
            repository.createTraining(idUser, nameTraining, descriptionTraining)
        }
    }

}