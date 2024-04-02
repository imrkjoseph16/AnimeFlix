package com.imrkjoseph.animenation.dashboard.shared.presentation.details

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import com.imrkjoseph.animenation.R
import com.imrkjoseph.animenation.app.foundation.BaseActivity
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.screen.DetailsFragmentArgs
import com.imrkjoseph.animenation.databinding.ActivityMainDetailsHandlerBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainDetailsHandler : BaseActivity<ActivityMainDetailsHandlerBinding>(bindingInflater = ActivityMainDetailsHandlerBinding::inflate) {

    private val navArgs: MainDetailsHandlerArgs by navArgs()

    override fun onActivityCreated() {
        super.onActivityCreated()
        setupNavGraph()
    }

    private fun setupNavGraph() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_details) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph_details, DetailsFragmentArgs(navArgs.argument).toBundle())
    }
}