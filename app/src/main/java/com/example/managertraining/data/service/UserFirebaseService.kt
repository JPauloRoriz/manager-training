package com.example.managertraining.data.service

import com.example.managertraining.data.model.UserResponse
import com.example.managertraining.data.service.contract.UserService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserFirebaseService(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : UserService {
    private val usersFirebase by lazy {
        firestore.collection(USERS_TABLE).document("id")
    }

    override suspend fun saveUser(name: String, email: String, password: String): Result<Any?> {
        return try {
            val newUser = UserResponse(email, name, email)

            val resultAuth = auth.createUserWithEmailAndPassword(newUser.email, password).await()

            CoroutineScope(Dispatchers.IO).launch {
                usersFirebase.set(newUser).await()
            }
            Result.success(null)

        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    companion object {
        private const val USERS_TABLE = "users"
    }
}