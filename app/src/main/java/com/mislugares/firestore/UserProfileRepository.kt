package com.mislugares.firestore

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserProfileRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun saveUserProfile(uid: String, profile: UserProfile) {
        require(uid.isNotBlank()) { "uid no puede estar vacío" }
        firestore.collection(COLLECTION_USERS)
            .document(uid)
            .set(profile)
            .await()
    }

    suspend fun getUserProfile(uid: String): UserProfile? {
        require(uid.isNotBlank()) { "uid no puede estar vacío" }
        return firestore.collection(COLLECTION_USERS)
            .document(uid)
            .get()
            .await()
            .toObject(UserProfile::class.java)
    }

    companion object {
        private const val COLLECTION_USERS = "users"
    }
}

data class UserProfile(
    val fullName: String = "",
    val email: String = "",
    val createdAtEpochMillis: Long = System.currentTimeMillis()
)
