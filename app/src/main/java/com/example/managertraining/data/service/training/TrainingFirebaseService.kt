package com.example.managertraining.data.service.training

import com.example.managertraining.data.model.TrainingResponse
import com.example.managertraining.data.model.UserResponse
import com.example.managertraining.data.service.training.contract.TrainingService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TrainingFirebaseService(
    private val firestore: FirebaseFirestore
): TrainingService {
    override suspend fun createTraining(email: String): Result<TrainingResponse> {
        TODO("Not yet implemented")
    }


    override suspend fun getTrainings(email: String, password: String): Result<UserResponse> {
        TODO("Not yet implemented")
    }
}