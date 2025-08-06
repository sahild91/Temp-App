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
import com.trufurrs.app.databinding.ActivitySignupBinding
import com.trufurrs.app.ui.BaseActivity
import com.trufurrs.app.ui.onboarding.PetRegistrationActivity
import com.trufurrs.app.ui.theme.TruFurrsBrand
import com.trufurrs.app.utils.ValidationUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignupBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupGoogleSignIn()
        setupClickListeners()
        setupFormValidation()
        observeAuthState()
    }

    private fun setupUI() {
        // Generic TruFurrs branding (no tier-specific elements)
        binding.tvAppTitle.text = TruFurrsBrand.APP_NAME
        binding.tvTagline.text = getString(R.string.brand_promise)

        // Setup form focus order
        setupFormNavigation()
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupFormNavigation() {
        // Setup tab order for form fields
        binding.etFullName.nextFocusDownId = R.id.et_email
        binding.etEmail.nextFocusDownId = R.id.et_phone
        binding.etPhone.nextFocusDownId = R.id.et_password
        binding.etPassword.nextFocusDownId = R.id.et_confirm_password
        binding.etConfirmPassword.nextFocusDownId = R.id.btn_sign_up
    }

    private fun setupFormValidation() {
        // Real-time validation on focus change
        binding.etFullName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateFullName()
        }

        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateEmail()
        }

        binding.etPhone.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validatePhone()
        }

        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validatePassword()
        }

        binding.etConfirmPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateConfirmPassword()
        }
    }

    private fun setupClickListeners() {
        binding.btnSignUp.setOnClickListener {
            handleEmailPasswordSignUp()
        }

        binding.btnGoogleSignUp.setOnClickListener {
            handleGoogleSignIn()
        }

        binding.btnAppleSignUp.setOnClickListener {
            handleAppleSignIn()
        }

        binding.tvLoginLink.setOnClickListener {
            navigateToLogin()
        }

        binding.cbTerms.setOnCheckedChangeListener { _, isChecked ->
            binding.btnSignUp.isEnabled = isChecked && validateForm(showErrors = false)
        }

        binding.tvTermsLink.setOnClickListener {
            showTermsAndConditions()
        }

        binding.tvPrivacyLink.setOnClickListener {
            showPrivacyPolicy()
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
                        // Navigate to pet registration (next step in onboarding)
                        navigateToPetRegistration()
                    }
                    is AuthState.Error -> {
                        showLoading(false)
                        showError(state.message)
                    }
                    is AuthState.ProfileCompleted -> {
                        showLoading(false)
                        navigateToPetRegistration()
                    }
                    else -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun handleEmailPasswordSignUp() {
        if (!validateForm()) {
            return
        }

        if (!binding.cbTerms.isChecked) {
            showError("Please accept the Terms & Conditions and Privacy Policy")
            return
        }

        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim().takeIf { it.isNotEmpty() }
        val password = binding.etPassword.text.toString()

        authViewModel.signUpWithEmail(
            fullName = fullName,
            email = email,
            phoneNumber = phone,
            password = password
        )
    }

    private fun handleGoogleSignIn() {
        if (!binding.cbTerms.isChecked) {
            showError("Please accept the Terms & Conditions and Privacy Policy")
            return
        }

        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun handleAppleSignIn() {
        if (!binding.cbTerms.isChecked) {
            showError("Please accept the Terms & Conditions and Privacy Policy")
            return
        }

        // Implement Apple Sign-In if needed
        showMessage("Apple Sign-In coming soon!")
    }

    private fun handleGoogleSignInResult(account: GoogleSignInAccount) {
        authViewModel.signUpWithGoogle(
            idToken = account.idToken!!,
            displayName = account.displayName ?: "",
            email = account.email ?: "",
            photoUrl = account.photoUrl?.toString()
        )
    }

    private fun validateForm(showErrors: Boolean = true): Boolean {
        var isValid = true

        if (!validateFullName() && showErrors) isValid = false
        if (!validateEmail() && showErrors) isValid = false
        if (!validatePhone() && showErrors) isValid = false
        if (!validatePassword() && showErrors) isValid = false
        if (!validateConfirmPassword() && showErrors) isValid = false

        return isValid
    }

    private fun validateFullName(): Boolean {
        val fullName = binding.etFullName.text.toString().trim()

        when {
            fullName.isEmpty() -> {
                binding.tilFullName.error = "Full name is required"
                return false
            }
            fullName.length < 2 -> {
                binding.tilFullName.error = "Name must be at least 2 characters"
                return false
            }
            !ValidationUtils.isValidName(fullName) -> {
                binding.tilFullName.error = "Please enter a valid name"
                return false
            }
            else -> {
                binding.tilFullName.error = null
                return true
            }
        }
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

    private fun validatePhone(): Boolean {
        val phone = binding.etPhone.text.toString().trim()

        // Phone is optional, but if provided, must be valid
        if (phone.isNotEmpty() && !ValidationUtils.isValidPhone(phone)) {
            binding.tilPhone.error = "Please enter a valid phone number"
            return false
        }

        binding.tilPhone.error = null
        return true
    }

    private fun validatePassword(): Boolean {
        val password = binding.etPassword.text.toString()

        when {
            password.isEmpty() -> {
                binding.tilPassword.error = "Password is required"
                return false
            }
            !ValidationUtils.isValidPassword(password) -> {
                binding.tilPassword.error = "Password must be at least 8 characters with letters and numbers"
                return false
            }
            else -> {
                binding.tilPassword.error = null
                return true
            }
        }
    }

    private fun validateConfirmPassword(): Boolean {
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        when {
            confirmPassword.isEmpty() -> {
                binding.tilConfirmPassword.error = "Please confirm your password"
                return false
            }
            password != confirmPassword -> {
                binding.tilConfirmPassword.error = "Passwords do not match"
                return false
            }
            else -> {
                binding.tilConfirmPassword.error = null
                return true
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.btnSignUp.isEnabled = !show && binding.cbTerms.isChecked
        binding.btnGoogleSignUp.isEnabled = !show
        binding.btnAppleSignUp.isEnabled = !show
        binding.progressBar.visibility = if (show) android.view.View.VISIBLE else android.view.View.GONE

        if (show) {
            binding.btnSignUp.text = "Creating Account..."
        } else {
            binding.btnSignUp.text = "Create Account"
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showTermsAndConditions() {
        // Open terms and conditions (web view or separate activity)
        showMessage("Terms & Conditions - Coming Soon")
    }

    private fun showPrivacyPolicy() {
        // Open privacy policy (web view or separate activity)
        showMessage("Privacy Policy - Coming Soon")
    }

    private fun navigateToLogin() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun navigateToPetRegistration() {
        val intent = Intent(this, PetRegistrationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }
}