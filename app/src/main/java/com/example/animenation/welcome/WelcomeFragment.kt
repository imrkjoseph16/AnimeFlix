package com.example.animenation.welcome

import androidx.navigation.fragment.findNavController
import com.example.animenation.app.foundation.BaseFragment
import com.example.animenation.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    override fun onViewCreated() {
        super.onViewCreated()
        with(binding) { configureViews() }
    }

    private fun FragmentWelcomeBinding.configureViews() {
        getStarted.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.goToDashboard())
        }
    }
}