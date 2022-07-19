package com.example.managertraining.data.repository.training

import com.example.managertraining.data.model.TrainingResponse
import com.example.managertraining.data.repository.training.contract.TrainingRepository
import com.example.managertraining.data.service.training.contract.TrainingService
import com.example.managertraining.domain.exception.DefaultException
import com.example.managertraining.domain.exception.NoConnectionInternetException
import com.example.managertraining.domain.model.TrainingModel
import com.google.firebase.FirebaseNetworkException
import java.io.IOException

class TrainingRepositoryImpl(
    private val trainingService: TrainingService
) : TrainingRepository {
    override suspend fun createTraining(
        idUser: String?,
        nameTraining: String,
        descriptionTraining: String
    ): Result<Any?> {
        return trainingService.createTraining(idUser, nameTraining, descriptionTraining)
            .recoverCatching { error ->
                return when (error) {
                    is IOException -> Result.failure(NoConnectionInternetException())
                    is FirebaseNetworkException -> Result.failure(NoConnectionInternetException())
                    else -> Result.failure(DefaultException())
                }
            }
    }

    override suspend fun updateTraining(
        idTraining: String?,
        nameTraining: String,
        descriptionTraining: String
    ): Result<Any?> {
        return trainingService.updateTraining(idTraining, nameTraining, descriptionTraining)
            .recoverCatching { error ->
                return when (error) {
                    is IOException -> Result.failure(NoConnectionInternetException())
                    is FirebaseNetworkException -> Result.failure(NoConnectionInternetException())
                    else -> Result.failure(DefaultException())
                }
            }
    }

    override suspend fun getTrainings(idUser: String): Result<List<TrainingModel>> {
        return trainingService.getTrainings(idUser).map { it ->
            it.map { trainingResponse ->
                TrainingModel(
                    trainingResponse.id,
                    trainingResponse.idUser,
                    trainingResponse.name,
                    trainingResponse.description,
                    trainingResponse.data,
                    false
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

    override suspend fun deleteTraining(training: TrainingModel): Result<Any?> {
        return trainingService.deleteTraining(
            TrainingResponse(
                training.id,
                training.idUser,
                training.name,
                training.description,
                training.data
            )
        ).recoverCatching { exception ->
            return when (exception) {
                is IOException -> Result.failure(NoConnectionInternetException())
                is FirebaseNetworkException -> Result.failure(NoConnectionInternetException())
                else -> Result.failure(DefaultException())
            }
        }
    }


}