package com.imrkjoseph.animenation.dashboard.pages.home.list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val animeListItemFactory: AnimeListItemFactory
): ViewModel() {

    fun getUiItems(animeUiItems: GetAnimeUiItems) = animeListItemFactory.createOverview(getHomeUiItems = animeUiItems)
}