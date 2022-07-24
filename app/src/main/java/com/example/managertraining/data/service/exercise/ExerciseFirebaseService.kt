package com.example.managertraining.data.service.exercise

import android.graphics.Bitmap
import com.example.managertraining.data.model.ExerciseResponse
import com.example.managertraining.data.service.exercise.contract.ExerciseService
import com.example.managertraining.domain.exception.DefaultException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class ExerciseFirebaseService(
    private val firestore: FirebaseFirestore,
    private val storageFirebase: FirebaseStorage
) : ExerciseService {
    private val exerciseFirebase by lazy {
        firestore.collection(EXERCISE_TABLE)
    }
    private val exerciseStorage by lazy {
        storageFirebase.reference.child("EXERCICIES")
    }

    override suspend fun createExercise(exercise: ExerciseResponse): Result<ExerciseResponse> {
        return try {
            exercise.id = exerciseFirebase.document().id
            exerciseFirebase.document(exercise.id).set(exercise).await()

            val exerciseResponse = exerciseFirebase.document(exercise.id).get().await()
            exerciseResponse.toObject(ExerciseResponse::class.java)?.let { exerciseResp ->
                Result.success(exerciseResp)
            } ?: run {
                Result.failure(DefaultException())
            }
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
    ): Result<ExerciseResponse> {
        return try {
            exerciseFirebase.document(idExercise)
                .update(
                    "name", name,
                    "note", note,
                    "image", image
                ).await()
            val exerciseResponse = exerciseFirebase.document(idExercise).get().await()
            exerciseResponse.toObject(ExerciseResponse::class.java)?.let { exercise ->
                Result.success(exercise)
            } ?: run {
                Result.failure(DefaultException())
            }
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

    override suspend fun saveImageExercise(bitmap: Bitmap?): Result<String?> {
        return try {
            val baos = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            val idImage = exerciseFirebase.document().id
            val uploadTask = exerciseStorage.child(idImage).putBytes(data).await()

            Result.success(uploadTask.storage.downloadUrl.await().toString())
        } catch (exception: Exception) {
            Result.failure(exception)
        }

    }


    companion object {
        private const val EXERCISE_TABLE = "exercise"
        private const val IMAGE_TABLE = "Image.jpg"
    }
}