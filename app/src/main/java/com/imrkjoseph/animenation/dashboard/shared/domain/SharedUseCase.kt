package com.imrkjoseph.animenation.dashboard.shared.domain

import com.imrkjoseph.animenation.dashboard.shared.data.repository.SharedRepository
import com.imrkjoseph.animenation.dashboard.shared.data.dto.favorites.FavoritesDetailsFullData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class SharedUseCase @Inject constructor(
    private val sharedRepository: SharedRepository
) {

    suspend fun addToFavorites(data: FavoritesDetailsFullData) = sharedRepository.addToFavorites(data = data)

    fun getFavorites(favoritesType: String) = sharedRepository.getFavorites(favoritesType = favoritesType)
}