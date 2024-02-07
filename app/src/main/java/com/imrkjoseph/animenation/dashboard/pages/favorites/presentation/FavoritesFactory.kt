package com.imrkjoseph.animenation.dashboard.pages.favorites.presentation

import com.imrkjoseph.animenation.app.shared.binder.component.FavoritesItem
import com.imrkjoseph.animenation.app.shared.dto.FavoritesItemViewDto
import com.imrkjoseph.animenation.dashboard.shared.data.dto.favorites.FavoritesDetailsFullData
import javax.inject.Inject

class FavoritesFactory @Inject constructor() {

    fun createOverview(data: List<FavoritesDetailsFullData>?) = prepareList(result = data)

    private fun prepareList(result: List<FavoritesDetailsFullData>?) = result?.map { details ->
        FavoritesItem(
            dto = FavoritesItemViewDto(
                itemTitle = details.favoriteName,
                itemId = details.favoriteId.orEmpty(),
                itemDate = details.releaseDate,
                itemImageUrl = details.favoritesImage,
                type = details.favoritesType
            )
        )
    } ?: emptyList()
}