package scientists.house.affiche.app.screens.main.tabs.planned

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import scientists.house.affiche.app.R
import scientists.house.affiche.app.databinding.FragmentPlannedBinding
import scientists.house.affiche.app.screens.base.BaseFragment

@AndroidEntryPoint
class PlannedFragment : BaseFragment(R.layout.fragment_planned) {

    override val viewModel by viewModels<PlannedViewModel>()

    private lateinit var binding: FragmentPlannedBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlannedBinding.bind(view)

        // binding.resultView.setTryAgainAction { viewModel.reload() }
    }
}
