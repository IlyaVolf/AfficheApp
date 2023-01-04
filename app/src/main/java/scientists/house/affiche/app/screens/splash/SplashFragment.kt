package scientists.house.affiche.app.screens.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import scientists.house.affiche.app.R
import scientists.house.affiche.app.databinding.FragmentSplashBinding
import scientists.house.affiche.app.screens.main.MainActivity
import scientists.house.affiche.app.utils.observeEvent
import scientists.house.affiche.app.utils.visible

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding

    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSplashBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        drawElements()

        renderAnimations()

        viewModel.launchMainScreenEvent.observeEvent(viewLifecycleOwner) { launchMainScreen() }
    }

    private fun launchMainScreen() {
        val intent = Intent(requireContext(), MainActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        drawElements()

        startActivity(intent)
    }

    private fun drawElements() {
        binding.logo.visible = true
        binding.pleaseWaitTextView.visible = true
        // binding.pleaseWaitTextView2.visible = true
    }

    private fun renderAnimations() {
        binding.pleaseWaitTextView.alpha = 0f
        binding.pleaseWaitTextView.animate()
            .alpha(1f)
            .setStartDelay(500)
            .setDuration(1000)
            .start()
    }
}
