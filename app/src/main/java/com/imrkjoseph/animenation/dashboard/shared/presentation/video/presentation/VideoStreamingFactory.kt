package com.imrkjoseph.animenation.dashboard.shared.presentation.video.presentation

import com.imrkjoseph.animenation.app.shared.binder.component.CardDetailsSmallItem
import com.imrkjoseph.animenation.app.shared.dto.CardDetailsSmallItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.ShimmerLoadingItemViewDto
import com.imrkjoseph.animenation.app.util.Default.Companion.TOTAL_EPISODES
import com.imrkjoseph.animenation.app.util.Default.EntryPointType
import com.imrkjoseph.animenation.app.util.ViewUtil.Companion.getRandomRatings
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.DetailsFullData
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.Episode
import javax.inject.Inject

class VideoStreamingFactory @Inject constructor() {

    fun prepareList(
        entryPoint: EntryPointType,
        result: DetailsFullData?,
        currentEpisode: Int?
    ) =
        result?.getNotEmptyList()
        .setupEpisodeItems(
            entryPoint = entryPoint,
            result = result,
            currentEpisode = currentEpisode
        )

    private fun DetailsFullData.getNotEmptyList() = when {
        episodes.isNotEmpty() -> episodes
        recommendations.isNotEmpty() -> recommendations
        else -> relations
    }

    private fun List<Episode>?.setupEpisodeItems(
        entryPoint: EntryPointType,
        result: DetailsFullData?,
        currentEpisode: Int?
    ) = this?.let {
        it.mapIndexed { episodeNumber, episode ->
            CardDetailsSmallItem(
                dto = CardDetailsSmallItemViewDto(
                    detailsId = result?.id ?: error("detailsId not found"),
                    typeOfMovie = result.type,
                    itemId = episode.episodeId,
                    showId = episode.showId,
                    title = episode.title?.english,
                    itemPosition = episodeNumber.inc(),
                    itemType = episode.type,
                    itemStatus = episode.status,
                    itemDescription = episode.description ?: "$TOTAL_EPISODES ${episode.episode ?: episode.number}",
                    itemImageUrl = episode.image ?: episode.cover,
                    rating = episode.rating ?: getRandomRatings(),
                    itemEntryPointType = entryPoint,
                    cardVisible = episodeNumber.inc() != currentEpisode,
                )
            )
        }
    } ?: listOf(ShimmerLoadingItemViewDto)
}