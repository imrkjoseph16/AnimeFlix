package com.example.animenation.dashboard.pages.explore.presentation

import com.example.animenation.R
import com.example.animenation.app.component.TextLine
import com.example.animenation.app.shared.binder.data.AnimeCarouselItem
import com.example.animenation.app.shared.binder.data.AnimeItem
import com.example.animenation.app.shared.binder.data.ExploreCarouselItem
import com.example.animenation.app.shared.binder.data.ExploreSeriesItem
import com.example.animenation.app.shared.dto.AnimeItemViewDto
import com.example.animenation.app.shared.dto.ExploreSeriesItemViewDto
import com.example.animenation.app.shared.dto.SectionTitleItemViewDto
import com.example.animenation.app.shared.dto.SpaceItemViewDto
import com.example.animenation.app.util.EntryPointType
import com.example.animenation.dashboard.pages.explore.data.ExploreResultData
import javax.inject.Inject

class FindAnimeMoviesFactory @Inject constructor() {

    /**
     * (createOverview) building the layout components.
     * */
    fun createOverview(searchResult: ExploreUiItems) = prepareList(searchResult)

    private fun prepareList(searchItems: ExploreUiItems) =
        setupSectionTitle(leftTitle = if (searchItems.animeResult != null) R.string.anime_result else null) +
        searchItems.animeResult.setupCarouselItem(exploreType = EXPLORETYPE.ANIME) +
        setupSectionTitle(leftTitle = if (searchItems.moviesResult != null) R.string.movie_result else null) +
        searchItems.moviesResult.setupCarouselItem(exploreType = EXPLORETYPE.MOVIES) +
        setupSectionTitle(leftTitle = if (searchItems.exploreResult != null) R.string.explore_result else null) +
        searchItems.exploreResult.setupExploreItem()

    private fun setupSectionTitle(
        leftTitle: Int?,
    ) = listOf(
        SectionTitleItemViewDto(
            leftTitle = TextLine(textRes = leftTitle)
        )
    )

    private fun ExploreResultData?.setupCarouselItem(
        exploreType: EXPLORETYPE
    ) = this?.resultDetails?.let {
            AnimeItem(
                id = exploreType,
                payload = it.mapIndexed { _, details ->
                    AnimeCarouselItem(
                        dto = AnimeItemViewDto(
                            itemId = details.id,
                            itemTitle = TextLine(text = details.title),
                            itemImageUrl = details.image,
                            type = if (exploreType == EXPLORETYPE.ANIME) EntryPointType.ANIME else EntryPointType.SERIES
                        )
                    )
                }
            )
        } ?: SpaceItemViewDto()
    
    private fun ExploreResultData?.setupExploreItem() =
        ExploreSeriesItem(
            id = EXPLORETYPE.EXPLORE,
            payload = this?.resultDetails?.mapIndexed { _, details ->
                ExploreCarouselItem(
                    dto = ExploreSeriesItemViewDto(
                        itemId = details.id,
                        itemTitle = TextLine(text = details.title),
                        itemImageUrl = details.image
                    )
                )
            } ?: emptyList()
        )

    enum class EXPLORETYPE {
        ANIME,
        MOVIES,
        EXPLORE
    }
}