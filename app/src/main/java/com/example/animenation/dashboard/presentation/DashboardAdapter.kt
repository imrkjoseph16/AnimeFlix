package com.example.animenation.dashboard.presentation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.animenation.dashboard.pages.home.presentation.HomeFragment

class DashboardAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    enum class DashboardPresetFragments {
        HOME_FRAGMENT
    }

    override fun getItemCount() = DashboardPresetFragments.values().size

    override fun createFragment(position: Int): Fragment = when(DashboardPresetFragments.values()[position]) {
        DashboardPresetFragments.HOME_FRAGMENT -> HomeFragment()
    }
}