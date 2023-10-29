package com.example.animenation.dashboard.pages.home.presentation

import com.example.animenation.R
import com.example.animenation.app.component.TextLine
import com.example.animenation.app.shared.binder.data.AnimeCarouselItem
import com.example.animenation.app.shared.binder.data.AnimeItem
import com.example.animenation.app.shared.dto.AnimeItemViewDto
import com.example.animenation.app.shared.dto.SectionTitleItemViewDto
import com.example.animenation.app.shared.dto.ShimmerLoadingItemViewDto
import com.example.animenation.dashboard.pages.home.data.dto.Result
import javax.inject.Inject

class HomeItemFactory @Inject constructor() {

    /**
     * (createOverview) building the layout components.
     * */
    fun createOverview(getHomeUiItems: GetHomeUiItems) = prepareList(getHomeUiItems)

    private fun prepareList(getHomeUiItems: GetHomeUiItems) = listOf(
        setupSectionTitle(leftTitle = R.string.top_10_anime_this_week),
        getHomeUiItems.topAnimeList?.results.setupMovieDetails(ANIMETYPE.TOPANIME),
        setupSectionTitle(leftTitle = R.string.recent_episodes),
        getHomeUiItems.recentEpisodesList?.results.setupMovieDetails(ANIMETYPE.RECENTANIME)
    )

    private fun setupSectionTitle(
        leftTitle: Int,
        rightTitle: Int? = R.string.see_all
    ) = SectionTitleItemViewDto(
        leftTitle = TextLine(textRes = leftTitle),
        rightTitle = TextLine(textRes = rightTitle)
    )

    private fun List<Result>?.setupMovieDetails(animeType: ANIMETYPE) = this?.let {
        AnimeItem(
            id = animeType,
            payload = mapIndexed { _, result ->
                AnimeCarouselItem(
                    dto = AnimeItemViewDto(
                        itemId = result.id,
                        itemTitle = TextLine(text = result.title),
                        itemImageUrl = result.image
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