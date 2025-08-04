package com.trufurrs.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.trufurrs.app.databinding.ActivityLoginBinding
import com.trufurrs.app.ui.BaseActivity
import com.trufurrs.app.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        binding.tvAppTitle.text = getAppTitle()
        binding.tvTagline.text = if (isActiveMode()) {
            "Smart Pet Tracking"
        } else {
            "Find Your Pet"
        }
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            // For now, just navigate to main activity
            Toast.makeText(this, "Login clicked - ${getAppTitle()}", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            Toast.makeText(this, "Sign Up clicked", Toast.LENGTH_SHORT).show()
        }

        binding.tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Forgot Password clicked", Toast.LENGTH_SHORT).show()
        }
    }
}