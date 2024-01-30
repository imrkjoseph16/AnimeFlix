package com.example.animenation.dashboard.shared.presentation.details

import com.example.animenation.app.component.TextLine
import com.example.animenation.app.shared.binder.data.OtherEpisodeItem
import com.example.animenation.app.shared.dto.LoadingItemViewDto
import com.example.animenation.app.shared.dto.OtherEpisodesItemViewDto
import com.example.animenation.dashboard.shared.presentation.details.data.DetailsFullData
import javax.inject.Inject

class DetailsItemFactory @Inject constructor() {

    /**
     * (createOverview) building the layout components.
     * */
    fun createOverview(
        details: DetailsFullData,
        episodesCount: Int,
        onBottomScrolled: Boolean = false
    ) = setupOtherEpisodes(
        details = details,
        episodesCount = episodesCount,
        onBottomScrolled = onBottomScrolled
    )

    private fun setupOtherEpisodes(
        details: DetailsFullData,
        episodesCount: Int,
        onBottomScrolled: Boolean
    ) : List<Any> {
        val items = mutableListOf<Any>()
        details.episodes?.take(episodesCount)?.mapIndexed { position, episode ->
            items.add(OtherEpisodeItem(
                dto =  OtherEpisodesItemViewDto(
                    itemId = episode.id ?: error("episodeId not found"),
                    itemImageUrl = details.image,
                    episodeUrl = episode.url,
                    itemTitle = TextLine(text = episode.id),
                    itemSubtitle = TextLine(text = "Episode: ${position + 1}")
                )
            ))
        }
        if (onBottomScrolled && details.totalEpisodes > episodesCount) items.add(LoadingItemViewDto)
        return items
    }
}