package scientists.house.affiche.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import scientists.house.affiche.app.databinding.ActivitySplashBinding
import javax.inject.Inject
import scientists.house.affiche.app.screens.splash.SplashFragment
import scientists.house.affiche.app.screens.splash.SplashViewModel
import scientists.house.affiche.app.utils.visible

/**
 * Entry point of the app.
 *
 * Splash activity contains only window background, all other initialization logic is placed to
 * [SplashFragment] and [SplashViewModel].
 */
@AndroidEntryPoint
class SplashActivity @Inject constructor() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //val binding = ActivitySplashBinding.inflate(layoutInflater)
        //binding.pleaseWaitTextView2.visible = true
    }
}
