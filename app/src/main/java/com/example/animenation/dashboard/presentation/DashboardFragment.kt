package com.example.animenation.dashboard.presentation

import com.example.animenation.app.foundation.BaseFragment
import com.example.animenation.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {

    override fun onViewCreated() {
        super.onViewCreated()
        with(binding) { setupViewPager() }
    }

    private fun FragmentDashboardBinding.setupViewPager(){
        viewPager.apply {
            adapter = DashboardAdapter(this@DashboardFragment)
            isUserInputEnabled = false
            offscreenPageLimit = 5
        }
    }
}