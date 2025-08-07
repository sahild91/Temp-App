package com.trufurrs.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.trufurrs.app.R
import com.trufurrs.app.databinding.ActivityLoginBinding
import com.trufurrs.app.ui.BaseActivity
import com.trufurrs.app.ui.main.MainActivity
import com.trufurrs.app.ui.theme.TruFurrsBrand
import com.trufurrs.app.utils.ValidationUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient

    // Google Sign-In launcher
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            handleGoogleSignInResult(account)
        } catch (e: ApiException) {
            showError("Google Sign-In failed: ${e.message}")
        }
    }

    // Apple Sign-In launcher (if implementing Apple Sign-In)
//    private val appleSignInLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        // Handle Apple Sign-In result
//        handleAppleSignInResult(result.resultCode, result.data)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupGoogleSignIn()
        setupClickListeners()
        observeAuthState()
    }

    private fun setupUI() {
        // Generic TruFurrs branding (no tier-specific elements)
        binding.tvAppTitle.text = TruFurrsBrand.APP_NAME
        binding.tvTagline.text = getString(R.string.brand_promise)

        // Setup form validation
        setupFormValidation()
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupFormValidation() {
        // Real-time email validation
        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateEmail()
            }
        }

        // Real-time password validation
        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validatePassword()
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            handleEmailPasswordLogin()
        }

        binding.btnGoogleSignIn.setOnClickListener {
            handleGoogleSignIn()
        }

        binding.btnAppleSignIn.setOnClickListener {
            handleAppleSignIn()
        }

        binding.btnSignUp.setOnClickListener {
            navigateToSignUp()
        }

        binding.tvForgotPassword.setOnClickListener {
            handleForgotPassword()
        }
    }

    private fun observeAuthState() {
        lifecycleScope.launch {
            authViewModel.authState.collect { state ->
                when (state) {
                    is AuthState.Loading -> {
                        showLoading(true)
                    }
                    is AuthState.Success -> {
                        showLoading(false)
                        // Navigate to main app (tier will be determined after device pairing)
                        navigateToMain()
                    }
                    is AuthState.Error -> {
                        showLoading(false)
                        showError(state.message)
                    }
                    is AuthState.ProfileCompleted -> {
                        showLoading(false)
                        navigateToMain()
                    }
                    else -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun handleEmailPasswordLogin() {
        if (!validateForm()) {
            return
        }

        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        authViewModel.signInWithEmail(email, password)
    }

    private fun handleGoogleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun handleAppleSignIn() {
        // Implement Apple Sign-In if needed
        // For now, show coming soon message
        showMessage("Apple Sign-In coming soon!")
    }

    private fun handleGoogleSignInResult(account: GoogleSignInAccount) {
        authViewModel.signInWithGoogle(account.idToken!!)
    }

//    private fun handleAppleSignInResult(resultCode: Int, data: Intent?) {
//        // Handle Apple Sign-In result
//        // Implementation depends on Apple Sign-In SDK
//    }

    private fun handleForgotPassword() {
        val email = binding.etEmail.text.toString().trim()

        if (email.isEmpty()) {
            binding.tilEmail.error = "Please enter your email first"
            binding.etEmail.requestFocus()
            return
        }

        if (!ValidationUtils.isValidEmail(email)) {
            binding.tilEmail.error = "Please enter a valid email"
            return
        }

        authViewModel.resetPassword(email)
        showMessage("Password reset email sent to $email")
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (!validateEmail()) {
            isValid = false
        }

        if (!validatePassword()) {
            isValid = false
        }

        return isValid
    }

    private fun validateEmail(): Boolean {
        val email = binding.etEmail.text.toString().trim()

        when {
            email.isEmpty() -> {
                binding.tilEmail.error = "Email is required"
                return false
            }
            !ValidationUtils.isValidEmail(email) -> {
                binding.tilEmail.error = "Please enter a valid email"
                return false
            }
            else -> {
                binding.tilEmail.error = null
                return true
            }
        }
    }

    private fun validatePassword(): Boolean {
        val password = binding.etPassword.text.toString()

        when {
            password.isEmpty() -> {
                binding.tilPassword.error = "Password is required"
                return false
            }
            password.length < 6 -> {
                binding.tilPassword.error = "Password must be at least 6 characters"
                return false
            }
            else -> {
                binding.tilPassword.error = null
                return true
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.btnLogin.isEnabled = !show
        binding.btnGoogleSignIn.isEnabled = !show
        binding.btnAppleSignIn.isEnabled = !show
        binding.progressBar.visibility = if (show) android.view.View.VISIBLE else android.view.View.GONE

        if (show) {
            binding.btnLogin.text = "Signing In..."
        } else {
            binding.btnLogin.text = "Log In"
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToSignUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}