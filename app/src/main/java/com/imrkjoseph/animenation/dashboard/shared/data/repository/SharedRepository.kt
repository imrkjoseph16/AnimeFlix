package com.imrkjoseph.animenation.dashboard.shared.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.imrkjoseph.animenation.app.util.Default.Companion.DB_FAVORITES
import com.imrkjoseph.animenation.dashboard.shared.data.dto.favorites.FavoritesDetailsFullData
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedRepository @Inject constructor(
    private val fireStore: FirebaseFirestore,
    firebaseAuth: FirebaseAuth
) {

    private val userId = firebaseAuth.currentUser?.uid.orEmpty()

    suspend fun addToFavorites(data: FavoritesDetailsFullData) : Boolean {
        val favorites = fireStore.collection(DB_FAVORITES)
            .document(userId)
            .collection(data.favoritesType)
            .document()
            .set(data)
        favorites.await()
        return favorites.isSuccessful
    }

    fun getFavorites(
        favoritesType: String,
    ) : Flow<List<FavoritesDetailsFullData>> = flow {
        val fireStorePath = fireStore.collection(DB_FAVORITES)
            .document(userId)
            .collection(favoritesType).get()
        fireStorePath.await()

        val favoritesResult = mutableListOf<FavoritesDetailsFullData>()

        if (fireStorePath.isSuccessful) {
            fireStorePath.result?.forEach {
                val data = it.toObject(FavoritesDetailsFullData::class.java)
                favoritesResult.add(data)
            }
        }

        emit(favoritesResult)
    }
}