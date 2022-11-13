package scientists.house.affiche.app.screens.main.tabs.affiche

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import scientists.house.affiche.app.R
import scientists.house.affiche.app.databinding.FragmentAfficheBinding
import scientists.house.affiche.app.screens.base.BaseFragment

@AndroidEntryPoint
class AfficheFragment : BaseFragment(R.layout.fragment_affiche) {

    override val viewModel by viewModels<AfficheViewModel>()

    private lateinit var binding: FragmentAfficheBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAfficheBinding.bind(view)
    }
}
