package com.example.animenation.dashboard.pages.home.list

import com.example.animenation.R
import com.example.animenation.app.component.TextLine
import com.example.animenation.app.shared.binder.data.AnimeCarouselItem
import com.example.animenation.app.shared.binder.data.AnimeItem
import com.example.animenation.app.shared.dto.AnimeItemViewDto
import com.example.animenation.app.shared.dto.SectionTitleItemViewDto
import com.example.animenation.app.shared.dto.ShimmerLoadingItemViewDto
import com.example.animenation.app.util.EntryPointType
import com.example.animenation.dashboard.shared.data.dto.anime.Result
import javax.inject.Inject

class AnimeListItemFactory @Inject constructor() {

    /**
     * (createOverview) building the layout components.
     * */
    fun createOverview(getHomeUiItems: GetAnimeUiItems) = prepareList(getHomeUiItems)

    private fun prepareList(getHomeUiItems: GetAnimeUiItems) = listOf(
        setupSectionTitle(leftTitle = R.string.top_10_anime_this_week),
        getHomeUiItems.topAnimeList?.results?.drop(5).setupAnimeDetails(ANIMETYPE.TOPANIME),
        setupSectionTitle(leftTitle = R.string.recent_episodes),
        getHomeUiItems.recentEpisodesList?.results.setupAnimeDetails(ANIMETYPE.RECENTANIME)
    )

    private fun setupSectionTitle(
        leftTitle: Int,
        rightTitle: Int? = R.string.see_all
    ) = SectionTitleItemViewDto(
        leftTitle = TextLine(textRes = leftTitle),
        rightTitle = TextLine(textRes = rightTitle)
    )

    private fun List<Result>?.setupAnimeDetails(animeType: ANIMETYPE) = this?.let {
        AnimeItem(
            id = animeType,
            payload = mapIndexed { _, result ->
                AnimeCarouselItem(
                    dto = AnimeItemViewDto(
                        itemId = result.id,
                        itemTitle = TextLine(text = result.title),
                        itemImageUrl = result.image,
                        type = EntryPointType.ANIME
                    )
                )
            }
        )
    } ?: ShimmerLoadingItemViewDto

    enum class ANIMETYPE {
        TOPANIME,
        RECENTANIME
    }
}