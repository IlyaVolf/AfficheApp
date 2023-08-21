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


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding

    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSplashBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        //renderAnimations()

        viewModel.launchMainScreenEvent.observeEvent(viewLifecycleOwner) { launchMainScreen() }
    }

    private fun launchMainScreen() {
        val intent = Intent(requireContext(), MainActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        startActivity(intent)
    }

}
