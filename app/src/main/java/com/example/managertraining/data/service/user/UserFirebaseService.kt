package com.example.managertraining.data.service.user

import com.example.managertraining.data.model.UserResponse
import com.example.managertraining.data.service.user.contract.UserService
import com.example.managertraining.domain.exception.DefaultException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserFirebaseService(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : UserService {
    private val usersFirebase by lazy {
        firestore.collection(USERS_TABLE)
    }

    override suspend fun saveUser(name: String, email: String, password: String): Result<Any?> {
        return try {
            val resultAuth = auth.createUserWithEmailAndPassword(email, password).await()
            val newUser = UserResponse(resultAuth.user?.uid ?: "", name, email)
            usersFirebase.document(newUser.id).set(newUser).await()
            Result.success(null)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun getUser(email: String, password: String): Result<UserResponse> {

        return try {
            val resultAuth = auth.signInWithEmailAndPassword(email, password).await()

            val uid: String = resultAuth.user?.uid.toString()
            val resultUser = usersFirebase.document(uid).get().await()
            resultUser.toObject(UserResponse::class.java)?.let { userResponse ->
                Result.success(userResponse)
            } ?: run {
                Result.failure(DefaultException())
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    companion object {
        private const val USERS_TABLE = "users"
    }
}