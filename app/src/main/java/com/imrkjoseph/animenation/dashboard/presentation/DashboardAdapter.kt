package com.imrkjoseph.animenation.dashboard.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.imrkjoseph.animenation.dashboard.pages.explore.MainExploreFragment
import com.imrkjoseph.animenation.dashboard.pages.favorites.MainFavoritesFragment
import com.imrkjoseph.animenation.dashboard.pages.home.MainHomeFragment
import com.imrkjoseph.animenation.dashboard.pages.profile.ProfileFragment

class DashboardAdapter(
    fragment: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragment, lifecycle) {

    enum class DashboardPresetFragments {
        HOME_FRAGMENT,
        EXPLORE_FRAGMENT,
        FAVORITES_FRAGMENT,
        PROFILE_FRAGMENT
    }

    override fun getItemCount() = DashboardPresetFragments.values().size

    override fun createFragment(position: Int): Fragment = when(DashboardPresetFragments.values()[position]) {
        DashboardPresetFragments.HOME_FRAGMENT -> MainHomeFragment()
        DashboardPresetFragments.EXPLORE_FRAGMENT -> MainExploreFragment()
        DashboardPresetFragments.FAVORITES_FRAGMENT -> MainFavoritesFragment()
        DashboardPresetFragments.PROFILE_FRAGMENT -> ProfileFragment()
    }
}