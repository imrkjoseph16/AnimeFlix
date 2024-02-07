package com.imrkjoseph.animenation.register.data.repository

import com.imrkjoseph.animenation.register.data.dto.RegisterCredentialResponse
import com.imrkjoseph.animenation.register.data.dto.SaveDetailsFireStore
import com.imrkjoseph.animenation.register.data.dto.SendEmailVerificationResponse
import com.imrkjoseph.animenation.register.domain.data.ICreateUserCredential
import com.imrkjoseph.animenation.register.domain.data.ISaveDetailsFireStore
import com.imrkjoseph.animenation.register.domain.data.ISendEmailVerification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.imrkjoseph.animenation.app.util.Default.Companion.DB_USER
import com.imrkjoseph.animenation.register.data.UserDetails
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) {

    suspend fun registerCredentials(userDetails: UserDetails) : ICreateUserCredential {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(
            userDetails.email.orEmpty(),
            userDetails.password.orEmpty()
        ).await()
        return RegisterCredentialResponse(authResult)
    }

    suspend fun sendEmailVerification() : ISendEmailVerification {
        val emailVerificationTask = firebaseAuth.currentUser?.sendEmailVerification()
        emailVerificationTask?.await()
        return SendEmailVerificationResponse(emailVerificationTask?.isSuccessful == true)
    }

    suspend fun saveFireStoreDetails(details: HashMap<String, String?>) : ISaveDetailsFireStore {
        val userId = firebaseAuth.currentUser?.uid ?: error("userId failed to generate")
        val saveDetailsStatus = fireStore.collection(DB_USER).document(userId).set(details)
        saveDetailsStatus.await()
        return SaveDetailsFireStore(saveDetailsStatus.isSuccessful)
    }
}