package com.imrkjoseph.animenation.dashboard.shared.presentation.details

import com.imrkjoseph.animenation.R
import com.imrkjoseph.animenation.app.component.TextLine
import com.imrkjoseph.animenation.app.shared.binder.component.CardDetailsLargeItem
import com.imrkjoseph.animenation.app.shared.binder.component.HorizontalListContentItem
import com.imrkjoseph.animenation.app.shared.binder.component.SectionTitleItem
import com.imrkjoseph.animenation.app.shared.dto.AvatarCastItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.CardDetailsLargeItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.LoadingItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.OtherEpisodesItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.OtherRelatedItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.SectionTitleItemViewDto
import com.imrkjoseph.animenation.app.util.Default.Companion.TOTAL_EPISODES
import com.imrkjoseph.animenation.app.util.EntryPointType
import com.imrkjoseph.animenation.app.util.ViewUtil.Companion.getRandomRatings
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.DetailsOtherSelection.EPISODES
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.DetailsOtherSelection.RELATED
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.Character
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.DetailsFullData
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.Episode
import javax.inject.Inject

class DetailsItemFactory @Inject constructor() {

    private var itemsCountToBeDisplayed = 0

    fun prepareCastList(
        cast: List<Character>?,
        actors: List<String>?
    ): List<Any> {
        val items = mutableListOf<Any>()
        // Section Title
        items.add(
            index = 0,
            element = SectionTitleItem(
                dto = SectionTitleItemViewDto(
                    leftTitle = TextLine(
                        textRes = R.string.title_cast
                    )
                )
            )
        ).takeIf {
            cast?.isNotEmpty() == true ||
            actors?.isNotEmpty() == true
        }

        // Casts Details
        cast?.let {
            items.add(
                element = HorizontalListContentItem(
                    payload = it.map { characters ->
                        AvatarCastItemViewDto(
                            itemId = characters.id.toString(),
                            itemFullName = characters.name?.full,
                            itemProfileUrl = characters.image,
                            itemRole = characters.role
                        )
                    }
                )
            )
        } ?:

        items.add(element = HorizontalListContentItem(
            payload = actors?.map { fullName ->
                AvatarCastItemViewDto(itemFullName = fullName)
            } ?: emptyList()
        ))

        return items
    }

    fun prepareOtherSelectionList(
        details: DetailsFullData,
        episodesCount: Int,
        onBottomScrolled: Boolean = false,
        selectedType: DetailsOtherSelection,
        entryPoint: EntryPointType
    ): List<Any> {
        itemsCountToBeDisplayed = episodesCount
        return when (selectedType) {
            EPISODES -> setupOtherEpisodes(
                details = details,
                onBottomScrolled = onBottomScrolled
            )
            RELATED -> setupRelatedDetails(
                entryPoint = entryPoint,
                relations = details.relations
            )
            else -> setupRecommendedDetails(
                entryPoint = entryPoint,
                recommendations = details.recommendations
            )
        }
    }

    private fun setupOtherEpisodes(
        details: DetailsFullData,
        onBottomScrolled: Boolean
    ) : List<Any> {
        val items = mutableListOf<Any>()
        details.episodes.take(getItemsCount(details.episodes.size)).mapIndexed { _, episode ->
            items.add(
                element = OtherEpisodesItemViewDto(
                    itemTitle = TextLine(text = episode.title?.english ?: details.title),
                    itemSubtitle = TextLine(text = episode.releaseDate ?: "Episode: ${episode.number}"),
                    itemDescription = TextLine(text = episode.url ?: episode.episodeId),
                    itemEpisodeId = details.episodeId ?: episode.episodeId,
                    itemShowId = episode.showId ?: details.id,
                    itemImageUrl = details.image,
                )
            )
        }
        if (onBottomScrolled && details.episodes.size > itemsCountToBeDisplayed) items.add(element = LoadingItemViewDto)
        return items
    }

    private fun setupRelatedDetails(
        entryPoint: EntryPointType,
        relations: List<Episode>?
    ) =
        relations?.take(getItemsCount(relations.size))?.map {
            OtherRelatedItemViewDto(
                title = it.title?.english,
                rating = it.rating ?: getRandomRatings(),
                itemType = it.type,
                itemStatus = it.status,
                itemDescription = it.relationType,
                itemImageUrl = it.cover,
                itemId = it.episodeId,
                itemEntryPointType = entryPoint
            )
        } ?: emptyList()

    private fun setupRecommendedDetails(
        entryPoint: EntryPointType,
        recommendations: List<Episode>?
    ) =
        recommendations?.take(getItemsCount(recommendations.size))?.map {
            CardDetailsLargeItem(
                dto = CardDetailsLargeItemViewDto(
                    title = it.title?.english ?: it.title?.native,
                    rating = it.rating ?: getRandomRatings(),
                    itemType = it.type,
                    itemStatus = it.status,
                    itemDescription = "$TOTAL_EPISODES ${it.totalEpisodes ?: 0}",
                    itemImageUrl = it.cover,
                    itemId = it.episodeId,
                    itemEntryPoint = entryPoint
                )
            )
        } ?: emptyList()

    private fun getItemsCount(itemsCount: Int) = if (itemsCountToBeDisplayed > itemsCount) itemsCount else itemsCountToBeDisplayed
}

enum class DetailsOtherSelection {
    EPISODES,
    RELATED,
    RECOMMENDED
}