package com.imrkjoseph.animenation.dashboard.shared.data.dto.favorites

import com.imrkjoseph.animenation.app.util.EntryPointType

data class FavoritesDetailsFullData(
    val favoriteId: String? = null,
    val favoriteName: String? = null,
    val favoritesImage: String? = null,
    val releaseDate: String? = null,
    val favoritesType: String = EntryPointType.ALL.name
)