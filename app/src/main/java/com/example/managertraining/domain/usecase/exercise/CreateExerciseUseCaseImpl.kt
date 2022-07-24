package com.example.managertraining.domain.usecase.exercise

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.managertraining.R
import com.example.managertraining.data.repository.exercise.contract.ExerciseRepository
import com.example.managertraining.domain.exception.EmptyFildException
import com.example.managertraining.domain.exception.NoConnectionInternetException
import com.example.managertraining.domain.usecase.exercise.contract.CreateExerciseUseCase

class CreateExerciseUseCaseImpl(
    private val repository: ExerciseRepository,
    private val context: Context
) : CreateExerciseUseCase {
    private val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    private val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

    override suspend fun invoke(
        idTraining: String,
        name: String,
        note: String,
        image: String
    ): Result<Any?> {
        return if (name.isEmpty() || note.isEmpty()) {
            Result.failure(EmptyFildException(context.getString(R.string.empty_fild)))
        }  else if (!isConnected) {
            Result.failure(NoConnectionInternetException())
        } else {
            return repository.createExercise(idTraining, name, note, image)
        }
    }
}