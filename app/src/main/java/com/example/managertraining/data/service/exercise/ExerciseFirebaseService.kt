package com.example.managertraining.data.service.exercise

import android.net.Uri
import com.example.managertraining.data.model.ExerciseResponse
import com.example.managertraining.data.service.exercise.contract.ExerciseService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File

class ExerciseFirebaseService(
    private val firestore: FirebaseFirestore,
    private val storageFirebase: FirebaseStorage
) : ExerciseService {
    private val exerciseFirebase by lazy {
        firestore.collection(EXERCISE_TABLE)
    }
    private val exerciseStorage by lazy {
        storageFirebase.reference
    }

    val mountainsRef = exerciseStorage.child("mountains.jpg")


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
            exercise.toObjects(ExerciseResponse::class.java).let {
                return Result.success(it)
            }

        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun deleteAllExercisesOfTraining(idTraining: String) {
        getExercises(idTraining).onSuccess { listExercises ->
            listExercises.map {
                exerciseFirebase.document(it.id).delete().await()
            }

        }

    }

    override suspend fun saveImageExercise(data: Uri?): Result<String?> {
        return try {
            val file = File("path/to/images/rivers.jpg")
            val riversRef = exerciseStorage.child("images/${file.name}")

            data?.let { riversRef.putFile(it).await() }
            Result.success("")

        } catch (exception: Exception) {
            Result.failure(exception)
        }

    }

    companion object {
        private const val EXERCISE_TABLE = "exercise"
        private const val IMAGE_TABLE = "Image.jpg"
    }
}