package com.imrkjoseph.animenation.welcome

import androidx.navigation.fragment.findNavController
import com.imrkjoseph.animenation.app.foundation.BaseFragment
import com.imrkjoseph.animenation.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(bindingInflater = FragmentWelcomeBinding::inflate) {

    override fun onViewCreated() {
        super.onViewCreated()
        with(binding) {
            configureViews()
        }
    }

    private fun FragmentWelcomeBinding.configureViews() {
        getStarted.setOnClickListener {
            goToLoginScreen()
        }
    }

    private fun goToLoginScreen() = findNavController().navigate(directions = WelcomeFragmentDirections.actionToLoginScreen())
}