package com.trufurrs.app.ui.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.trufurrs.app.R
import com.trufurrs.app.databinding.ActivityMainBinding
import com.trufurrs.app.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        setupTierSpecificUI()
    }

    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup bottom navigation based on tier
        if (isActiveMode()) {
            binding.bottomNavigation.inflateMenu(R.menu.bottom_navigation_active)
        } else {
            binding.bottomNavigation.inflateMenu(R.menu.bottom_navigation_tag)
        }

        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun setupTierSpecificUI() {
        title = getAppTitle()
    }
}