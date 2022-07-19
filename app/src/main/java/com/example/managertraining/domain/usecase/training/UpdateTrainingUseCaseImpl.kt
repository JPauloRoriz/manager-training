package com.example.managertraining.domain.usecase.training

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.managertraining.R
import com.example.managertraining.data.repository.training.contract.TrainingRepository
import com.example.managertraining.domain.exception.EmptyFildException
import com.example.managertraining.domain.exception.NoConnectionInternetException
import com.example.managertraining.domain.usecase.training.contract.UpdateTrainingUseCase

class UpdateTrainingUseCaseImpl(
    private val repository: TrainingRepository,
    private val context: Context
) : UpdateTrainingUseCase {
    override suspend fun invoke(
        idTraining: String?,
        nameTraining: String,
        descriptionTraining: String
    ): Result<Any?> {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        return if (nameTraining.isEmpty() || descriptionTraining.isEmpty()) {
            return Result.failure(EmptyFildException(context.getString(R.string.empty_fild)))
        } else if (!isConnected) {
            Result.failure(NoConnectionInternetException())
        } else {
            repository.updateTraining(idTraining, nameTraining, descriptionTraining)
        }
    }
}
