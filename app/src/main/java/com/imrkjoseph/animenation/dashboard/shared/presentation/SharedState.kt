package com.imrkjoseph.animenation.dashboard.shared.presentation

import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.DetailsFullData

open class SharedState

data class ShowFavorites(
    val isSuccess: Boolean
) : SharedState()

data class GetContentDetails(val detailsData: DetailsFullData? = null) : SharedState()

data class ShowDetailsError(val throwable: Throwable) : SharedState()