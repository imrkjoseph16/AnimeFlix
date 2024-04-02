package com.imrkjoseph.animenation.splash

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.imrkjoseph.animenation.app.foundation.BaseFragment
import com.imrkjoseph.animenation.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(bindingInflater = FragmentSplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated() {
        super.onViewCreated()
        setupObserver()
    }

    private fun setupObserver() {
        with(viewModel) {
            navDirection.observe(viewLifecycleOwner, findNavController()::navigate)
        }
    }
}