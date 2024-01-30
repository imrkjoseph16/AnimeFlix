package com.example.animenation.dashboard.presentation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.animenation.dashboard.pages.explore.MainExploreFragment
import com.example.animenation.dashboard.pages.favorites.FavoritesFragment
import com.example.animenation.dashboard.pages.home.MainHomeFragment
import com.example.animenation.dashboard.pages.profile.ProfileFragment

class DashboardAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {

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
        DashboardPresetFragments.FAVORITES_FRAGMENT -> FavoritesFragment()
        DashboardPresetFragments.PROFILE_FRAGMENT -> ProfileFragment()
    }
}