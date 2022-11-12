package scientists.house.affiche.app.screens.main.tabs.affiche

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import scientists.house.affiche.app.R
import scientists.house.affiche.app.databinding.FragmentAffichePostDetailsBinding
import scientists.house.affiche.app.screens.base.BaseFragment

@AndroidEntryPoint
class AffichePostDetailsFragment : BaseFragment(R.layout.fragment_affiche_post_details) {

    override val viewModel by viewModels<AfficheViewModel>()

    private lateinit var binding: FragmentAffichePostDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAffichePostDetailsBinding.bind(view)
    }
}
