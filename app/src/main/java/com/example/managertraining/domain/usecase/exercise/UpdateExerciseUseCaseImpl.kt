package com.example.managertraining.domain.usecase.exercise

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.managertraining.R
import com.example.managertraining.data.repository.exercise.contract.ExerciseRepository
import com.example.managertraining.domain.exception.EmptyFildException
import com.example.managertraining.domain.exception.NoConnectionInternetException
import com.example.managertraining.domain.usecase.exercise.contract.UpdateExerciseUseCase

class UpdateExerciseUseCaseImpl(
    private val repository: ExerciseRepository,
    private val context: Context
) : UpdateExerciseUseCase {
    override suspend fun invoke(idExercise: String, name: String, note: String, image: String): Result<Any?> {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        return if (name.isEmpty() || note.isEmpty()) {
            return Result.failure(EmptyFildException(context.getString(R.string.empty_fild)))
        } else if (!isConnected) {
            Result.failure(NoConnectionInternetException())
        } else {
            repository.updateExercise(idExercise, name, note, image)
        }
    }
}