package com.example.managertraining.data.service.training

import com.example.managertraining.data.model.TrainingResponse
import com.example.managertraining.data.service.training.contract.TrainingService
import com.example.managertraining.domain.exception.DefaultException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TrainingFirebaseService(
    private val firestore: FirebaseFirestore
) : TrainingService {
    private val trainingFirebase by lazy {
        firestore.collection(TRAINING_TABLE)
    }

    override suspend fun createTraining(
        idUser: String?,
        nameTraining: String,
        descriptionTraining: String
    ): Result<Any?> {

        return try {
            val idTraining = trainingFirebase.document().id
            val newTraining = TrainingResponse(
                idTraining,
                idUser = idUser,
                name = nameTraining,
                description = descriptionTraining,
                data = ""
            )
            trainingFirebase.document(idTraining).set(newTraining).await()
            Result.success(null)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun getTrainings(idUser: String): Result<List<TrainingResponse>> {
        return try {
            val trainings = trainingFirebase.whereEqualTo("idUser", idUser).get().await()
            trainings.toObjects(TrainingResponse::class.java).let{
                return Result.success(it)
            }

        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun updateTraining(
        idTraining: String?,
        nameTraining: String,
        descriptionTraining: String
    ): Result<Any?> {
        return try {
            trainingFirebase.document(idTraining.toString())
                .update(
                    "name", nameTraining,
                    "description", descriptionTraining
                ).await()
            Result.success(null)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun deleteTraining(trainingResponse: TrainingResponse): Result<Any?> {
        return try {
            trainingFirebase.document(trainingResponse.id).delete().await()
            Result.success(null)
        } catch (exception : Exception){
            Result.failure(exception)
        }
    }

    companion object {
        private const val TRAINING_TABLE = "training"
    }
}