package com.imrkjoseph.animenation.dashboard.pages.home.list

import android.support.annotation.StringRes
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
import com.imrkjoseph.animenation.app.util.EntryPointType
import com.imrkjoseph.animenation.app.util.ViewUtil.Companion.getRandomRatings
import com.imrkjoseph.animenation.dashboard.pages.home.list.AnimeListItemFactory.AnimeType.AIRINGSCHEDULE
import com.imrkjoseph.animenation.dashboard.pages.home.list.AnimeListItemFactory.AnimeType.POPULARANIME
import com.imrkjoseph.animenation.dashboard.pages.home.list.AnimeListItemFactory.AnimeType.RANDOM
import com.imrkjoseph.animenation.dashboard.pages.home.list.AnimeListItemFactory.AnimeType.RECENTANIME
import com.imrkjoseph.animenation.dashboard.pages.home.list.AnimeListItemFactory.AnimeType.TOPANIME
import com.imrkjoseph.animenation.dashboard.shared.data.dto.anime.Result
import javax.inject.Inject

class AnimeListItemFactory @Inject constructor() {

    /**
     * (createOverview) building the layout components.
     * */
    fun createOverview(getHomeUiItems: GetAnimeUiItems) = getHomeUiItems.prepareList()

    private fun GetAnimeUiItems.prepareList() = listOf(
        // Top 10 or Popular Anime's
        setupSectionTitle(animeType = TOPANIME, leftTitle = R.string.section_top_10_anime_this_week),
        topAnimeList?.results?.takeInitialResult().setupContentDetails(),

        // Latest Anime Episodes Release
        setupSectionTitle(animeType = RECENTANIME, leftTitle = R.string.section_latest_episodes),
        recentEpisodesList?.results?.takeInitialResult().setupContentDetails(),

        setupSectionTitle(animeType = RANDOM, leftTitle = R.string.section_random_anime),
        randomAnimeList?.results?.takeInitialResult().setupSpecialDetails(),

        // Popular Anime's
        setupSectionTitle(animeType = POPULARANIME, leftTitle = R.string.section_popular_anime),
        popularAnimeList?.results?.takeInitialResult().setupContentDetails(),

        // Anime Airing Schedule
        setupSectionTitle(animeType = AIRINGSCHEDULE, leftTitle = R.string.section_anime_airing_schedule),
        airingScheduleList?.results?.takeInitialResult().setupContentDetails()
    )

    private fun setupSectionTitle(
        animeType: AnimeType,
        @StringRes leftTitle: Int,
        @StringRes rightTitle: Int = R.string.title_see_all
    ) = SectionTitleItem(
        id = animeType,
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

    private fun List<Result>.takeInitialResult() = if (size >= 5) take(5) else this

    enum class AnimeType {
        TOPANIME,
        RECENTANIME,
        POPULARANIME,
        AIRINGSCHEDULE,
        RANDOM
    }
}