package com.example.animenation.dashboard.pages.profile

import androidx.appcompat.app.AppCompatDelegate
import com.example.animenation.app.foundation.BaseFragment
import com.example.animenation.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(bindingInflater = FragmentProfileBinding::inflate) {
    override fun onViewCreated() {
        super.onViewCreated()
        with(binding) {
            configureViews()
        }
    }

    private fun FragmentProfileBinding.configureViews() {
        themeSwitch.setOnCheckedChangeListener { _, checked ->
            if (checked) setApplicationTheme(AppCompatDelegate.MODE_NIGHT_YES)
            else setApplicationTheme(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setApplicationTheme(appTheme: Int) = AppCompatDelegate.setDefaultNightMode(appTheme)
}