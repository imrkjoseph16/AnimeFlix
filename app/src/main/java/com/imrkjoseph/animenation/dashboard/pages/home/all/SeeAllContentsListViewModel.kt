package com.imrkjoseph.animenation.dashboard.pages.home.all

import androidx.lifecycle.ViewModel
import com.imrkjoseph.animenation.dashboard.pages.home.list.GetAnimeUiItems
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeeAllContentsListViewModel @Inject constructor(
    private val factory: SeeAllContentListFactory
) : ViewModel() {

    fun getUiItems(animeUiItems: GetAnimeUiItems) = factory.createOverview(animeUiResult = animeUiItems)
}