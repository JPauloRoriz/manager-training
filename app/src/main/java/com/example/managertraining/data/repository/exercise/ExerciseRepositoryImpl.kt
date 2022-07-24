package com.example.managertraining.data.repository.exercise

import android.graphics.Bitmap
import com.example.managertraining.data.model.ExerciseResponse
import com.example.managertraining.data.repository.exercise.contract.ExerciseRepository
import com.example.managertraining.data.service.exercise.contract.ExerciseService
import com.example.managertraining.domain.exception.DefaultException
import com.example.managertraining.domain.exception.NoConnectionInternetException
import com.example.managertraining.domain.model.ExerciseModel
import com.google.firebase.FirebaseNetworkException
import java.io.IOException

class ExerciseRepositoryImpl(
    private val exerciseService: ExerciseService
) : ExerciseRepository {
    override suspend fun createExercise(
        idTraining: String,
        name: String,
        note: String,
        image: String
    ): Result<ExerciseModel> {
        val exerciseResponse =
            ExerciseResponse(idTraining = idTraining, name = name, note = note, image = image)
        return exerciseService.createExercise(exerciseResponse).map {
            ExerciseModel(
                id = it.id,
                idTraining = it.idTraining,
                name = it.name,
                image = it.image,
                note = it.note
            )
        }.recoverCatching { error ->
            return when (error) {
                is IOException -> Result.failure(NoConnectionInternetException())
                is FirebaseNetworkException -> Result.failure(NoConnectionInternetException())
                else -> Result.failure(DefaultException())
            }
        }
    }

    override suspend fun deleteExercise(idExercise: String): Result<Any?> {
        return exerciseService.deleteExercise(idExercise).recoverCatching { error ->
            return when (error) {
                is IOException -> Result.failure(NoConnectionInternetException())
                is FirebaseNetworkException -> Result.failure(NoConnectionInternetException())
                else -> Result.failure(DefaultException())
            }
        }
    }

    override suspend fun updateExercise(
        idExercise: String,
        name: String,
        note: String,
        image: String
    ): Result<ExerciseModel> {
        return exerciseService.updateExercise(idExercise, name, note, image)
            .map { exerciseResponse ->
                return Result.success(
                    ExerciseModel(
                        id = exerciseResponse.id,
                        idTraining = exerciseResponse.idTraining,
                        name = exerciseResponse.name,
                        image = exerciseResponse.image,
                        note = exerciseResponse.note
                    )
                )
            }.recoverCatching { error ->
                return when (error) {
                    is IOException -> Result.failure(NoConnectionInternetException())
                    is FirebaseNetworkException -> Result.failure(NoConnectionInternetException())
                    else -> Result.failure(DefaultException())
                }
            }
    }

    override suspend fun getExercises(idTrainings: String): Result<List<ExerciseModel>> {
        return exerciseService.getExercises(idTrainings).map { listExerciseResponse ->
            listExerciseResponse.map { exerciseResponse ->
                ExerciseModel(
                    id = exerciseResponse.id,
                    idTraining = exerciseResponse.idTraining,
                    name = exerciseResponse.name,
                    image = exerciseResponse.image,
                    note = exerciseResponse.note
                )
            }
        }.recoverCatching { exception ->
            return when (exception) {
                is IOException -> Result.failure(NoConnectionInternetException())
                is FirebaseNetworkException -> Result.failure(NoConnectionInternetException())
                else -> Result.failure(DefaultException())
            }
        }
    }

    override suspend fun deleteAllExercisesOfTraining(idTraining: String) {
        exerciseService.deleteAllExercisesOfTraining(idTraining)
    }

    override suspend fun saveImageExercise(data: Bitmap?): Result<String?> {
        return exerciseService.saveImageExercise(data).recoverCatching { exception ->
            return when (exception) {
                is IOException -> Result.failure(NoConnectionInternetException())
                is FirebaseNetworkException -> Result.failure(NoConnectionInternetException())
                else -> Result.failure(DefaultException())
            }
        }
    }
}
