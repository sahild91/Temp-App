package com.trufurrs.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trufurrs.app.databinding.FragmentHomeBinding
import com.trufurrs.app.utils.TierConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.tvWelcome.text = "Welcome to ${getAppTitle()}"
        binding.tvTierInfo.text = "Current tier: ${TierConfig.currentTier.name}"

        // Show tier-specific features
        if (TierConfig.isGeofencingEnabled) {
            binding.tvFeatures.text = "Features: GPS Tracking, Geofencing, Activity Monitoring, Smart Alerts"
        } else {
            binding.tvFeatures.text = "Features: Basic GPS Tracking, Find My Pet"
        }
    }

    private fun getAppTitle(): String {
        return if (TierConfig.currentTier == com.trufurrs.app.utils.AppTier.ACTIVE) {
            "TruFurrs ACTIVE"
        } else {
            "TruFurrs TAG"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}