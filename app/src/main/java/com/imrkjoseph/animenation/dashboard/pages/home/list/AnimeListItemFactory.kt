package com.imrkjoseph.animenation.dashboard.pages.home.list

import androidx.annotation.StringRes
import com.imrkjoseph.animenation.R
import com.imrkjoseph.animenation.app.component.TextLine
import com.imrkjoseph.animenation.app.shared.binder.component.CardDetailsLargeItem
import com.imrkjoseph.animenation.app.shared.binder.component.ContentItem
import com.imrkjoseph.animenation.app.shared.binder.component.HorizontalListContentItem
import com.imrkjoseph.animenation.app.shared.binder.component.SectionTitleItem
import com.imrkjoseph.animenation.app.shared.dto.CardDetailsLargeItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.ContentItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.SectionTitleItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.ShimmerLoadingItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.SpaceItemViewDto
import com.imrkjoseph.animenation.app.util.Default.AnimeType
import com.imrkjoseph.animenation.app.util.Default.AnimeType.*
import com.imrkjoseph.animenation.app.util.Default.EntryPointType
import com.imrkjoseph.animenation.app.util.ViewUtil.Companion.getRandomRatings
import com.imrkjoseph.animenation.dashboard.shared.data.dto.anime.AnimeResponse
import com.imrkjoseph.animenation.dashboard.shared.data.dto.anime.Result
import javax.inject.Inject

class AnimeListItemFactory @Inject constructor() {

    /**
     * (createOverview) building the layout components.
     * */
    fun createOverview(getHomeUiItems: GetAnimeUiItems) = getHomeUiItems.prepareList()

    private fun GetAnimeUiItems.prepareList() = listOf(
        // Top 10 or Popular Anime's
        setupSectionTitle(sectionType = TOPANIME),
        topAnimeList?.takeInitialResult().setupContentDetails(),

        // Latest Anime Episodes Release
        setupSectionTitle(sectionType = RECENTANIME),
        recentEpisodesList?.takeInitialResult().setupContentDetails(),

        // Random Anime
        setupSectionTitle(sectionType = RANDOM),
        randomAnimeList?.takeInitialResult().setupSpecialDetails(),

        // Popular Anime's
        setupSectionTitle(sectionType = POPULARANIME),
        popularAnimeList?.takeInitialResult().setupContentDetails(),

        // Anime Airing Schedule
        setupSectionTitle(sectionType = AIRINGSCHEDULE),
        airingScheduleList?.takeInitialResult().setupContentDetails()
    )

    private fun setupSectionTitle(
        sectionType: AnimeType,
        @StringRes leftTitle: Int? = AnimeType.getSectionTitle(animeType = sectionType),
        @StringRes rightTitle: Int = R.string.title_see_all
    ) = SectionTitleItem(
        id = sectionType,
        dto = SectionTitleItemViewDto(
            leftTitle = TextLine(textRes = leftTitle),
            rightTitle = TextLine(textRes = rightTitle)
        )
    )

    private fun List<Result>?.setupContentDetails() = this?.let {
        HorizontalListContentItem(
            payload = it.map { result ->
                ContentItem(
                    dto = ContentItemViewDto(
                        itemId = result.id,
                        itemTitle = TextLine(text = result.title?.native),
                        itemImageUrl = result.image,
                        typeOfMovie = result.type,
                    )
                )
            }
        )
    } ?: ShimmerLoadingItemViewDto

    private fun List<Result>?.setupSpecialDetails() = this?.let {
        HorizontalListContentItem(
            payload = it.map { result ->
                CardDetailsLargeItem(
                    dto = CardDetailsLargeItemViewDto(
                        itemId = result.id,
                        title = result.title?.native,
                        itemImageUrl = result.image,
                        itemStatus = result.episodeTitle,
                        itemDescription = result.description ?: result.type,
                        itemType = result.type,
                        itemEntryPoint = EntryPointType.ANIME,
                        rating = result.rating ?: getRandomRatings()
                    )
                )
            }
        )
    } ?: SpaceItemViewDto()

    private fun AnimeResponse.takeInitialResult() = if ((this.results?.size ?: 0) >= 5) this.results?.take(5) else this.results
}