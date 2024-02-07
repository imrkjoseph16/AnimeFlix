package com.imrkjoseph.animenation.dashboard.shared.presentation.video.presentation

import com.imrkjoseph.animenation.app.shared.binder.component.CardDetailsSmallItem
import com.imrkjoseph.animenation.app.shared.dto.CardDetailsSmallItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.ShimmerLoadingItemViewDto
import com.imrkjoseph.animenation.app.util.Default.Companion.TOTAL_EPISODES
import com.imrkjoseph.animenation.app.util.EntryPointType
import com.imrkjoseph.animenation.app.util.ViewUtil.Companion.getRandomRatings
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.DetailsFullData
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.Episode
import javax.inject.Inject

class VideoStreamingFactory @Inject constructor() {

    fun prepareList(entryPoint: EntryPointType, result: DetailsFullData?) = result?.getNotEmptyList().setupEpisodeItems(entryPoint)

    private fun DetailsFullData.getNotEmptyList() = when {
        episodes.isNotEmpty() -> episodes
        recommendations.isNotEmpty() -> recommendations
        else -> relations
    }

    private fun List<Episode>?.setupEpisodeItems(entryPoint: EntryPointType) = this?.let {
        it.map { episode ->
            CardDetailsSmallItem(
                dto = CardDetailsSmallItemViewDto(
                    itemId = episode.episodeId,
                    showId = episode.showId,
                    title = episode.title?.english,
                    itemType = episode.type,
                    itemStatus = episode.status,
                    itemDescription = episode.description ?: "$TOTAL_EPISODES ${episode.episode ?: episode.number}",
                    itemImageUrl = episode.image ?: episode.cover,
                    rating = episode.rating ?: getRandomRatings(),
                    itemEntryPointType = entryPoint
                )
            )
        }
    } ?: listOf(ShimmerLoadingItemViewDto)
}