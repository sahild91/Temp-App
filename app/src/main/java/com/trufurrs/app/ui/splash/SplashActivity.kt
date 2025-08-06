import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.trufurrs.app.R
import com.trufurrs.app.databinding.ActivitySplashBinding
import com.trufurrs.app.ui.BaseActivity
import com.trufurrs.app.ui.auth.LoginActivity
import com.trufurrs.app.ui.main.MainActivity
import com.trufurrs.app.ui.splash.SplashAuthState
import com.trufurrs.app.ui.splash.SplashViewModel
import com.trufurrs.app.ui.theme.TruFurrsBrand
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()
    private val splashDuration = 3000L // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSplashScreen()
        observeAuthState()
        initializeApp()
    }

    private fun setupSplashScreen() {
        // Set brand elements (no tier-specific branding yet)
        binding.tvAppName.text = TruFurrsBrand.APP_NAME
        binding.tvTagline.text = getString(R.string.brand_promise)

        // Setup brand logo/animation if available
        setupBrandAnimation()
    }

    private fun setupBrandAnimation() {
        // Animate app logo and brand elements
        binding.ivLogo.alpha = 0f
        binding.tvAppName.alpha = 0f
        binding.tvTagline.alpha = 0f

        // Fade in animation with brand timing
        binding.ivLogo.animate()
            .alpha(1f)
            .setDuration(800)
            .setStartDelay(200)
            .start()

        binding.tvAppName.animate()
            .alpha(1f)
            .setDuration(800)
            .setStartDelay(600)
            .start()

        binding.tvTagline.animate()
            .alpha(1f)
            .setDuration(800)
            .setStartDelay(1000)
            .start()
    }

    private fun observeAuthState() {
        lifecycleScope.launch {
            splashViewModel.authState.collect { state ->
                when (state) {
                    is SplashAuthState.Authenticated -> {
                        // User is already logged in, go to main app
                        navigateToMain()
                    }
                    is SplashAuthState.NotAuthenticated -> {
                        // User needs to log in
                        navigateToLogin()
                    }
                    is SplashAuthState.Loading -> {
                        // Still checking auth state, show splash
                        // Animation continues
                    }
                    is SplashAuthState.Error -> {
                        // Error checking auth, default to login
                        navigateToLogin()
                    }
                }
            }
        }
    }

    private fun initializeApp() {
        // Start authentication check and app initialization
        splashViewModel.initializeApp()

        // Ensure minimum splash duration for brand experience
        Handler(Looper.getMainLooper()).postDelayed({
            splashViewModel.completeSplashDuration()
        }, splashDuration)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Disable back button on splash screen
        // User should not be able to navigate away during initialization
    }
}