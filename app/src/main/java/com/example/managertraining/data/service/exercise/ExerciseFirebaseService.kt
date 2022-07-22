package com.example.managertraining.data.service.exercise

import com.example.managertraining.data.model.ExerciseResponse
import com.example.managertraining.data.service.exercise.contract.ExerciseService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ExerciseFirebaseService(
    private val firestore: FirebaseFirestore
) : ExerciseService {
    private val exerciseFirebase by lazy {
        firestore.collection(EXERCISE_TABLE)
    }

    override suspend fun createExercise(exercise: ExerciseResponse): Result<Any?> {
        return try {
            exercise.id = exerciseFirebase.document().id
            exerciseFirebase.document(exercise.id).set(exercise).await()
            Result.success(null)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun deleteExercise(idExercise: String): Result<Any?> {
        return try {
            exerciseFirebase.document(idExercise).delete().await()
            Result.success(null)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun updateExercise(
        idExercise: String,
        name: String,
        note: String,
        image: String
    ): Result<Any?> {
        return try {
            exerciseFirebase.document(idExercise)
                .update(
                    "name", name,
                    "note", note,
                    "image", image
                ).await()
            Result.success(null)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun getExercises(idTrainings: String): Result<List<ExerciseResponse>> {
        return try {
            val exercise = exerciseFirebase.whereEqualTo("idTraining", idTrainings).get().await()
            exercise.toObjects(ExerciseResponse::class.java).let{
                return Result.success(it)
            }

        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    companion object {
        private const val EXERCISE_TABLE = "exercise"
    }
}