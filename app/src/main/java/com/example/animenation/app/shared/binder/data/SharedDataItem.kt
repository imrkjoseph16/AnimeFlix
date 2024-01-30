package com.example.animenation.app.shared.binder.data

import com.example.animenation.app.shared.dto.AnimeItemViewDto
import com.example.animenation.app.shared.dto.ExploreSeriesItemViewDto
import com.example.animenation.app.shared.dto.OtherEpisodesItemViewDto
import com.example.animenation.app.shared.dto.SearchAnimeItemViewDto

data class AnimeItem(
    val id: Any,
    val payload: List<AnimeCarouselItem>
)

data class AnimeCarouselItem(
    val dto: AnimeItemViewDto
)

data class OtherEpisodeItem(
    val dto: OtherEpisodesItemViewDto
)

data class SearchAnimeItem(
    val dto: SearchAnimeItemViewDto
)

data class ExploreSeriesItem(
    val id: Any,
    val payload: List<ExploreCarouselItem>
)

data class ExploreCarouselItem(
    val dto: ExploreSeriesItemViewDto
)