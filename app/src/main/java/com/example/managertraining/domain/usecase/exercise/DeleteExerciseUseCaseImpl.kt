package com.example.managertraining.domain.usecase.exercise

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.managertraining.data.repository.exercise.contract.ExerciseRepository
import com.example.managertraining.domain.exception.NoConnectionInternetException
import com.example.managertraining.domain.usecase.exercise.contract.DeleteExerciseUseCase

class DeleteExerciseUseCaseImpl(
    private val context : Context,
    private val repository : ExerciseRepository
) : DeleteExerciseUseCase {
    override suspend fun invoke(idTraining: String): Result<Any?> {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

      return  if(isConnected){
            Result.failure(NoConnectionInternetException())
        }else {
            repository.deleteExercise(idTraining)
      }
    }
}