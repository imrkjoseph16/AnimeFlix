package com.example.animenation.app.shared.binder.data

import com.example.animenation.app.shared.dto.AnimeItemViewDto

data class AnimeItem(
    val id: Any,
    val payload: List<AnimeCarouselItem>
)

data class AnimeCarouselItem(
    val dto: AnimeItemViewDto
)