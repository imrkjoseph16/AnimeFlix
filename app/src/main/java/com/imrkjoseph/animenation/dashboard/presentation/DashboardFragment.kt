package com.imrkjoseph.animenation.dashboard.presentation

import com.imrkjoseph.animenation.app.foundation.BaseFragment
import com.imrkjoseph.animenation.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>(bindingInflater = FragmentDashboardBinding::inflate) {

    override fun onViewCreated() {
        super.onViewCreated()
        with(binding) {
            configureViews()
            setupViewPager()
        }
    }

    private fun FragmentDashboardBinding.configureViews() {
        bottomNavigation.setOnItemSelectedListener { item ->
            viewPager.setCurrentItem(item.order, false)
            true
        }

        onBackPressedCallBack(requireActivity()::finishAffinity)
    }

    private fun FragmentDashboardBinding.setupViewPager() {
        viewPager.apply {
            adapter = DashboardAdapter(
                fragment = childFragmentManager,
                lifecycle = viewLifecycleOwner.lifecycle
            )
            isUserInputEnabled = false
            offscreenPageLimit = 5
        }
    }
}